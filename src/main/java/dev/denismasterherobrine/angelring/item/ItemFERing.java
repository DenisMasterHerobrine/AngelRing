package dev.denismasterherobrine.angelring.item;

import dev.denismasterherobrine.angelring.AngelRing;
import dev.denismasterherobrine.angelring.compat.FEAngelRingIntegration;
import dev.denismasterherobrine.angelring.config.Configuration;
import dev.denismasterherobrine.angelring.utils.ExternalMods;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = AngelRing.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(AngelRing.MODID)
public class ItemFERing extends Item {
    public ItemFERing() {
        super(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_MISC));
    }

    @SubscribeEvent
    public static void sendImc(InterModEnqueueEvent event) {
        if (ExternalMods.CURIOS.isLoaded())
            FEAngelRingIntegration.sendImc();
    }

    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundNBT unused) {
        if (ExternalMods.CURIOS.isLoaded()) {
            return FEAngelRingIntegration.initCapabilities(stack);
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
            tooltip.add(new TranslationTextComponent("item.angelring.itemfering.tooltip").withStyle(TextFormatting.GRAY));
        else {
            tooltip.add(new TranslationTextComponent("item.angelring.itemfering.desc0").withStyle(TextFormatting.DARK_GREEN));
            tooltip.add(new TranslationTextComponent("item.angelring.itemfering.desc1", energy.getEnergyStored(), energy.getMaxEnergyStored()).withStyle(TextFormatting.GRAY));
        }
    }

}
