package com.rosiepies.sculksickness.particles.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class SculkEffectParticles extends TextureSheetParticle {

    private static final RandomSource RANDOM = RandomSource.create();
    private final SpriteSet sprites;
    protected boolean isGlowing;
    SculkEffectParticles(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, SpriteSet spriteSet) {
        super(clientLevel, d, e, f, 0.5 - RANDOM.nextDouble(), h, 0.5 - RANDOM.nextDouble());
        this.friction = 0.96F;
        this.gravity = -0.1F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = spriteSet;
        this.yd *= 0.20000000298023224;
        if (g == 0.0 && i == 0.0) {
            this.xd *= 0.10000000149011612;
            this.zd *= 0.10000000149011612;
        }

        this.quadSize *= 0.75F;
        this.lifetime = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.hasPhysics = false;
        this.setSpriteFromAge(spriteSet);
        if (this.isCloseToScopingPlayer()) {
            this.setAlpha(0.0F);
        }

    }

    public int getLightColor(float f) {
        return this.isGlowing ? 240 : super.getLightColor(f);
    }
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        if (this.isCloseToScopingPlayer()) {
            this.setAlpha(0.0F);
        } else {
            this.setAlpha(Mth.lerp(0.05F, this.alpha, 1.0F));
        }

    }

    private boolean isCloseToScopingPlayer() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer localPlayer = minecraft.player;
        return localPlayer != null && localPlayer.getEyePosition().distanceToSqr(this.x, this.y, this.z) <= 9.0 && minecraft.options.getCameraType().isFirstPerson() && localPlayer.isScoping();
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            SculkEffectParticles sculkEffectParticles = new SculkEffectParticles(clientLevel, d, e, f, g, h, i, this.sprite);
            sculkEffectParticles.isGlowing = true;
            sculkEffectParticles.setColor(1F, 1F, 1F);
            sculkEffectParticles.setAlpha(Mth.randomBetween(clientLevel.random,0.25F,0.75F));
            return sculkEffectParticles;
        }
    }
}
