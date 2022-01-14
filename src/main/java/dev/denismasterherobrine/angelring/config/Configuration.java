package dev.denismasterherobrine.angelring.config;

import dev.denismasterherobrine.angelring.AngelRing;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Configuration {
    public static class ClientConfiguration {}

    public static class CommonConfiguration {
        public static BooleanValue BalancedRecipe;

        public static IntValue XPCost;
        public static IntValue TicksPerDrain;

        // TODO: BalancedRecipe feature
        // Add ServerConfiguration for XPCost and TicksPerDrain
        CommonConfiguration(ForgeConfigSpec.Builder builder){
            builder.comment("General Angel Ring 2 configuration options")
                    .push("General");

            XPCost = builder
                    .comment("Define how much XP will be drained from player when flying by wearing classic Angel Ring? Put 0 if you need to disable XP requirement for flight. [Default: 3]")
                    .defineInRange("XPCost", 3, 0, 2147483647);

            TicksPerDrain = builder
                    .comment("Define how much ticks is required to pass between each XP drain event of classic Angel Ring? [Default: 100]")
                    .defineInRange("TicksPerDrain", 100, 1, 2147483647);

            builder.pop();
        }

    }

    public static class ServerConfiguration {}

    public static final ForgeConfigSpec commonSpec;
    public static final CommonConfiguration COMMON;
    static {
        final Pair<CommonConfiguration, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfiguration::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
        AngelRing.LOGGER.debug("Loaded Angel Ring's config file {}", configEvent.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
        AngelRing.LOGGER.fatal("Angel Ring's config just got changed on the file system!");
    }

}