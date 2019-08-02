package com.denismasterherobrine.angelring.ring;

import com.denismasterherobrine.angelring.AngelRing;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = AngelRing.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(AngelRing.MODID)
public class ItemRing extends Item {
    public ItemRing() {
        super(new Item.Properties().maxStackSize(1));
    }
}

