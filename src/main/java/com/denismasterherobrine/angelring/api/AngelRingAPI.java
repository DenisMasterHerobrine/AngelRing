package com.denismasterherobrine.angelring.api;

import com.denismasterherobrine.angelring.register.ItemRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
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
