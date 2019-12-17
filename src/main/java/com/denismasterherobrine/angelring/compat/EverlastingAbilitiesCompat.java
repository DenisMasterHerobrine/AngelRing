package com.denismasterherobrine.angelring.compat;

import net.minecraft.entity.player.PlayerEntity;
import org.cyclops.everlastingabilities.Reference;

public class EverlastingAbilitiesCompat {
    private static final String PLAYER_NBT_KEY = Reference.MOD_ID + ":" + "lastFlight";

    public static boolean getAllowFlying(PlayerEntity player) {
        return player.getPersistentData().contains(PLAYER_NBT_KEY);
    }
}
