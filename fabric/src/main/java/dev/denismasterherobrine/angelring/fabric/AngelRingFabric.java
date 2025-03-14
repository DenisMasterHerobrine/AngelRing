package dev.denismasterherobrine.angelring.fabric;

import dev.denismasterherobrine.angelring.fabric.registry_fabric.AngelRingRegistryFabric;
import net.fabricmc.api.ModInitializer;

import dev.denismasterherobrine.angelring.AngelRing;

import static dev.denismasterherobrine.angelring.fabric.listener.PlayerJoinListener.onPlayerJoin;

public final class AngelRingFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AngelRing.init();
        AngelRingRegistryFabric.register();

        onPlayerJoin();
    }
}
