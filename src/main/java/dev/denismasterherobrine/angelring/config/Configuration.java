package dev.denismasterherobrine.angelring.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Configuration {

    public static final String CATEGORY_GENERAL = "general";
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.BooleanValue BalancedRecipe;
    public static ForgeConfigSpec.IntValue XPCost;
    public static ForgeConfigSpec.IntValue TicksPerDrain;

    public static ForgeConfigSpec.IntValue EnergeticFEPerTick;
    public static ForgeConfigSpec.IntValue EnergeticFECapacity;

    public static ForgeConfigSpec.IntValue LeadstoneFEPerTick;
    public static ForgeConfigSpec.IntValue LeadstoneCapacity;

    public static ForgeConfigSpec.IntValue HardenedFEPerTick;
    public static ForgeConfigSpec.IntValue HardenedCapacity;

    public static ForgeConfigSpec.IntValue ReinforcedFEPerTick;
    public static ForgeConfigSpec.IntValue ReinforcedCapacity;

    public static ForgeConfigSpec.IntValue ResonantFEPerTick;
    public static ForgeConfigSpec.IntValue ResonantCapacity;

    static {
        COMMON_BUILDER.comment("General Angel Ring 2 configuration options.").push(CATEGORY_GENERAL);

        // TODO: Add a config option to switch recipes?
        // BalancedRecipe = COMMON_BUILDER.comment("Define if Angel Ring must have a harder recipe or have a default recipe as it was in versions before 2.0.0.").define("BalancedRecipe", true);

        XPCost = COMMON_BUILDER.comment("Define how much XP will be drained from player when flying by wearing classic Angel Ring? Put 0 if you need to disable XP requirement for flight.").defineInRange("XPCost", 3, 0, 2147483647);
        TicksPerDrain = COMMON_BUILDER.comment("Define how much ticks is required to pass between each XP drain event of classic Angel Ring?").defineInRange("TicksPerDrain", 50, 1, 2147483647);

        EnergeticFEPerTick = COMMON_BUILDER.comment("Define how much FE the Energetic Angel Ring will drain every tick while flying.").defineInRange("EnergeticFEPerTick", 150, 1, 2147483647);
        EnergeticFECapacity = COMMON_BUILDER.comment("Define how much FE the Energetic Angel Ring can store.").defineInRange("EnergeticFECapacity", 3000000, 1, 2147483647);

        LeadstoneFEPerTick = COMMON_BUILDER.comment("Define how much FE the Leadstone Angel Ring will drain every tick while flying.").defineInRange("LeadstoneFEPerTick", 250, 1, 2147483647);
        LeadstoneCapacity = COMMON_BUILDER.comment("Define how much FE the Leadstone Angel Ring can store.").defineInRange("LeadstoneFECapacity", 2500000, 1, 2147483647);

        HardenedFEPerTick = COMMON_BUILDER.comment("Define how much FE the Hardened Angel Ring will drain every tick while flying.").defineInRange("HardenedFEPerTick", 200, 1, 2147483647);
        HardenedCapacity = COMMON_BUILDER.comment("Define how much FE the Hardened Angel Ring can store.").defineInRange("HardenedFECapacity", 5000000, 1, 2147483647);

        ReinforcedFEPerTick = COMMON_BUILDER.comment("Define how much FE the Reinforced Angel Ring will drain every tick while flying.").defineInRange("ReinforcedFEPerTick", 100, 1, 2147483647);
        ReinforcedCapacity = COMMON_BUILDER.comment("Define how much FE the Reinforced Angel Ring can store.").defineInRange("ReinforcedFECapacity", 8000000, 1, 2147483647);

        ResonantFEPerTick = COMMON_BUILDER.comment("Define how much FE the Resonant Angel Ring will drain every tick while flying.").defineInRange("ResonantFEPerTick", 50, 1, 2147483647);
        ResonantCapacity = COMMON_BUILDER.comment("Define how much FE the Resonant Angel Ring can store.").defineInRange("ResonantFECapacity", 16000000, 1, 2147483647);

        COMMON_BUILDER.pop();
        SERVER_CONFIG = COMMON_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .preserveInsertionOrder()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    public ForgeConfigSpec getSpec(){
        return SERVER_CONFIG;
    }
}