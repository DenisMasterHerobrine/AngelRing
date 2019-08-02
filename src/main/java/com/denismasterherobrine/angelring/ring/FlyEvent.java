package com.denismasterherobrine.angelring.ring;

import com.denismasterherobrine.angelring.register.ItemRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class FlyEvent {
    @SubscribeEvent
    public static void onTickPlayerEvent(TickEvent.PlayerTickEvent event){
        PlayerEntity player = (PlayerEntity) event.player;
        if(event.player.inventory.hasItemStack(new ItemStack(ItemRegistry.ItemRing))){
            event.player.abilities.allowFlying = true;
        }else{
            if(event.player.abilities.isFlying == true && !event.player.inventory.hasItemStack(new ItemStack(ItemRegistry.ItemRing))){
                event.player.abilities.isFlying = event.player.abilities.isCreativeMode ? true : false;
                event.player.abilities.allowFlying = event.player.abilities.isCreativeMode ? true : false;
            }
        }
    }
}