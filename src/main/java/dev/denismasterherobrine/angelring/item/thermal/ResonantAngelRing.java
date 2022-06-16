package dev.denismasterherobrine.angelring.item.thermal;

import dev.denismasterherobrine.angelring.compat.curios.ClassicAngelRingIntegration;
import dev.denismasterherobrine.angelring.compat.thermal.ResonantAngelRingIntegration;
import dev.denismasterherobrine.angelring.utils.AngelRingTab;
import dev.denismasterherobrine.angelring.utils.ExternalMods;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ResonantAngelRing extends Item {
    public ResonantAngelRing() {
        super(new Item.Properties().stacksTo(1).tab(AngelRingTab.ANGELRING2));
    }

    @SubscribeEvent
    public static void sendImc(InterModEnqueueEvent event) {
        if (ExternalMods.CURIOS.isLoaded())
            ClassicAngelRingIntegration.sendImc();
    }

    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundTag unused) {
        if (ExternalMods.CURIOS.isLoaded()) {
            return ResonantAngelRingIntegration.initCapabilities(stack);
        }
        return super.initCapabilities(stack, unused);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
        return (energy.getEnergyStored() < energy.getMaxEnergyStored());
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY, null)
                .map(e -> Math.min(13 * e.getEnergyStored() / e.getMaxEnergyStored(), 13))
                .orElse(0);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag tooltipFlag) {
        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
        if (!Screen.hasShiftDown())
            tooltip.add(Component.translatable("item.angelring.energetic_angel_ring.tooltip").withStyle(ChatFormatting.GRAY));
        else {
            tooltip.add(Component.translatable("item.angelring.energetic_angel_ring.desc0").withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.translatable("item.angelring.energetic_angel_ring.desc1", energy.getEnergyStored(), energy.getMaxEnergyStored()).withStyle(ChatFormatting.GRAY));
        }
    }
}
