package dev.denismasterherobrine.angelring;

import dev.denismasterherobrine.angelring.register.ItemRegistry;
import dev.denismasterherobrine.angelring.utils.ExternalMods;
import dev.denismasterherobrine.angelring.config.Configuration;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("angelring")
public class AngelRing {
    public static final String MODID = "angelring";
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Configuration SERVER_CONFIG = new Configuration();

    public AngelRing() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);

        ItemRegistry.init();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG.getSpec());
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        if (ExternalMods.TINKERSCONSTRUCT.isLoaded()) {
            LOGGER.warn("Tinkers' Construct is loaded! Slime Boots doesn't work with Angel Ring at the moment.");
        }
    }
}
