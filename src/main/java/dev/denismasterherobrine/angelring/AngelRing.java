package dev.denismasterherobrine.angelring;

import dev.denismasterherobrine.angelring.item.thermal.HardenedAngelRing;
import dev.denismasterherobrine.angelring.item.thermal.LeadstoneAngelRing;
import dev.denismasterherobrine.angelring.item.thermal.ReinforcedAngelRing;
import dev.denismasterherobrine.angelring.item.thermal.ResonantAngelRing;
import dev.denismasterherobrine.angelring.item.vanilla.EnergeticAngelRing;
import dev.denismasterherobrine.angelring.item.vanilla.DiamondRing;
import dev.denismasterherobrine.angelring.item.vanilla.AngelRingItem;
import dev.denismasterherobrine.angelring.register.ItemRegistry;
import dev.denismasterherobrine.angelring.utils.ExternalMods;
import dev.denismasterherobrine.angelring.config.Configuration;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("angelring")
public class AngelRing {
    public static final String MODID = "angelring";
    private static final Logger LOGGER = LogManager.getLogger();

    public AngelRing() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
        ItemRegistry.init();
        Configuration.loadConfig(Configuration.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("angelring2.toml"));
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
