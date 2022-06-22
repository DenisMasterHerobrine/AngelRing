package dev.denismasterherobrine.angelring.utils;

import dev.denismasterherobrine.angelring.register.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class AngelRingTab {
    public static final ItemGroup ANGELRING2 = new ItemGroup("angelring2") {
        @Override
        public ItemStack makeIcon() {
            return ItemRegistry.ItemRing.getDefaultInstance();
        }
    };
}
