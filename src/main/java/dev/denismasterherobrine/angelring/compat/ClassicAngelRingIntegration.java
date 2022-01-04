package dev.denismasterherobrine.angelring.compat;

import dev.denismasterherobrine.angelring.config.Configuration;
import dev.denismasterherobrine.angelring.register.ItemRegistry;

import dev.denismasterherobrine.angelring.utils.ExperienceUtils;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;

import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.InterModComms;

import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ClassicAngelRingIntegration {
    private static int i;

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
                    if (!player.abilities.mayfly && ExperienceUtils.getPlayerXP(player) > Configuration.XPCost.get()) {
                        startFlying(player);
                    }
                    if (ExperienceUtils.getPlayerXP(player) <= Configuration.XPCost.get() && Configuration.XPCost.get() != 0){
                        stopFlying(player);
                        if (player instanceof ServerPlayerEntity){
                            // TODO: Localize it through i18n.
                            ((ServerPlayerEntity) player).sendMessage(new StringTextComponent("You need to have XP to fly. Get more XP to fly or take off Angel Ring."), ChatType.GAME_INFO, player.getUUID());
                        }
                    }
                    if (player.abilities.mayfly && player.abilities.flying && ExperienceUtils.getPlayerXP(player) > Configuration.XPCost.get() && Configuration.XPCost.get() != 0) {
                        i++;
                        if (i >= Configuration.TicksPerDrain.get()){
                            ExperienceUtils.addPlayerXP(player, -Configuration.XPCost.get());
                            i = 0;
                        }
                    }
                }
            }

            @Override
            public boolean canEquip(String identifier, LivingEntity entityLivingBase) {
                return !CuriosApi.getCuriosHelper().findEquippedCurio(ItemRegistry.ItemRing, entityLivingBase).isPresent();
            }

            @Override
            public void onEquipFromUse(SlotContext slotContext) {
                slotContext.getWearer().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA,
                        1.0F, 1.0F);
            }

            @Override
            public boolean showAttributesTooltip(String identifier) {
                return true;
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
