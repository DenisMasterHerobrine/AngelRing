package dev.denismasterherobrine.angelring.item.thermal;

import dev.denismasterherobrine.angelring.compat.curios.ClassicAngelRingIntegration;
import dev.denismasterherobrine.angelring.compat.thermal.HardenedAngelRingIntegration;
import dev.denismasterherobrine.angelring.utils.AngelRingTab;
import dev.denismasterherobrine.angelring.utils.ExternalMods;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

import javax.annotation.Nullable;
import java.util.List;

public class HardenedAngelRing extends Item {
    public HardenedAngelRing() {
        super(new Item.Properties().stacksTo(1).tab(AngelRingTab.ANGELRING2));
    }

    @SubscribeEvent
    public static void sendImc(InterModEnqueueEvent event) {
        if (ExternalMods.CURIOS.isLoaded())
            ClassicAngelRingIntegration.sendImc();
    }

    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundNBT unused) {
        if (ExternalMods.CURIOS.isLoaded()) {
            return HardenedAngelRingIntegration.initCapabilities(stack);
        }
        return super.initCapabilities(stack, unused);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
        return (energy.getEnergyStored() < energy.getMaxEnergyStored());
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY, null)
                .map(e -> 1D - (e.getEnergyStored() / (double) e.getMaxEnergyStored()))
                .orElse(0D);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag tooltipFlag) {
        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
        if (!Screen.hasShiftDown())
            tooltip.add(new TranslationTextComponent("item.angelring.energetic_angel_ring.tooltip").withStyle(TextFormatting.GRAY));
        else {
            tooltip.add(new TranslationTextComponent("item.angelring.energetic_angel_ring.desc0").withStyle(TextFormatting.GOLD));
            tooltip.add(new TranslationTextComponent("item.angelring.energetic_angel_ring.desc1", energy.getEnergyStored(), energy.getMaxEnergyStored()).withStyle(TextFormatting.GRAY));
        }
    }
}
