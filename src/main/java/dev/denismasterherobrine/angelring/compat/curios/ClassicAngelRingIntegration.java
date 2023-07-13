package dev.denismasterherobrine.angelring.compat.curios;

import dev.denismasterherobrine.angelring.config.Configuration;
import dev.denismasterherobrine.angelring.register.ItemRegistry;
import dev.denismasterherobrine.angelring.utils.ExperienceUtils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.InterModComms;

import net.minecraftforge.fml.server.ServerLifecycleHooks;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class ClassicAngelRingIntegration {
    private static int ticksDrained;
    public static boolean once = true;

    public static void sendImc() {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("angelring").build());
    }

    public static ICapabilityProvider initCapabilities() {
        ICurio curio = new AbstractRingCurio(ItemRegistry.ItemRing) {
            @Override
            protected boolean checkIfAllowedToFly(PlayerEntity player, ItemStack stack) {
                if (Configuration.XPCost.get() == 0) {
                    return true;
                }
                else return ExperienceUtils.getPlayerXP(player) >= Configuration.XPCost.get();
            }

            @Override
            protected TextComponent getNotAbleToFlyMessage() {
                return new TranslationTextComponent("item.angelring.itemring.not_enough_xp");
            }

            @Override
            protected void payForFlight(PlayerEntity player, ItemStack stack) {
                ticksDrained++;
                if (ticksDrained > Configuration.TicksPerDrain.get()) {
                    if (!once) return;

                    ServerPlayerEntity serverPlayer = getServerPlayerInstance(player.getUUID());

                    if (serverPlayer != null) {
                        serverPlayer.giveExperiencePoints(-Configuration.XPCost.get());
                    }

                    ticksDrained = 0;
                    once = false;
                }
            }
        };

        return new ICapabilityProvider() {
            private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
                                                     @Nullable Direction side) {

                return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
            }
        };

    }

    public static ServerPlayerEntity getServerPlayerInstance(UUID playerUUID) {
        ServerPlayerEntity player = null;

        if (ServerLifecycleHooks.getCurrentServer() != null) {
            player = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(playerUUID);
        }

        return player;
    }
}
