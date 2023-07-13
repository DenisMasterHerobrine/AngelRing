package dev.denismasterherobrine.angelring.compat.curios;

import dev.denismasterherobrine.angelring.config.Configuration;
import dev.denismasterherobrine.angelring.register.ItemRegistry;
import dev.denismasterherobrine.angelring.utils.ExperienceUtils;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;

import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.server.ServerLifecycleHooks;
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
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("angelring").icon(new ResourceLocation("curios:slot/empty_ring_slot")).build());
    }

    public static ICapabilityProvider initCapabilities() {
        ICurio curio = new AbstractRingCurio(ItemRegistry.ANGEL_RING.get().asItem()) {
            final ItemStack stack = new ItemStack(ItemRegistry.ANGEL_RING.get()); // I believe it should work since there is no additional data by default.

            @Override
            public ItemStack getStack() {
                return stack;
            }

            @Override
            protected boolean checkIfAllowedToFly(Player player, ItemStack stack) {
                if (Configuration.XPCost.get() == 0) {
                    return true;
                }
                else return ExperienceUtils.getPlayerXP(player) >= Configuration.XPCost.get();
            }

            @Override
            protected Component getNotAbleToFlyMessage() {
                return Component.translatable("item.angelring.angel_ring.not_enough_xp");
            }

            @Override
            protected void payForFlight(Player player, ItemStack stack) {
                ticksDrained++;
                if (ticksDrained > Configuration.TicksPerDrain.get()) {
                    if (!once) return;

                    ServerPlayer serverPlayer = getServerPlayerInstance(player.getUUID());

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

    public static ServerPlayer getServerPlayerInstance(UUID playerUUID) {
        ServerPlayer player = null;

        if (ServerLifecycleHooks.getCurrentServer() != null) {
            player = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(playerUUID);
        }

        return player;
    }
}
