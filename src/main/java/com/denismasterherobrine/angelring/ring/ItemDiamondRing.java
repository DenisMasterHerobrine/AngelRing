package com.denismasterherobrine.angelring.ring;

import com.denismasterherobrine.angelring.AngelRing;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(AngelRing.MODID)
public class ItemDiamondRing extends Item {
        public ItemDiamondRing() {
            super(new Item.Properties().maxStackSize(1).group(ItemGroup.MISC));
        }
}
