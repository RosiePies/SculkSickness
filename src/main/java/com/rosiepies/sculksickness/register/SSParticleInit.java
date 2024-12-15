package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SSParticleInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, SculkSickness.MOD_ID);

    public static final RegistryObject<SimpleParticleType> SCULK_BLOSSOM_SPEWING = PARTICLES.register("sculk_blossom_spewing", () -> new SimpleParticleType(false) {});
    public static final RegistryObject<SimpleParticleType> SCULK_BLOSSOM_AIR = PARTICLES.register("sculk_blossom_air", () -> new SimpleParticleType(false) {});
    public static final RegistryObject<SimpleParticleType> SCULK_EFFECT = PARTICLES.register("sculk_effect", () -> new SimpleParticleType(false) {});


    public static void init(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}
