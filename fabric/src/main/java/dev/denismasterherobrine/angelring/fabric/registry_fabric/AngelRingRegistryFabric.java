package dev.denismasterherobrine.angelring.fabric.registry_fabric;

import dev.denismasterherobrine.angelring.AngelRing;
import dev.denismasterherobrine.angelring.fabric.item.AngelRingItem;
import dev.denismasterherobrine.angelring.fabric.item.DiamondRingItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AngelRingRegistryFabric {
    public static final Item ANGEL_RING = new AngelRingItem();
    public static final Item DIAMOND_RING = new DiamondRingItem();

    public static ItemGroup ANGEL_RING_TAB = ItemGroup.create(null, -1)
            .displayName(Text.translatable("itemGroup.angelring3"))
            .icon(() -> new ItemStack(ANGEL_RING))
            .entries((displayContext, entries) -> {
                entries.add(ANGEL_RING);
                entries.add(DIAMOND_RING);
            })
            .build();

    public static void registerItems() {
        Registry.register(Registries.ITEM, Identifier.of(AngelRing.MOD_ID, "angel_ring"), ANGEL_RING);
        Registry.register(Registries.ITEM, Identifier.of(AngelRing.MOD_ID, "diamond_ring"), DIAMOND_RING);

        Registry.register(Registries.ITEM_GROUP, Identifier.of(AngelRing.MOD_ID, "angelring3"), AngelRingRegistryFabric.ANGEL_RING_TAB);
    }

    public static void register() {
        registerItems();
    }
}
