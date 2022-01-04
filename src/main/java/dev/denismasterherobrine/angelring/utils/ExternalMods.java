package dev.denismasterherobrine.angelring.utils;

import net.minecraftforge.fml.ModList;

public enum ExternalMods {
    CURIOS("curios"),
    TINKERSCONSTRUCT("tconstruct");

    private final boolean loaded;

    ExternalMods(String modid) {
        this.loaded = ModList.get() != null && ModList.get().getModContainerById(modid).isPresent();
    }

    public boolean isLoaded() {
        return this.loaded;
    }
}
