package com.rosiepies.sculksickness.forge.events;

import com.google.common.collect.ImmutableList;
import com.rosiepies.sculksickness.register.EffectInit;
import com.rosiepies.sculksickness.register.ItemInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class ModEntityEvents {
    @SubscribeEvent
    public static void sicknessCurativeItems(MobEffectEvent mobEffectEvent) {
        MobEffectInstance effectInstance;
        if ((effectInstance = mobEffectEvent.getEffectInstance()) != null && effectInstance.getEffect().equals(EffectInit.SCULK_SICKNESS.get())) {
            mobEffectEvent.getEffectInstance().setCurativeItems(ImmutableList.of(new ItemStack(ItemInit.AMBROSIA_HERB.get())));
        }
    }
}
