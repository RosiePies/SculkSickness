package com.rosiepies.sculksickness.events;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.register.DamageInit;
import com.rosiepies.sculksickness.register.EffectInit;
import com.rosiepies.sculksickness.register.TagInit;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;

public class ModEvents {
    public static void init() {
        EntityEvent.LIVING_HURT.register((LivingEntity entity, DamageSource source, float amount) -> {
            if (source.getDirectEntity() instanceof LivingEntity attackerEntity && SculkSickness.CONFIG.common.general.hitInfect) {
                if (!(entity.getType().is(TagInit.SCULK_IMMUNE)) && !entity.hasEffect(EffectInit.SCULK_SICKNESS.get()) && (attackerEntity.hasEffect(EffectInit.SCULK_SICKNESS.get()) || attackerEntity.getType().is(TagInit.SCULK_ENTITIES)) && SculkSickness.CONFIG.common.general.randomInfectChance >= EffectInit.randomTick()) {
                    while (entity.addEffect(new MobEffectInstance(EffectInit.SCULK_SICKNESS.get(), EffectInit.getStageInterval(entity.getRandom()), 0, false, SculkSickness.CONFIG.common.general.isEffectVisible,true))) {
                        if (entity.level().getServer() != null) {
                            SculkSickness.applyParticles((ServerLevel) entity.level(), ParticleTypes.SCULK_SOUL, entity.position(), new Vec3(0.5,0,0.5), 0.05F, 50, false, (Collection<ServerPlayer>) entity.level().players());
                        }
                        if (!entity.isSilent()) {
                            entity.level().playSound(null, entity.xo, entity.yo, entity.zo, SoundEvents.SCULK_CATALYST_BLOOM, entity.getSoundSource(), 5, 0.8F);
                            entity.playSound(SoundEvents.SCULK_CATALYST_BLOOM, 5, 0.8F);
                        }
                        if (entity instanceof Player player) {
                            player.displayClientMessage(Component.translatable("text.sculksickness.infected_by.entity_attack"), true);
                        }
                    }
                    return EventResult.interruptTrue();
                }
            }
            return EventResult.pass();
        });

        EntityEvent.LIVING_HURT.register((LivingEntity entity, DamageSource source, float amount) -> {
            if (SculkSickness.CONFIG.common.general.blossomInfect && DamageInit.isSculkCorrosionDamage(source)) {
                if (!(entity.getType().is(TagInit.SCULK_IMMUNE)) && !entity.hasEffect(EffectInit.SCULK_SICKNESS.get()) && SculkSickness.CONFIG.common.general.randomInfectChance >= EffectInit.randomTick()) {
                    while (entity.addEffect(new MobEffectInstance(EffectInit.SCULK_SICKNESS.get(), EffectInit.getStageInterval(entity.getRandom()), 0, false, SculkSickness.CONFIG.common.general.isEffectVisible,true))) {
                        if (entity.level().getServer() != null) {
                            SculkSickness.applyParticles((ServerLevel) entity.level(), ParticleTypes.SCULK_SOUL, entity.position(), new Vec3(0.5,0,0.5), 0.05F, 50, false, (Collection<ServerPlayer>) entity.level().players());
                        }
                        if (!entity.isSilent()) {
                            entity.level().playSound(null, entity.xo, entity.yo, entity.zo, SoundEvents.SCULK_CATALYST_BLOOM, entity.getSoundSource(), 5, 0.8F);
                            entity.playSound(SoundEvents.SCULK_CATALYST_BLOOM, 5, 0.8F);
                        }
                        if (entity instanceof Player player) {
                            player.displayClientMessage(Component.translatable("text.sculksickness.infected_by.sculk_blossom"), true);
                        }
                    }
                    return EventResult.interruptTrue();
                }
            }
            return EventResult.pass();
        });
    }
}
