package com.denismasterherobrine.angelring.ring;

import com.denismasterherobrine.angelring.AngelRing;
import com.denismasterherobrine.angelring.compat.CuriosCompat;
import com.denismasterherobrine.angelring.compat.ExternalMods;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.*;
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
            CuriosCompat.sendImc();
    }

    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundNBT unused) {
        if (ExternalMods.CURIOS.isLoaded()) {
            return CuriosCompat.initCapabilities();
        }
        return super.initCapabilities(stack, unused);
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag tooltipFlag) {
        if (!Screen.hasShiftDown() && ExternalMods.TINKERSCONSTRUCT.isLoaded())
            tooltip.add(new StringTextComponent("Hold Shift for more information!").withStyle(TextFormatting.DARK_GRAY));
        if (ExternalMods.TINKERSCONSTRUCT.isLoaded() && Screen.hasShiftDown())
            tooltip.add(new StringTextComponent("Slime Boots doesn't work when wearing Angel Ring. Sorry for inconvenience!").withStyle(TextFormatting.DARK_GREEN));
        //if (Screen.hasShiftDown())
            //tooltip.add(new StringTextComponent("Current Mode: Creative Flight")); // Experience, RF (FE) and Mana (Botania) and probably some other modes coming soonTM.
    }
}
