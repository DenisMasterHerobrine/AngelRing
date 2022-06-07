package dev.denismasterherobrine.angelring.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Configuration {

    public static final String CATEGORY_GENERAL = "general";
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.BooleanValue BalancedRecipe;
    public static ForgeConfigSpec.IntValue XPCost;
    public static ForgeConfigSpec.IntValue TicksPerDrain;

    public static ForgeConfigSpec.IntValue EnergeticFEPerTick;
    public static ForgeConfigSpec.IntValue EnergeticFECapacity;

    public static ForgeConfigSpec.IntValue LeadstoneFEPerTick;
    public static ForgeConfigSpec.IntValue LeadstoneCapacity;


    static {
        COMMON_BUILDER.comment("General Angel Ring 2 configuration options.").push(CATEGORY_GENERAL);

        // TODO: Add a config option to switch recipes?
        // BalancedRecipe = COMMON_BUILDER.comment("Define if Angel Ring must have a harder recipe or have a default recipe as it was in versions before 2.0.0.").define("BalancedRecipe", true);

        XPCost = COMMON_BUILDER.comment("Define how much XP will be drained from player when flying by wearing classic Angel Ring? Put 0 if you need to disable XP requirement for flight.").defineInRange("XPCost", 3, 0, 2147483647);
        TicksPerDrain = COMMON_BUILDER.comment("Define how much ticks is required to pass between each XP drain event of classic Angel Ring?").defineInRange("TicksPerDrain", 50, 1, 2147483647);

        EnergeticFEPerTick = COMMON_BUILDER.comment("Define how much FE the Energetic Angel Ring will drain every tick while flying.").defineInRange("EnergeticFEPerTick", 50, 1, 2147483647);
        EnergeticFECapacity = COMMON_BUILDER.comment("Define how much FE the Energetic Angel Ring can store.").defineInRange("FECapacity", 1000000, 1, 2147483647);

        LeadstoneFEPerTick = COMMON_BUILDER.comment("Define how much FE the Energetic Angel Ring will drain every tick while flying.").defineInRange("LeadstoneFEPerTick", 200, 1, 2147483647);
        LeadstoneCapacity = COMMON_BUILDER.comment("Define how much FE the Energetic Angel Ring can store.").defineInRange("FECapacity", 2500000, 1, 2147483647);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }
}