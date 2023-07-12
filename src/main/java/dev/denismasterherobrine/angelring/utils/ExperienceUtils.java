/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Open Mods
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package dev.denismasterherobrine.angelring.utils;

import net.minecraft.world.entity.player.Player;

public class ExperienceUtils {

    /**
     * Be warned, minecraft doesn't update experienceTotal properly, so we have
     * to do this.
     *
     * @param player
     * @return
     */

    public static int getPlayerXP(Player player) {
        return (int)(ExperienceUtils.getExperienceForLevel(player.experienceLevel) + (player.experienceProgress * player.getXpNeededForNextLevel()));
    }

    public static void addPlayerXP(Player player, int amount) {
        int experience = getPlayerXP(player);
        player.giveExperiencePoints(amount);
    }

    public static int xpBarCap(int level) {
        if (level >= 30)
            return 112 + (level - 30) * 9;

        if (level >= 15)
            return 37 + (level - 15) * 5;

        return 7 + level * 2;
    }

    private static int sum(int n, int a0, int d) {
        return n * (2 * a0 + (n - 1) * d) / 2;
    }

    public static int getExperienceForLevel(int level) {
        if (level == 0) return 0;
        if (level <= 15) return sum(level, 7, 2);
        if (level <= 30) return 315 + sum(level - 15, 37, 5);
        return 1395 + sum(level - 30, 112, 9);
    }

    public static int getLevelForExperience(int targetXp) {
        int level = 0;
        while (true) {
            final int xpToNextLevel = xpBarCap(level);
            if (targetXp < xpToNextLevel) return level;
            level++;
            targetXp -= xpToNextLevel;
        }
    }

    public static float calculateStoredLevels(int storedXP)
    {
        float storedLevels = 0.0F;
        int xp = storedXP;

        while(xp > 0)
        {
            int xpToNextLevel = ExperienceUtils.xpBarCap((int)storedLevels);

            if(xp < xpToNextLevel)
            {
                storedLevels += (float)xp / xpToNextLevel;
                break;
            }

            xp -= xpToNextLevel;
            storedLevels += 1.0F;
        }

        return storedLevels;
    }

    /**
     * Gets the amount of XP needed until reaching the next level
     * @param currentXP The XP that the player already has
     * @return
     */
    public static int getXPToNextLevel(int currentXP)
    {
        int level = ExperienceUtils.getLevelForExperience(currentXP);
        int nextLevelXP = ExperienceUtils.getExperienceForLevel(level + 1);

        return nextLevelXP - currentXP;
    }

    // Solution found somewhere on the Forge Forum.
    public static void decreaseXP(Player player, float amount)
    {
        if (player.totalExperience - amount <= 0)
        {
            player.experienceLevel = 0;
            player.experienceProgress = 0;
            player.totalExperience = 0;
            return;
        }

        player.totalExperience -= amount;

        if (player.experienceProgress * (float)ExperienceUtils.xpBarCap(player.experienceLevel) <= amount)
        {
            amount -= player.experienceProgress * (float)ExperienceUtils.xpBarCap(player.experienceLevel);
            player.experienceProgress = 1.0f;
            player.experienceLevel--;
        }

        while (ExperienceUtils.xpBarCap(player.experienceLevel) < amount)
        {
            amount -= ExperienceUtils.xpBarCap(player.experienceLevel);
            player.experienceLevel--;
        }

        player.experienceProgress -= (float) amount / (float)ExperienceUtils.xpBarCap(player.experienceLevel);
    }
}