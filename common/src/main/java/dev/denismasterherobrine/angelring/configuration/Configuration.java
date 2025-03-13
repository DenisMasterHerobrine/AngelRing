package dev.denismasterherobrine.angelring.configuration;

import dev.denismasterherobrine.haydenapi.configuration.annotation.Comment;
import dev.denismasterherobrine.haydenapi.configuration.annotation.Config;
import dev.denismasterherobrine.haydenapi.configuration.annotation.Entry;

@Config
public class Configuration {
    @Entry
    @Comment("Defines a type of behaviour for the Angel Ring. CLASSIC means that the ring will not consume anything. DURABILITY means that the ring will consume durability. XP means that the ring will consume XP. MANA means that the ring will consume mana. RF means that the ring will consume RF energy and Angel Ring will be turned to Energetic one. [Accepts: CLASSIC, DURABILITY, XP, MANA, RF]")
    public static String AngelRingMode = "CLASSIC";

    @Entry
    @Comment("Defines how much XP will be drained from player when flying by wearing Angel Ring. Must be an Integer value, otherwise the game will crash. [Min: -2147483648, Max: 2147483647]")
    public static int XPConsumptionRate = 3;

    @Entry
    @Comment("Defines how much ticks is required to pass between each XP drain event of Angel Ring. Must be an Integer value, otherwise the game will crash. [Min: -2147483648, Max: 2147483647]")
    public static int XPTickingRate = 20;

    @Entry
    @Comment("Defines how much durability will be drained from player when flying by wearing Angel Ring. Must be an Integer value, otherwise the game will crash. [Min: -2147483648, Max: 2147483647]")
    public static int DurabilityConsumptionRate = 1;

    @Entry
    @Comment("Defines how much ticks is required to pass between each durability drain event of Angel Ring. Must be an Integer value, otherwise the game will crash. [Min: -2147483648, Max: 2147483647]")
    public static int DurabilityTickingRate = 20;

    @Entry
    @Comment("Defines how much durability the Angel Ring will have. Must be an Integer value, otherwise the game will crash. [Min: -2147483648, Max: 2147483647]")
    public static int DurabilityAmount = 2048;
}
