package dev.denismasterherobrine.angelring.neoforge.listener;

import dev.denismasterherobrine.angelring.AngelRing;
import dev.denismasterherobrine.flightapi.api.FlightAPI;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = "angelring", bus = EventBusSubscriber.Bus.GAME)
public class PlayerJoinListener {
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity entity = event.getEntity();

        if (entity instanceof ServerPlayerEntity player) {
            FlightAPI.getCurrentOwner(player.getGameProfile().getId()).ifPresent(owner -> {
                FlightAPI.requestFlight(AngelRing.MOD_ID, player);
            });
        }
    }
}
