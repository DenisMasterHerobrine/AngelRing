package dev.denismasterherobrine.angelring.api;

import dev.denismasterherobrine.angelring.register.ItemRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

public class AngelRingAPI {

    /*
     *  This method returns a boolean value from Curios API.
     *  You can easily use CuriosApi#getCuriosHelper() to find out is Angel Ring is present on player.
     *  However you can easily use isRingInCuriosSlot(<selected player>) and get a boolean answer.
     */
    public static boolean isRingInCuriosSlot(LivingEntity player) {
        ItemStack angelRing = ItemRegistry.ItemRing.getDefaultInstance();
        return CuriosApi.getCuriosHelper().findEquippedCurio(angelRing.getItem(), player).isPresent();
    }
}
