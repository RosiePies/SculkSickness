package com.rosiepies.sculksickness.events;

import com.google.common.collect.ImmutableList;
import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.register.SSDamageInit;
import com.rosiepies.sculksickness.register.SSEffectInit;
import com.rosiepies.sculksickness.register.SSItemInit;
import com.rosiepies.sculksickness.register.SSTagInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public abstract class EntityEvents {



    @SubscribeEvent
    public static void sicknessCurativeItems(LivingEntityUseItemEvent.Finish finishItemUse) {

        LivingEntity livingEntity = finishItemUse.getEntity();

        Item item = SculkSickness.getItemFromStrings(livingEntity, SculkSickness.CONFIG.common.general.curativeItems);

        if (finishItemUse.getItem().is(item)) livingEntity.removeEffect(SSEffectInit.SCULK_SICKNESS.get());


        if (SculkSickness.CONFIG.common.general.devLogs) SculkSickness.getLogger().info("Ate curative food");
    }

    @SubscribeEvent
    public static void sicknessInfectionItems(LivingEntityUseItemEvent.Finish finishItemUse) {

        LivingEntity livingEntity = finishItemUse.getEntity();

        Item item = SculkSickness.getItemFromStrings(livingEntity, SculkSickness.CONFIG.common.general.sickeningItems);

        if (!livingEntity.hasEffect(SSEffectInit.IMMUNITY.get()) || !livingEntity.hasEffect(SSEffectInit.SCULK_SICKNESS.get())) {
            if (finishItemUse.getItem().is(item))
                livingEntity.addEffect(new MobEffectInstance(SSEffectInit.SCULK_SICKNESS.get(), SSEffectInit.getStageInterval(livingEntity.getRandom()), 0, false, SculkSickness.CONFIG.common.general.isEffectVisible, true));
        }

        if (SculkSickness.CONFIG.common.general.devLogs) SculkSickness.getLogger().info("Ate infectious food");
    }

    @SubscribeEvent
    public static void hitInfect(LivingHurtEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Entity entity = event.getSource().getEntity();
        if (entity instanceof LivingEntity attackerEntity
                && SculkSickness.CONFIG.common.general.hitInfect) {
            if (!livingEntity.getType().is(SSTagInit.SCULK_IMMUNE) && !livingEntity.hasEffect(SSEffectInit.SCULK_SICKNESS.get()) && attackerEntity.hasEffect(SSEffectInit.SCULK_SICKNESS.get()) || attackerEntity.getType().is(SSTagInit.SCULK_ENTITIES) && SculkSickness.CONFIG.common.general.randomInfectChance >= SSEffectInit.randomTick()) {
                while (livingEntity.addEffect(new MobEffectInstance(SSEffectInit.SCULK_SICKNESS.get(), SSEffectInit.getStageInterval(livingEntity.getRandom()), 0, false, SculkSickness.CONFIG.common.general.isEffectVisible, true))) {
                    if (entity.level().getServer() != null) {
                        SculkSickness.applyParticles((ServerLevel) entity.level(), ParticleTypes.SCULK_SOUL, entity.position(), new Vec3(0.5, 0, 0.5), 0.05F, 50, false, (Collection<ServerPlayer>) entity.level().players());
                    }
                    if (!entity.isSilent()) {
                        entity.level().playSound(null, entity.xo, entity.yo, entity.zo, SoundEvents.SCULK_CATALYST_BLOOM, entity.getSoundSource(), 5, 0.8F);
                        entity.playSound(SoundEvents.SCULK_CATALYST_BLOOM, 5, 0.8F);
                    }
                    if (entity instanceof Player player) {
                        player.displayClientMessage(Component.translatable("text.sculksickness.infected_by.entity_attack"), true);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void blossomCorrosion (LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        if (SculkSickness.CONFIG.common.general.blossomInfect && SSDamageInit.isSculkCorrosionDamage(event.getSource())) {
            if (!(entity.getType().is(SSTagInit.SCULK_IMMUNE)) && !entity.hasEffect(SSEffectInit.SCULK_SICKNESS.get()) && SculkSickness.CONFIG.common.general.randomInfectChance >= SSEffectInit.randomTick()) {
                while (entity.addEffect(new MobEffectInstance(SSEffectInit.SCULK_SICKNESS.get(), SSEffectInit.getStageInterval(entity.getRandom()), 0, false, SculkSickness.CONFIG.common.general.isEffectVisible, true))) {
                    if (entity.level().getServer() != null) {
                        SculkSickness.applyParticles((ServerLevel) entity.level(), ParticleTypes.SCULK_SOUL, entity.position(), new Vec3(0.5, 0, 0.5), 0.05F, 50, false, (Collection<ServerPlayer>) entity.level().players());
                    }
                    if (!entity.isSilent()) {
                        entity.level().playSound(null, entity.xo, entity.yo, entity.zo, SoundEvents.SCULK_CATALYST_BLOOM, entity.getSoundSource(), 5, 0.8F);
                        entity.playSound(SoundEvents.SCULK_CATALYST_BLOOM, 5, 0.8F);
                    }
                    if (entity instanceof Player player) {
                        player.displayClientMessage(Component.translatable("text.sculksickness.infected_by.sculk_blossom"), true);
                    }
                }
            }
        }
    }
}
