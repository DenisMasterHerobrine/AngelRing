package com.denismasterherobrine.angelring.ring;

import com.denismasterherobrine.angelring.AngelRing;
import com.denismasterherobrine.angelring.compat.CuriosCompat;
import com.denismasterherobrine.angelring.compat.ExternalMods;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = AngelRing.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(AngelRing.MODID)
public class ItemRing extends Item {

    public ItemRing() {
        super(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC));
    }

    @SubscribeEvent
    public static void sendImc(InterModEnqueueEvent event) {
        if (ExternalMods.CURIOS.isLoaded())
            CuriosCompat.sendImc();
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        if (ExternalMods.CURIOS.isLoaded()) {
            return CuriosCompat.initCapabilities();
        }
        return super.initCapabilities(stack, nbt);
    }
}
