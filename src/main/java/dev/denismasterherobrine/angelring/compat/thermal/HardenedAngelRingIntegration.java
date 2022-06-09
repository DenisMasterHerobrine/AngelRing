package dev.denismasterherobrine.angelring.compat.thermal;

import dev.denismasterherobrine.angelring.compat.curios.AbstractRingCurio;
import dev.denismasterherobrine.angelring.config.Configuration;
import dev.denismasterherobrine.angelring.item.utils.EnergyItem;
import dev.denismasterherobrine.angelring.register.ItemRegistry;
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
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HardenedAngelRingIntegration {
    public static ICapabilityProvider initCapabilities(ItemStack stack) {
        ICurio curio = new AbstractRingCurio(ItemRegistry.HardenedAngelRing) {
            private final int fePerTick = Configuration.HardenedFEPerTick.get();

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

        EnergyItem energyItem = new EnergyItem(stack, Configuration.HardenedCapacity.get());

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
