package com.denismasterherobrine.angelring.compat;

import net.minecraft.entity.player.PlayerEntity;

public class EverlastingAbilitiesCompat {
    private static final String PLAYER_NBT_KEY = "everlastingabilities:" + "lastFlight";

    public static boolean getAllowFlying(PlayerEntity player) {
        return player.getPersistentData().contains(PLAYER_NBT_KEY);
    }
}
