package com.rosiepies.sculksickness.mixin;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.register.SSDamageInit;
import com.rosiepies.sculksickness.register.SSEffectInit;
import com.rosiepies.sculksickness.register.SSParticleInit;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(MobEffectInstance.class)
@SuppressWarnings("unchecked")
public abstract class SicknessTickEffect implements Comparable<MobEffectInstance> {

    @Final
    @Shadow
    private MobEffect effect;
    @Shadow int duration;
    @Shadow
    private int amplifier;

    @Inject(at=@At("HEAD"), method = "tick", cancellable = true)
    public void tick(LivingEntity entity, Runnable onUpdate, CallbackInfoReturnable<Boolean> callback) {
        if (duration <= 1 && effect == SSEffectInit.SCULK_SICKNESS.get()) {
            if (amplifier < 4) {
                amplifier = amplifier + 1;
                duration = SSEffectInit.getStageInterval(entity.getRandom());
                if (entity.getServer() != null) {
                    SculkSickness.applyParticles((ServerLevel) entity.level(), SSParticleInit.SCULK_EFFECT.get(), entity.position(), new Vec3(0.5, 0, 0.5), 0.05F, 50, false, (Collection<ServerPlayer>) entity.level().players());
                }
                if (entity instanceof Player player) {
                    entity.level().playSound(null, entity.xo, entity.yo, entity.zo, SoundEvents.SCULK_CATALYST_BLOOM, entity.getSoundSource(), 5, 0.8F);
                    entity.playSound(SoundEvents.SCULK_CATALYST_BLOOM, 5, 0.8F);
                    player.displayClientMessage(Component.translatable("text.sculksickness.sickness_stage_warning",amplifier + 1),true);
                } else {
                    if (!entity.isSilent()) {
                        entity.level().playSound(null, entity.xo, entity.yo, entity.zo, SoundEvents.SCULK_CATALYST_BLOOM, entity.getSoundSource(), 5, 0.8F);
                        entity.playSound(SoundEvents.SCULK_CATALYST_BLOOM, 5, 0.8F);
                    }
                }
                callback.setReturnValue(true);
            }
            else {
                while (entity.hurt(SSDamageInit.causeSculkCorrosionDamage(entity), 10000)) {
                    if (entity.getServer() != null) {
                        SculkSickness.applyParticles((ServerLevel) entity.level(), SSParticleInit.SCULK_EFFECT.get(), entity.position(), new Vec3(0.5, 0, 0.5), 0.05F, 50, false, (Collection<ServerPlayer>) entity.level().players());
                    }
                    if (!entity.isSilent()) {
                        entity.level().playSound(null, entity.xo, entity.yo, entity.zo, SoundEvents.SCULK_SHRIEKER_SHRIEK, entity.getSoundSource(), 1.5F, .5F);
                        entity.playSound(SoundEvents.SCULK_SHRIEKER_SHRIEK, 1.5F, .5F);
                    }
                    SculkSickness.runFeatureFromString(entity,SculkSickness.MOD_ID,"sculk_patch_death");
                }
                callback.setReturnValue(false);
            }
        }
    }
}
