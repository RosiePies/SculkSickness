package com.rosiepies.sculksickness;

import com.rosiepies.sculksickness.particles.SculkBlossomParticles;
import com.rosiepies.sculksickness.particles.SculkEffectParticles;
import com.rosiepies.sculksickness.register.BlockInit;
import com.rosiepies.sculksickness.register.ParticleInit;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class SculkSicknessClient {

    public static void initClient() {
        RenderTypeRegistry.register(RenderType.cutout(),
                BlockInit.SCULK_BLOSSOM_BLOCK.get()
        );
    }


    public static void initParticles(BiConsumer<ParticleType<?>, Function<SpriteSet, ? extends ParticleProvider<? extends ParticleOptions>>> spriteProvider) {
        spriteProvider.accept(ParticleInit.SCULK_BLOSSOM_SPEWING.get(), SculkBlossomParticles.SculkBlossomSpewingProvider::new);
        spriteProvider.accept(ParticleInit.SCULK_BLOSSOM_AIR.get(), SculkBlossomParticles.SculkBlossomAirProvider::new);
        spriteProvider.accept(ParticleInit.SCULK_EFFECT.get(), SculkEffectParticles.Provider::new);
    }}