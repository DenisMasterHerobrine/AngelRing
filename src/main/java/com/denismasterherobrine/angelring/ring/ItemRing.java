package com.denismasterherobrine.angelring.ring;

import com.denismasterherobrine.angelring.AngelRing;
import com.denismasterherobrine.angelring.compat.CuriosCompat;
import com.denismasterherobrine.angelring.compat.ExternalMods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = AngelRing.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(AngelRing.MODID)
public class ItemRing extends Item {

    public ItemRing() {
        super(new Item.Properties().maxStackSize(1).group(ItemGroup.MISC));
    }

    @SubscribeEvent
    public static void sendImc(InterModEnqueueEvent event) {
        if (ExternalMods.CURIOS.isLoaded())
            CuriosCompat.sendImc();
    }

    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundNBT unused) {
        if (ExternalMods.CURIOS.isLoaded()) {
            return CuriosCompat.initCapabilities();
        }
        return super.initCapabilities(stack, unused);
    }
}
