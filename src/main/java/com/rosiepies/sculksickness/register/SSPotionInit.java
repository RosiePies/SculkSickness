package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SSPotionInit {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, SculkSickness.MOD_ID);

    public static final RegistryObject<Potion> SCULK_IMMUNITY = POTIONS.register("sculk_immunity",
            () -> new Potion(
                    new MobEffectInstance(SSEffectInit.IMMUNITY.get(), 900)));
    public static final RegistryObject<Potion> SCULK_IMMUNITY_LONG = POTIONS.register("sculk_immunity_long",
            () -> new Potion(
                    new MobEffectInstance(SSEffectInit.IMMUNITY.get(), 1800)));

    public static final RegistryObject<Potion> SCULK_SICKNESS = POTIONS.register("sculk_sickness",
            () -> new Potion(
                    new MobEffectInstance(SSEffectInit.SCULK_SICKNESS.get(), (int) (SculkSickness.CONFIG.common.general.maxStageInterval+SculkSickness.CONFIG.common.general.minStageInterval)*10,0,false,SculkSickness.CONFIG.common.general.isEffectVisible,true)));



    public static void init(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
