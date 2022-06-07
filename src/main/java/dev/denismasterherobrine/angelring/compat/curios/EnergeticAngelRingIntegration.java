package dev.denismasterherobrine.angelring.compat.curios;

import dev.denismasterherobrine.angelring.config.Configuration;
import dev.denismasterherobrine.angelring.register.ItemRegistry;
import dev.denismasterherobrine.angelring.item.utils.EnergyItem;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.InterModComms;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnergeticAngelRingIntegration {
    public static void sendImc() {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("angelring").build());
    }

    public static ICapabilityProvider initCapabilities(ItemStack stack) {
        ICurio curio = new AbstractRingCurio(ItemRegistry.EnergeticAngelRing) {
            private final int fePerTick = Configuration.EnergeticFEPerTick.get();

            private IEnergyStorage getEnergyStorage(ItemStack stack) {
                return stack.getCapability(CapabilityEnergy.ENERGY).resolve().get();
            }

            @Override
            protected boolean checkIfAllowedToFly(PlayerEntity player, ItemStack stack) {
                return getEnergyStorage(stack).getEnergyStored() > 1;
            }

            @Override
            protected TextComponent getNotAbleToFlyMessage() {
                return new TranslationTextComponent("item.angelring.energetic_angel_ring.not_enough_fe");
            }

            @Override
            protected void payForFlight(PlayerEntity player, ItemStack stack) {
                getEnergyStorage(stack).extractEnergy(fePerTick, false);
            }
        };

        EnergyItem energyItem = new EnergyItem(stack, Configuration.EnergeticFECapacity.get());

        return new ICapabilityProvider() {
            private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);
            private final LazyOptional<IEnergyStorage> energyStorageOpt = LazyOptional.of(() -> energyItem);

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
                                                     @Nullable Direction side) {
                if (cap == CapabilityEnergy.ENERGY) {
                    return energyStorageOpt.cast();
                } else {
                    return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
                }
            }
        };

    }

    public static boolean isRingInCuriosSlot(ItemStack angelRing, LivingEntity player) {
        return CuriosApi.getCuriosHelper().findEquippedCurio(angelRing.getItem(), player).isPresent();
    }
}
