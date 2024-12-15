package com.rosiepies.sculksickness;

import com.rosiepies.sculksickness.particles.particles.SculkBlossomParticles;
import com.rosiepies.sculksickness.particles.particles.SculkEffectParticles;
import com.rosiepies.sculksickness.register.SSParticleInit;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.BiConsumer;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = SculkSickness.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SculkSicknessClient {


    public static void initParticles(BiConsumer<ParticleType<?>, Function<SpriteSet, ? extends ParticleProvider<? extends ParticleOptions>>> spriteProvider) {
        spriteProvider.accept(SSParticleInit.SCULK_BLOSSOM_SPEWING.get(), SculkBlossomParticles.SculkBlossomSpewingProvider::new);
        spriteProvider.accept(SSParticleInit.SCULK_BLOSSOM_AIR.get(), SculkBlossomParticles.SculkBlossomAirProvider::new);
        spriteProvider.accept(SSParticleInit.SCULK_EFFECT.get(), SculkEffectParticles.Provider::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        SculkSicknessClient.initParticles(
                (particleType, spriteSetFunction) -> event.registerSpriteSet(particleType, (ParticleEngine.SpriteParticleRegistration) spriteSetFunction::apply));
    }
}