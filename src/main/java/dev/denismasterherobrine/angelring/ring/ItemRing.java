package dev.denismasterherobrine.angelring.ring;

import dev.denismasterherobrine.angelring.AngelRing;
import dev.denismasterherobrine.angelring.compat.ClassicAngelRingIntegration;
import dev.denismasterherobrine.angelring.config.Configuration;
import dev.denismasterherobrine.angelring.utils.ExternalMods;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
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
        super(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_MISC));
    }

    @SubscribeEvent
    public static void sendImc(InterModEnqueueEvent event) {
        if (ExternalMods.CURIOS.isLoaded())
            ClassicAngelRingIntegration.sendImc();
    }

    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundNBT unused) {
        if (ExternalMods.CURIOS.isLoaded()) {
            return ClassicAngelRingIntegration.initCapabilities();
        }
        return super.initCapabilities(stack, unused);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag tooltipFlag) {
        if (!Screen.hasShiftDown())
            tooltip.add(new StringTextComponent("Hold Shift for more information!").withStyle(TextFormatting.GRAY));
        if (Screen.hasShiftDown() && Configuration.XPCost.get() != 0){
            tooltip.add(new StringTextComponent("Angel Ring drains player's XP over time while flying.").withStyle(TextFormatting.DARK_GREEN));
            tooltip.add(new StringTextComponent("If player's XP reaches less than 1 level,").withStyle(TextFormatting.GRAY));
            tooltip.add(new StringTextComponent("magic will disappear and flight will not be possible.").withStyle(TextFormatting.GRAY));
        }
        if (Screen.hasShiftDown() && Configuration.XPCost.get() == 0){
            tooltip.add(new StringTextComponent("Angel Ring is switched into Classic Mode. Grants creative flight while in Curios slot.").withStyle(TextFormatting.RED));
            tooltip.add(new StringTextComponent("Switch XP draining to more than 0 points in the config to switch to Rebalanced Mode.").withStyle(TextFormatting.RED));
        }
    }
}
