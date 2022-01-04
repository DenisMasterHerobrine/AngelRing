package dev.denismasterherobrine.angelring.compat;

import dev.denismasterherobrine.angelring.config.Configuration;
import dev.denismasterherobrine.angelring.register.ItemRegistry;
import dev.denismasterherobrine.angelring.utils.ExperienceUtils;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

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
            public ItemStack getStack() {
                return ItemRegistry.ItemRing.getDefaultInstance();
            };

            @Override
            public boolean canEquipFromUse(SlotContext slotContext) {
                return true;
            }

            @Override
            public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                LivingEntity livingEntity = slotContext.entity();
                if (livingEntity instanceof Player) {
                    Player player = (Player) livingEntity;
                    startFlying(player);
                }
            }

            @Override
            public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                LivingEntity livingEntity = slotContext.getWearer();
                if (livingEntity instanceof Player) {
                    Player player = (Player) livingEntity;
                    stopFlying(player);
                }
            }

            private void startFlying(Player player) {
                if (!player.isCreative() && !player.isSpectator()) {
                    player.getAbilities().mayfly = true;
                    player.onUpdateAbilities();
                }
            }

            private void stopFlying(Player player) {
                if (!player.isCreative() && !player.isSpectator()) {
                    player.getAbilities().flying = false;
                    player.getAbilities().mayfly = false;
                    player.onUpdateAbilities();
                }
            }

            @Override
            public void curioTick(String identifier, int index, LivingEntity livingEntity) {
                if (livingEntity instanceof Player) {
                    Player player = ((Player) livingEntity);
                    if (!player.getAbilities().mayfly && ExperienceUtils.getPlayerXP(player) > Configuration.XPCost.get()) {
                        startFlying(player);
                    }
                    if (ExperienceUtils.getPlayerXP(player) <= Configuration.XPCost.get()){
                        stopFlying(player);
                        if (player instanceof ServerPlayer){
                            // TODO: Localize it through i18n.
                            ((ServerPlayer) player).sendMessage(new TextComponent("You need to have XP to fly. Get more XP to fly or take off Angel Ring."), ChatType.GAME_INFO, player.getUUID());
                        }
                    }
                    if (player.getAbilities().mayfly && player.getAbilities().flying && ExperienceUtils.getPlayerXP(player) > Configuration.XPCost.get()) {
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
                slotContext.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA,
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
