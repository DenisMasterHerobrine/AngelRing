package dev.denismasterherobrine.angelring.fabric.item;

import dev.denismasterherobrine.angelring.AngelRing;
import dev.denismasterherobrine.angelring.configuration.Configuration;
import dev.denismasterherobrine.angelring.utils.AngelRingMode;
import dev.denismasterherobrine.angelring.utils.AngelRingModeSelector;
import dev.denismasterherobrine.angelring.utils.ExperienceUtils;
import dev.denismasterherobrine.flightapi.api.FlightAPI;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AngelRingItem extends TrinketItem {
    private static int ticksDrained;
    private static final Map<UUID, Boolean> flightPermissionMap = new HashMap<>();

    public AngelRingItem() {
        super(new Settings()
                .maxCount(1)
                .rarity(Rarity.EPIC)
                .maxDamage(Configuration.DurabilityAmount)
                .fireproof()
        );
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity.getEntityWorld().isClient) return;

        if (entity instanceof ServerPlayerEntity player) {
            FlightAPI.requestFlight(AngelRing.MOD_ID, player);
        }
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity.getEntityWorld().isClient) return;

        if (entity instanceof ServerPlayerEntity player) {
            UUID playerUUID = player.getGameProfile().getId();
            AngelRingMode mode = AngelRingModeSelector.getMode();

            if (flightPermissionMap.containsKey(playerUUID)) {
                if ((mode == AngelRingMode.XP) && (Boolean.TRUE.equals(flightPermissionMap.get(playerUUID))) && (ExperienceUtils.getPlayerXP(player) >= Configuration.XPConsumptionRate)) {
                    FlightAPI.requestFlight(AngelRing.MOD_ID, player);
                    player.sendMessage(Text.translatable("angelring.xp.flight_restored").withColor(TextColor.fromRgb(9229895).getRgb()), true);
                    flightPermissionMap.put(playerUUID, false);
                }
            }

            FlightAPI.getCurrentOwner(playerUUID).ifPresent(owner -> {
                if (mode == AngelRingMode.XP) {
                    ticksDrained++;

                    if (ticksDrained > Configuration.XPTickingRate) {
                        if (!player.isOnGround()) {
                            if (ExperienceUtils.getPlayerXP(player) >= Configuration.XPConsumptionRate) {
                                ExperienceUtils.addPlayerXP(player, -Configuration.XPConsumptionRate);
                            } else {
                                FlightAPI.releaseFlight(AngelRing.MOD_ID, player);
                                player.sendMessage(Text.translatable("angelring.xp.no_more_xp").withColor(TextColor.fromRgb(14056293).getRgb()), true);
                                flightPermissionMap.put(playerUUID, true);
                            }
                        }

                        ticksDrained = 0;
                    }
                }

                if (mode == AngelRingMode.DURABILITY) {
                    ticksDrained++;

                    if (ticksDrained >= Configuration.DurabilityTickingRate) {
                        if (!player.isOnGround()) {
                            if (stack.getDamage() >= stack.getMaxDamage() * 0.95) {
                                player.sendMessage(Text.translatable("angelring.durability.low_durability").withColor(TextColor.fromRgb(14056293).getRgb()), true);
                            }

                            stack.setDamage(stack.getDamage() + Configuration.DurabilityConsumptionRate);
                        }

                        if (stack.getDamage() >= stack.getMaxDamage()) {
                            player.playSoundToPlayer(stack.getBreakSound(), SoundCategory.MASTER, 1.0f, 1.0f);
                            stack.decrement(1);

                            FlightAPI.releaseFlight(AngelRing.MOD_ID, player);
                            player.sendMessage(Text.translatable("angelring.durability.broken").withColor(TextColor.fromRgb(14691372).getRgb()), true);
                        }

                        ticksDrained = 0;
                    }
                }
            });
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity.getEntityWorld().isClient) return;

        if (entity instanceof ServerPlayerEntity player) {
            FlightAPI.releaseFlight(AngelRing.MOD_ID, player);
        }
    }

    @Override
    public SoundEvent getBreakSound() {
        return SoundEvent.of(Identifier.of("minecraft", "block.glass.break"));
    }

    @Override
    public RegistryEntry<SoundEvent> getEquipSound(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return RegistryEntry.of(SoundEvent.of(Identifier.of("minecraft", "item.armor.equip_elytra")));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (AngelRingModeSelector.getMode() == AngelRingMode.XP) {
            tooltip.add(Text.translatable("item.angelring.angel_ring.desc0"));
            tooltip.add(Text.translatable("item.angelring.angel_ring.desc1"));
            tooltip.add(Text.translatable("item.angelring.angel_ring.desc2"));
        } else if (AngelRingModeSelector.getMode() == AngelRingMode.DURABILITY) {
            tooltip.add(Text.translatable("angelring.durability.tooltip"));
        } else if (AngelRingModeSelector.getMode() == AngelRingMode.CLASSIC) {
            tooltip.add(Text.translatable("item.angelring.angel_ring.classic.desc0_1"));
        }
    }
}
