package dev.denismasterherobrine.angelring.utils;

import dev.denismasterherobrine.angelring.register.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AngelRingTab {
    public static final CreativeModeTab ANGELRING2 = new CreativeModeTab("angelring2") {
        @Override
        public ItemStack makeIcon() {
            return ItemRegistry.ItemRing.getDefaultInstance();
        }
    };
}
