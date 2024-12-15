package com.rosiepies.sculksickness.particles.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

import java.util.Optional;

public class SculkBlossomParticles extends TextureSheetParticle {

    protected boolean isGlowing;

    SculkBlossomParticles(ClientLevel clientLevel, SpriteSet spriteSet, double d, double e, double f, double g, double h, double i, float xSize, float ySize) {
        super(clientLevel, d, e - 0.125, f, g, h, i);
        this.setSize(0.01F, 0.01F);
        this.pickSprite(spriteSet);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
        this.lifetime = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.hasPhysics = false;
        this.friction = 1.0F;
        this.gravity = 0.0F;
        this.setSize(xSize, ySize);
    }

    public int getLightColor(float f) {
        return this.isGlowing ? 240 : super.getLightColor(f);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class SculkBlossomSpewingProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public SculkBlossomSpewingProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            double j = (double)clientLevel.random.nextFloat() * -1.9 * (double)clientLevel.random.nextFloat() * 0.05;
            SculkBlossomParticles sculkBlossomParticles = new SculkBlossomParticles(clientLevel, this.sprite, d, e, f, 0.0, j, 0.0, 0.001F, 0.001F);
            sculkBlossomParticles.isGlowing = true;
            sculkBlossomParticles.setColor(0.16F, 0.87F, 0.92F);
            return sculkBlossomParticles;
        }
    }

    public static class SculkBlossomAirProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public SculkBlossomAirProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            SculkBlossomParticles sculkBlossomParticles = new SculkBlossomParticles(clientLevel, this.sprite, d, e, f, 0.0, -0.800000011920929, 0.0, 0.001F, 0.001F) {
                public Optional<ParticleGroup> getParticleGroup() {
                    return Optional.of(ParticleGroup.SPORE_BLOSSOM);
                }
            };
            sculkBlossomParticles.lifetime = Mth.randomBetweenInclusive(clientLevel.random, 500, 1000);
            sculkBlossomParticles.gravity = 0.01F;
            sculkBlossomParticles.isGlowing = true;
            sculkBlossomParticles.setColor(1F, 1F, 1F);
            sculkBlossomParticles.setAlpha(Mth.randomBetween(clientLevel.random,0.25F,0.75F));
            return sculkBlossomParticles;
        }
    }
}
