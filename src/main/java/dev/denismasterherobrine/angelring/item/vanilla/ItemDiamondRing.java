package dev.denismasterherobrine.angelring.item.vanilla;

import dev.denismasterherobrine.angelring.AngelRing;
import dev.denismasterherobrine.angelring.utils.AngelRingTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(AngelRing.MODID)
public class ItemDiamondRing extends Item {
        public ItemDiamondRing() {
            super(new Properties().stacksTo(1).tab(AngelRingTab.ANGELRING2));
        }
}
