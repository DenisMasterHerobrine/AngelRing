package dev.denismasterherobrine.angelring.compat.curios;

import dev.denismasterherobrine.angelring.register.ItemRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponent;
import net.minecraftforge.energy.CapabilityEnergy;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.Optional;

public abstract class AbstractRingCurio implements ICurio {
    private final Item item;
    public AbstractRingCurio(Item item) {
        this.item = item;  // I have the suspicion that this results in a circular reference but yolo.
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext) {
        return true;
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack) {
        // This function gets also called when the data of the item changes.
        // We have to avoid this.
        if (newStack.getItem().getClass() == item.getClass()) return;

        // If any "wireless" charging method tries to recharge Curios slot with Angel Ring we need to recheck if we're full or not, because of skipping ticks on large modpacks.
        // We have to avoid this.
        if (newStack.getCapability(CapabilityEnergy.ENERGY).isPresent() && item.getDefaultInstance().getCapability(CapabilityEnergy.ENERGY).isPresent()) {
            if (newStack.getCapability(CapabilityEnergy.ENERGY).resolve().get().getEnergyStored() ==
                    item.getDefaultInstance().getCapability(CapabilityEnergy.ENERGY).resolve().get().getEnergyStored()) return;
        }

        LivingEntity livingEntity = slotContext.getWearer();
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) livingEntity;

            if (player.isCreative() || player.isSpectator()) return;

            stopFlying(player);
        }
    }

    private void startFlying(PlayerEntity player) {
        player.abilities.mayfly = true;
        player.onUpdateAbilities();
    }

    private void stopFlying(PlayerEntity player) {
        player.abilities.flying = false;
        player.abilities.mayfly = false;
        player.onUpdateAbilities();
    }

    @Override
    public boolean canEquip(String identifier, LivingEntity entityLivingBase) {
        return !CuriosApi.getCuriosHelper().findFirstCurio(entityLivingBase, ItemRegistry.ItemRing).isPresent();
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

    abstract protected boolean checkIfAllowedToFly(PlayerEntity player, ItemStack stack);
    abstract protected TextComponent getNotAbleToFlyMessage();
    abstract protected void payForFlight(PlayerEntity player, ItemStack stack);

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity) {
        Optional<SlotResult> optStack = CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, item);
        if (!optStack.isPresent()) return;

        ItemStack stack = optStack.get().getStack();
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity player = ((PlayerEntity) livingEntity);

            if (player.isCreative() || player.isSpectator()) return;

            if (checkIfAllowedToFly(player, stack) && !player.abilities.mayfly) {
                startFlying(player);
            } else if (!checkIfAllowedToFly(player, stack) && player.abilities.mayfly) {
                stopFlying(player);
                if (player instanceof ServerPlayerEntity){
                    ((ServerPlayerEntity) player).sendMessage(getNotAbleToFlyMessage(), ChatType.GAME_INFO, player.getUUID());
                }
            }

            if (player.abilities.mayfly && player.abilities.flying) {
                ClassicAngelRingIntegration.once = true;
                payForFlight(player, stack);
            }
        }
    }
}
