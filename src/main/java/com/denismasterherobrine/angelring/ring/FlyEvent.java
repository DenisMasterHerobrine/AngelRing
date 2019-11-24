package com.denismasterherobrine.angelring.ring;

import com.denismasterherobrine.angelring.register.ItemRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosAPI;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class FlyEvent {
    @SubscribeEvent
    public static void onTickPlayerEvent(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;
        ItemStack angelRing = new ItemStack(ItemRegistry.ItemRing);
        if(player.inventory.hasItemStack(angelRing) || CuriosAPI.getCurioEquipped(angelRing.getItem(), (LivingEntity) player.getEntity()).isPresent()){
            player.abilities.allowFlying = true;
        }else{
            if(player.abilities.isFlying && !player.inventory.hasItemStack(angelRing) && !player.isCreative() && !player.isSpectator()){
                player.abilities.isFlying = false;
                player.abilities.allowFlying = false;
            }
        }
    }
}