package dev.denismasterherobrine.angelring.utils;

import dev.denismasterherobrine.angelring.register.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AngelRingTab {
    public static final CreativeModeTab ANGELRING2 = new CreativeModeTab("angelring2") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return ItemRegistry.ANGEL_RING.get().getDefaultInstance();
        }
    };
}
