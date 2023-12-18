package dev.denismasterherobrine.angelring.register;

import dev.denismasterherobrine.angelring.AngelRing;
import dev.denismasterherobrine.angelring.item.compat.thermal.HardenedAngelRing;
import dev.denismasterherobrine.angelring.item.compat.thermal.LeadstoneAngelRing;
import dev.denismasterherobrine.angelring.item.compat.thermal.ReinforcedAngelRing;
import dev.denismasterherobrine.angelring.item.compat.thermal.ResonantAngelRing;
import dev.denismasterherobrine.angelring.item.vanilla.AngelRingItem;
import dev.denismasterherobrine.angelring.item.vanilla.crafting.DiamondRing;
import dev.denismasterherobrine.angelring.item.vanilla.EnergeticAngelRing;

import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static dev.denismasterherobrine.angelring.AngelRing.MODID;

@Mod.EventBusSubscriber(modid = AngelRing.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> DIAMOND_RING = ITEMS.register("diamond_ring", DiamondRing::new);
    public static final RegistryObject<Item> ANGEL_RING = ITEMS.register("angel_ring", AngelRingItem::new);
    public static final RegistryObject<Item> ENERGETIC_ANGEL_RING = ITEMS.register("energetic_angel_ring", EnergeticAngelRing::new);

    public static final RegistryObject<Item> LEADSTONE_ANGEL_RING = ITEMS.register("leadstone_angel_ring", LeadstoneAngelRing::new);
    public static final RegistryObject<Item> HARDENED_ANGEL_RING = ITEMS.register("hardened_angel_ring", HardenedAngelRing::new);
    public static final RegistryObject<Item> REINFORCED_ANGEL_RING = ITEMS.register("reinforced_angel_ring", ReinforcedAngelRing::new);
    public static final RegistryObject<Item> RESONANT_ANGEL_RING = ITEMS.register("resonant_angel_ring", ResonantAngelRing::new);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}