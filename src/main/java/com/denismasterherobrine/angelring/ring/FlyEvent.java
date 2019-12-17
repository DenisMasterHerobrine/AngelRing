package com.denismasterherobrine.angelring.ring;

import com.denismasterherobrine.angelring.compat.CuriosCompat;
import com.denismasterherobrine.angelring.compat.EverlastingAbilitiesCompat;
import com.denismasterherobrine.angelring.compat.ExternalMods;
import com.denismasterherobrine.angelring.register.ItemRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FlyEvent {
    @SubscribeEvent
    public static void onTickPlayerEvent(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        ItemStack angelRing = new ItemStack(ItemRegistry.ItemRing);
        if (player.inventory.hasItemStack(angelRing) || checkRing(angelRing, player) || checkAbility(player)) {
            player.abilities.allowFlying = true;
        } else {
            if (player.abilities.isFlying && !player.inventory.hasItemStack(angelRing) && !player.isCreative() && !player.isSpectator()) {
                player.abilities.isFlying = false;
                player.abilities.allowFlying = false;
            }
        }
    }

    private static boolean checkRing(ItemStack angelRing, LivingEntity player) {
        if (ExternalMods.CURIOS.isLoaded())
            return CuriosCompat.isRingInCuriosSlot(angelRing, player);
        return false;
    }

    private static boolean checkAbility(PlayerEntity player) {
        if (ExternalMods.EVERLASTINGABILITIES.isLoaded())
            return EverlastingAbilitiesCompat.getAllowFlying(player);
        return false;
    }
}