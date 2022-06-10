package dev.denismasterherobrine.angelring.compat.curios;

import dev.denismasterherobrine.angelring.register.ItemRegistry;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

    /*
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack) {
        LivingEntity livingEntity = slotContext.getWearer();
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) livingEntity;
            startFlying(player);
        }
    }
    */

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack) {
        // This function gets also called when the data of the item changes.
        // We have to avoid this.
        if (newStack.getItem().getClass() == item.getClass()) return;

        LivingEntity livingEntity = slotContext.getWearer();
        if (livingEntity instanceof Player) {
            Player player = (Player) livingEntity;

            if (player.isCreative() || player.isSpectator()) return;

            stopFlying(player);
        }
    }

    private void startFlying(Player player) {
        player.getAbilities().mayfly = true;
        player.onUpdateAbilities();
    }

    private void stopFlying(Player player) {
        player.getAbilities().flying = false;
        player.getAbilities().mayfly = false;
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

    abstract protected boolean checkIfAllowedToFly(Player player, ItemStack stack);
    abstract protected TranslatableComponent getNotAbleToFlyMessage();
    abstract protected void payForFlight(Player player, ItemStack stack);

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity) {
        Optional<SlotResult> optStack = CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, item);
        if (!optStack.isPresent()) return;

        ItemStack stack = optStack.get().stack().getContainerItem();
        if (livingEntity instanceof Player) {
            Player player = ((Player) livingEntity);

            if (player.isCreative() || player.isSpectator()) return;

            if (checkIfAllowedToFly(player, stack) && !player.getAbilities().mayfly) {
                startFlying(player);
            } else if (!checkIfAllowedToFly(player, stack) && player.getAbilities().mayfly) {
                stopFlying(player);
                if (player instanceof ServerPlayer){
                    ((ServerPlayer) player).sendMessage(getNotAbleToFlyMessage(), ChatType.GAME_INFO, player.getUUID());
                }
            }

            if (player.getAbilities().mayfly && player.getAbilities().flying) {
                payForFlight(player, stack);
            }
        }
    }
}
