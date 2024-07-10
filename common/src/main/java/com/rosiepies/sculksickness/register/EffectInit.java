package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.effects.ImmunityEffect;
import com.rosiepies.sculksickness.effects.RevitalizationEffect;
import com.rosiepies.sculksickness.effects.SculkSicknessEffect;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;

import java.util.Random;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class EffectInit {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(SculkSickness.MOD_ID, Registry.MOB_EFFECT_REGISTRY);

    public static final RegistrySupplier<MobEffect> SCULK_SICKNESS = EFFECTS.register("sculk_sickness", SculkSicknessEffect::new);
    public static final RegistrySupplier<MobEffect> IMMUNITY = EFFECTS.register("sculk_immunity", ImmunityEffect::new);
    public static final RegistrySupplier<MobEffect> REVITALIZATION = EFFECTS.register("revitalization", RevitalizationEffect::new);

    public static int getStageInterval(RandomSource randomSource) {
        return (int) (20 * getRandomStageInterval(SculkSickness.CONFIG.common.general.minStageInterval, SculkSickness.CONFIG.common.general.maxStageInterval, randomSource));
    }
    public static float getRandomStageInterval(float min, float max, RandomSource randomSource) {
        return (randomSource.nextFloat() * (max - min)) + min;
    }

    public static Float randomTick() {
        Random random = new Random();
        return random.nextFloat();
    }

    public static void init() {
        EFFECTS.register();
    }
}
