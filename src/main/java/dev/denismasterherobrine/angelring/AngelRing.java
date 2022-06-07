package dev.denismasterherobrine.angelring;

import dev.denismasterherobrine.angelring.item.ItemFERing;
import dev.denismasterherobrine.angelring.utils.ExternalMods;
import dev.denismasterherobrine.angelring.config.Configuration;
import dev.denismasterherobrine.angelring.proxy.ClientProxy;
import dev.denismasterherobrine.angelring.proxy.IProxy;
import dev.denismasterherobrine.angelring.proxy.ServerProxy;
import dev.denismasterherobrine.angelring.register.ItemRegistry;
import dev.denismasterherobrine.angelring.item.ItemDiamondRing;
import dev.denismasterherobrine.angelring.item.ItemRing;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("angelring")
public class AngelRing {
    public static final String MODID = "angelring";
    private static final Logger LOGGER = LogManager.getLogger();
    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public AngelRing() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);

        Configuration.loadConfig(Configuration.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("angelring-common.toml"));
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        if (ExternalMods.TINKERSCONSTRUCT.isLoaded()) {
            LOGGER.warn("Tinkers' Construct is loaded! Slime Boots doesn't work with Angel Ring at the moment.");
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void RegisterItems(final RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll
                    (
                            ItemRegistry.ItemRing = new ItemRing().setRegistryName(location("itemring")),
                            ItemRegistry.ItemDiamondRing = new ItemDiamondRing().setRegistryName(location("itemdiamondring")),
                            ItemRegistry.ItemFERing = new ItemFERing().setRegistryName(location("itemfering"))
                    );
        }
        /*
        @SubscribeEvent
        public static void registerDataProviders(final GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();
            System.out.println("Data Gathering event is triggered!");
            if (event.includeServer()){
                generator.addProvider(new ClassicAngelRingRecipeProvider(generator));
            }
        }

         */

        private static ResourceLocation location(String name) {
            return new ResourceLocation(MODID, name);
        }
    }
}
