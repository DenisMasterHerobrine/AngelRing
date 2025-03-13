package dev.denismasterherobrine.angelring.utils;

import java.util.Locale;
import dev.denismasterherobrine.angelring.configuration.Configuration;

public class AngelRingModeSelector {
    public static AngelRingMode getMode() {
        String mode = Configuration.AngelRingMode.toUpperCase(Locale.ROOT);
        return switch (mode.toUpperCase(Locale.ROOT)) {
            case "DURABILITY" -> AngelRingMode.DURABILITY;
            case "XP" -> AngelRingMode.XP;
            case "MANA" -> AngelRingMode.MANA;
            case "RF" -> AngelRingMode.RF;
            default -> AngelRingMode.CLASSIC;
        };
    }
}
