package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.effects.ImmunityEffect;
import com.rosiepies.sculksickness.effects.SculkSicknessEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Random;

public class SSEffectInit {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, SculkSickness.MOD_ID);

    public static final RegistryObject<MobEffect> SCULK_SICKNESS = EFFECTS.register("sculk_sickness", SculkSicknessEffect::new);
    public static final RegistryObject<MobEffect> IMMUNITY = EFFECTS.register("sculk_immunity", ImmunityEffect::new);

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

    public static void init(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}