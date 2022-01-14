package dev.denismasterherobrine.angelring.ring;

import dev.denismasterherobrine.angelring.AngelRing;
import dev.denismasterherobrine.angelring.compat.ClassicAngelRingIntegration;
import dev.denismasterherobrine.angelring.config.Configuration;
import dev.denismasterherobrine.angelring.utils.ExternalMods;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@Mod.EventBusSubscriber(modid = AngelRing.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(AngelRing.MODID)
public class ItemRing extends Item {

    public ItemRing() {
        super(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC));
    }

    @SubscribeEvent
    public static void sendImc(InterModEnqueueEvent event) {
        if (ExternalMods.CURIOS.isLoaded())
            ClassicAngelRingIntegration.sendImc();
    }

    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundTag unused) {
        if (ExternalMods.CURIOS.isLoaded()) {
            return ClassicAngelRingIntegration.initCapabilities();
        }
        return super.initCapabilities(stack, unused);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable net.minecraft.world.level.Level world, List<Component> tooltip, TooltipFlag tooltipFlag) {
        if (!Screen.hasShiftDown())
            tooltip.add(new TextComponent("Hold Shift for more information!").withStyle(ChatFormatting.GRAY));
        if (Screen.hasShiftDown() && Configuration.CommonConfiguration.XPCost.get() != 0){
            tooltip.add(new TextComponent("Angel Ring drains player's XP over time while flying.").withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(new TextComponent("If player's XP reaches less than 1 level,").withStyle(ChatFormatting.GRAY));
            tooltip.add(new TextComponent("magic will disappear and flight will not be possible.").withStyle(ChatFormatting.GRAY));
        }
        if (Screen.hasShiftDown() && Configuration.CommonConfiguration.XPCost.get() == 0){
            tooltip.add(new TextComponent("Angel Ring is switched into Classic Mode. Grants creative flight while in Curios slot.").withStyle(ChatFormatting.RED));
            tooltip.add(new TextComponent("Switch XP draining to more than 0 points in the config to switch to Rebalanced Mode.").withStyle(ChatFormatting.RED));
        }
    }
}
