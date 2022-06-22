package dev.denismasterherobrine.angelring.compat.curios;

import dev.denismasterherobrine.angelring.compat.curios.AbstractRingCurio;
import dev.denismasterherobrine.angelring.config.Configuration;
import dev.denismasterherobrine.angelring.register.ItemRegistry;
import dev.denismasterherobrine.angelring.utils.ExperienceUtils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.InterModComms;

import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ClassicAngelRingIntegration {
    private static int ticksDrained;

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
                if (ticksDrained >= Configuration.TicksPerDrain.get()){
                    ExperienceUtils.addPlayerXP(player, -Configuration.XPCost.get());
                    ticksDrained = 0;
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

    public static boolean isRingInCuriosSlot(ItemStack angelRing, LivingEntity player) {
        return CuriosApi.getCuriosHelper().findEquippedCurio(angelRing.getItem(), player).isPresent();
    }
}
