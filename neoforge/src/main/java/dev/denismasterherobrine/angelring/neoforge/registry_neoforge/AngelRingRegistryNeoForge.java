package dev.denismasterherobrine.angelring.neoforge.registry_neoforge;

import dev.denismasterherobrine.angelring.AngelRing;
import dev.denismasterherobrine.angelring.neoforge.item.AngelRingItem;
import dev.denismasterherobrine.angelring.neoforge.item.DiamondRingItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AngelRingRegistryNeoForge {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AngelRing.MOD_ID);

    public static final DeferredItem<Item> ANGEL_RING = ITEMS.register("angel_ring", AngelRingItem::new);
    public static final DeferredItem<Item> DIAMOND_RING = ITEMS.register("diamond_ring", DiamondRingItem::new);

    public static ItemGroup ANGEL_RING_TAB = ItemGroup.create(null, -1)
            .displayName(Text.translatable("itemGroup.angelring3"))
            .icon(() -> new ItemStack(ANGEL_RING.asItem()))
            .entries((displayContext, entries) -> {
                entries.add(ANGEL_RING);
                entries.add(DIAMOND_RING);
            })
            .build();

    public static final DeferredRegister<ItemGroup> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.ITEM_GROUP, AngelRing.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);

        CREATIVE_MODE_TAB.register("angelring3", () -> ANGEL_RING_TAB);
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
