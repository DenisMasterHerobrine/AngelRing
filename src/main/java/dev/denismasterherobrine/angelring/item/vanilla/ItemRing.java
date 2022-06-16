package dev.denismasterherobrine.angelring.item.vanilla;

import dev.denismasterherobrine.angelring.AngelRing;
import dev.denismasterherobrine.angelring.compat.curios.ClassicAngelRingIntegration;
import dev.denismasterherobrine.angelring.config.Configuration;
import dev.denismasterherobrine.angelring.utils.AngelRingTab;
import dev.denismasterherobrine.angelring.utils.ExternalMods;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = AngelRing.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(AngelRing.MODID)
public class ItemRing extends Item {
    public ItemRing() {
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
            return ClassicAngelRingIntegration.initCapabilities();
        }
        return super.initCapabilities(stack, unused);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level world, List<Component> tooltip, TooltipFlag tooltipFlag) {
        if (!Screen.hasShiftDown())
            tooltip.add(new TranslatableComponent("item.angelring.itemring.tooltip").withStyle(ChatFormatting.GRAY));
        if (Screen.hasShiftDown() && Configuration.XPCost.get() != 0){
            tooltip.add(new TranslatableComponent("item.angelring.itemring.desc0").withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(new TranslatableComponent("item.angelring.itemring.desc1").withStyle(ChatFormatting.GRAY));
            tooltip.add(new TranslatableComponent("item.angelring.itemring.desc2").withStyle(ChatFormatting.GRAY));
        }
        if (Screen.hasShiftDown() && Configuration.XPCost.get() == 0){
            tooltip.add(new TranslatableComponent("item.angelring.itemring.classic.desc0").withStyle(ChatFormatting.RED));
            tooltip.add(new TranslatableComponent("item.angelring.itemring.classic.desc1").withStyle(ChatFormatting.RED));
        }
    }
}
