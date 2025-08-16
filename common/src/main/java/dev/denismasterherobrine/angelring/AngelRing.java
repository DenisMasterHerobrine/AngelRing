package dev.denismasterherobrine.angelring;

import dev.denismasterherobrine.angelring.configuration.Configuration;
import dev.denismasterherobrine.haydenapi.configuration.api.ConfigurationAPI;
import dev.denismasterherobrine.haydenapi.configuration.exception.ConfigurationException;

import java.nio.file.Path;
import java.util.logging.Logger;

public final class AngelRing {
    public static final String MOD_ID = "angelring";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    public static void init() {
        try {
            ConfigurationAPI.createConfig(Path.of("config").resolve("angelring.json5").toString(), Configuration.class);
            ConfigurationAPI.reloadConfig(Path.of("config").resolve("angelring.json5").toString(), Configuration.class);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}