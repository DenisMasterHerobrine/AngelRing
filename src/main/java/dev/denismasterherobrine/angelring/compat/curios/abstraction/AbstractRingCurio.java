package dev.denismasterherobrine.angelring.compat.curios.abstraction;

import dev.denismasterherobrine.angelring.compat.curios.integration.ClassicAngelRingIntegration;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.common.capabilities.ForgeCapabilities;

import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.Optional;

public abstract class AbstractRingCurio implements ICurio {
    private static final ClientboundSetActionBarTextPacket packet = new ClientboundSetActionBarTextPacket(Component.translatable("angelring.warning"));
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
        if (newStack.getCapability(ForgeCapabilities.ENERGY).isPresent() && item.getDefaultInstance().getCapability(ForgeCapabilities.ENERGY).isPresent()) {
            if (newStack.getCapability(ForgeCapabilities.ENERGY).resolve().get().getEnergyStored() ==
                    item.getDefaultInstance().getCapability(ForgeCapabilities.ENERGY).resolve().get().getEnergyStored()) return;
        }

        LivingEntity livingEntity = slotContext.entity();
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
    public void onEquipFromUse(SlotContext slotContext) {
        slotContext.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA,
                1.0F, 1.0F);
    }

    @Override
    public boolean showAttributesTooltip(String identifier) {
        return true;
    }

    abstract protected boolean checkIfAllowedToFly(Player player, ItemStack stack);
    abstract protected Component getNotAbleToFlyMessage();
    abstract protected void payForFlight(Player player, ItemStack stack);
    abstract protected boolean warnPlayer(Player player, ItemStack stack);

    @Override
    public void curioTick(SlotContext slotContext) {
        Optional<SlotResult> optStack = CuriosApi.getCuriosHelper().findFirstCurio(slotContext.entity(), item);
        if (optStack.isEmpty()) return;

        ItemStack stack = optStack.get().stack().getCraftingRemainingItem();

        if (slotContext.entity() instanceof Player) {
            Player player = ((Player) slotContext.entity());

            if (player.isCreative() || player.isSpectator()) return;

            if (checkIfAllowedToFly(player, stack) && !player.getAbilities().mayfly) {
                startFlying(player);
            } else if (!checkIfAllowedToFly(player, stack) && player.getAbilities().mayfly) {
                stopFlying(player);
                if (player instanceof ServerPlayer){
                    player.sendSystemMessage(getNotAbleToFlyMessage());
                }
            }

            if (player.getAbilities().mayfly && player.getAbilities().flying) {
                ClassicAngelRingIntegration.once = true;
                payForFlight(player, stack);

                if (player instanceof ServerPlayer serverPlayer && warnPlayer(player, stack)) serverPlayer.connection.send(packet);
            }
        }
    }
}
