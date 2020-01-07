package com.denismasterherobrine.angelring.compat;

import net.minecraftforge.fml.ModList;

public enum ExternalMods {
    CURIOS("curios"),
    EVERLASTINGABILITIES("everlastingabilities");

    private final boolean loaded;

    ExternalMods(String modid) {
        this.loaded = ModList.get() != null && ModList.get().getModContainerById(modid).isPresent();
    }

    public boolean isLoaded() {
        return this.loaded;
    }
}
