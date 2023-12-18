package dev.denismasterherobrine.angelring.utils;

import net.minecraftforge.fml.ModList;

public enum ExternalMods {
    /*
        This is a list of mod integration for Angel Ring. If any of these mods is present: mod integration will be enabled by default.
    */
    CURIOS("curios"),
    TINKERSCONSTRUCT("tconstruct"),
    THERMALFOUNDATION("thermal"),

    /*
        Check if Experience Bug Fix is already installed, if not, we will patch the experience bar to sync it properly for Angel Ring item.
    */
    EXPERIENCEBUGFIX("experiencebugfix");

    private final boolean loaded;

    ExternalMods(String modid) {
        this.loaded = ModList.get() != null && ModList.get().getModContainerById(modid).isPresent();
    }

    public boolean isLoaded() {
        return this.loaded;
    }
}
