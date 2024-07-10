package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

public class PotionInit {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(SculkSickness.MOD_ID, Registries.POTION);

    public static final RegistrySupplier<Potion> RESONANT_DEEP = POTIONS.register("resonant_deep",
            () -> new Potion(
                    new MobEffectInstance(MobEffects.WEAKNESS, 400, 3),
                    new MobEffectInstance(MobEffects.DARKNESS, 120),
                    new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 2)));
    public static final RegistrySupplier<Potion> RESONANT_DEEP_LONG = POTIONS.register("resonant_deep_long",
            () -> new Potion(
                    new MobEffectInstance(MobEffects.WEAKNESS, 800, 3),
                    new MobEffectInstance(MobEffects.DARKNESS, 120),
                    new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 800, 2)));
    public static final RegistrySupplier<Potion> RESONANT_DEEP_STRONG = POTIONS.register("resonant_deep_strong",
            () -> new Potion(
                    new MobEffectInstance(MobEffects.WEAKNESS, 400, 5),
                    new MobEffectInstance(MobEffects.DARKNESS, 80),
                    new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 4)));

    public static final RegistrySupplier<Potion> SCULK_IMMUNITY = POTIONS.register("sculk_immunity",
            () -> new Potion(
                    new MobEffectInstance(EffectInit.IMMUNITY.get(), 900)));
    public static final RegistrySupplier<Potion> SCULK_IMMUNITY_LONG = POTIONS.register("sculk_immunity_long",
            () -> new Potion(
                    new MobEffectInstance(EffectInit.IMMUNITY.get(), 1800)));

    public static final RegistrySupplier<Potion> SCULK_SICKNESS = POTIONS.register("sculk_sickness",
            () -> new Potion(
                    new MobEffectInstance(EffectInit.SCULK_SICKNESS.get(), (int) (SculkSickness.CONFIG.common.general.maxStageInterval+SculkSickness.CONFIG.common.general.minStageInterval)*10,0,false,SculkSickness.CONFIG.common.general.isEffectVisible,true)));

    public static final RegistrySupplier<Potion> REVITALIZATION = POTIONS.register("revitalization",
            () -> new Potion(
                    new MobEffectInstance(EffectInit.REVITALIZATION.get(),900,0)));
    public static final RegistrySupplier<Potion> REVITALIZATION_LONG = POTIONS.register("revitalization_long",
            () -> new Potion(
                    new MobEffectInstance(EffectInit.REVITALIZATION.get(),1800,0)));
    public static final RegistrySupplier<Potion> REVITALIZATION_STRONG = POTIONS.register("revitalization_strong",
            () -> new Potion(
                    new MobEffectInstance(EffectInit.REVITALIZATION.get(),900,1)));


    public static void init() {
        POTIONS.register();
    }
}
