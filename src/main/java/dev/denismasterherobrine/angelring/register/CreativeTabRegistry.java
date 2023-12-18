package dev.denismasterherobrine.angelring.register;

import dev.denismasterherobrine.angelring.AngelRing;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AngelRing.MODID);

    public static final RegistryObject<CreativeModeTab> ANGEL_RING_TAB = CREATIVE_MODE_TAB_REGISTRY.register("angel_ring_tab", () -> CreativeModeTab.builder()
            .title(Component.literal("Angel Ring 2"))
            .icon(() -> new ItemStack(ItemRegistry.ANGEL_RING.get().asItem()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .displayItems((featureFlags, output) -> {
                output.accept(ItemRegistry.DIAMOND_RING.get());
                output.accept(ItemRegistry.ANGEL_RING.get());
                output.accept(ItemRegistry.ENERGETIC_ANGEL_RING.get());
                output.accept(ItemRegistry.LEADSTONE_ANGEL_RING.get());
                output.accept(ItemRegistry.HARDENED_ANGEL_RING.get());
                output.accept(ItemRegistry.REINFORCED_ANGEL_RING.get());
                output.accept(ItemRegistry.RESONANT_ANGEL_RING.get());
            })
            .build()
    );

    public static void init() {
        CREATIVE_MODE_TAB_REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
