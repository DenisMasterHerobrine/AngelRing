package dev.denismasterherobrine.angelring.item;

import dev.denismasterherobrine.angelring.AngelRing;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(AngelRing.MODID)
public class ItemDiamondRing extends Item {
        public ItemDiamondRing() {
            super(new Properties().stacksTo(1).tab(ItemGroup.TAB_MISC));
        }
}
