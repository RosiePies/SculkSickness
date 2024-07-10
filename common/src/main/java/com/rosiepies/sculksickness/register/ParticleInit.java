package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

public class ParticleInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(SculkSickness.MOD_ID, Registries.PARTICLE_TYPE);

    public static final RegistrySupplier<SimpleParticleType> SCULK_BLOSSOM_SPEWING = PARTICLES.register("sculk_blossom_spewing", () -> new SimpleParticleType(false) {});
    public static final RegistrySupplier<SimpleParticleType> SCULK_BLOSSOM_AIR = PARTICLES.register("sculk_blossom_air", () -> new SimpleParticleType(false) {});
    public static final RegistrySupplier<SimpleParticleType> SCULK_EFFECT = PARTICLES.register("sculk_effect", () -> new SimpleParticleType(false) {});


    public static void init() {
        PARTICLES.register();
    }
}
