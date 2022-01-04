package dev.denismasterherobrine.angelring.ring;

import dev.denismasterherobrine.angelring.AngelRing;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(AngelRing.MODID)
public class ItemDiamondRing extends Item {
        public ItemDiamondRing() {
            super(new Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC));
        }
}
