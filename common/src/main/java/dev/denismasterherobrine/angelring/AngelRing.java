package dev.denismasterherobrine.angelring;

import dev.denismasterherobrine.angelring.configuration.Configuration;
import dev.denismasterherobrine.haydenapi.configuration.api.ConfigurationAPI;
import dev.denismasterherobrine.haydenapi.configuration.exception.ConfigurationException;

public final class AngelRing {
    public static final String MOD_ID = "angelring";

    public static void init() {
        try {
            ConfigurationAPI.createConfig(".\\config\\angelring.json5", Configuration.class);
            ConfigurationAPI.reloadConfig(".\\config\\angelring.json5", Configuration.class);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}