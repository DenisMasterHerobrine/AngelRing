package com.denismasterherobrine.angelring.compat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.denismasterherobrine.angelring.register.ItemRegistry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.InterModComms;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CuriosCompat {
    public static void sendImc() {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("angelring").build());
    }

    public static ICapabilityProvider initCapabilities() {
        ICurio curio = new ICurio() {
            @Override
            public boolean canEquipFromUse(SlotContext slotContext) {
                return true;
            }

            @Override
            public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                LivingEntity livingEntity = slotContext.getWearer();
                if (livingEntity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) livingEntity;
                    startFlying(player);
                }
            }

            @Override
            public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                LivingEntity livingEntity = slotContext.getWearer();
                if (livingEntity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) livingEntity;
                    stopFlying(player);
                }
            }

            private void startFlying(PlayerEntity player) {
                if (!player.isCreative() && !player.isSpectator()) {
                    player.abilities.mayfly = true;
                    player.onUpdateAbilities();
                }
            }

            private void stopFlying(PlayerEntity player) {
                if (!player.isCreative() && !player.isSpectator()) {
                    player.abilities.flying = false;
                    player.abilities.mayfly = false;
                    player.onUpdateAbilities();
                }
            }

            @Override
            public void curioTick(String identifier, int index, LivingEntity livingEntity) {
                if (livingEntity instanceof PlayerEntity) {
                    PlayerEntity player = ((PlayerEntity) livingEntity);
                    if (!player.abilities.mayfly) {
                        startFlying(player);
                    }
                }
            }

            @Override
            public boolean canEquip(String identifier, LivingEntity entityLivingBase) {
                return !CuriosApi.getCuriosHelper().findEquippedCurio(ItemRegistry.ItemRing, entityLivingBase).isPresent();
            }

            @Override
            public void playRightClickEquipSound(LivingEntity entityLivingBase) {
                entityLivingBase.playSound(SoundEvents.ARMOR_EQUIP_ELYTRA,
                        1.0F, 1.0F);
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
