package com.rosiepies.sculksickness.items;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.register.EffectInit;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;

public class HerbItem extends Item {

    private final boolean isAmbrosia;
    private final boolean isVile;
    public HerbItem(Properties properties, boolean isAmbrosia) {
        super(properties.tab(CreativeModeTab.TAB_FOOD));
        this.isAmbrosia = isAmbrosia;
        this.isVile = !this.isAmbrosia;
    }



    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide() && isAmbrosia && !livingEntity.hasEffect(EffectInit.IMMUNITY.get())) {
            if (livingEntity.hasEffect(EffectInit.SCULK_SICKNESS.get())) {
                livingEntity.removeEffect(EffectInit.SCULK_SICKNESS.get());
                if (livingEntity instanceof Player player) {
                    player.displayClientMessage(Component.translatable("text.sculksickness.cured_by.eat_ambrosia_herb"), true);
                }
            }
            livingEntity.addEffect(new MobEffectInstance(EffectInit.IMMUNITY.get(), 100, 0, false, true,true));
        }
        if(!level.isClientSide() && isVile && !livingEntity.hasEffect(EffectInit.SCULK_SICKNESS.get())) {
            while (livingEntity.addEffect(new MobEffectInstance(EffectInit.SCULK_SICKNESS.get(), EffectInit.getStageInterval(livingEntity.getRandom()), 0, false, SculkSickness.CONFIG.common.general.isEffectVisible,true))) {
                if (level.getServer() != null) {
                    SculkSickness.applyParticles((level.getServer()).getLevel(level.dimension()), ParticleTypes.SCULK_SOUL, livingEntity.position(), new Vec3(0.5,0,0.5), 0.05F, 50, false, (Collection<ServerPlayer>) level.players());
                }
                if (!livingEntity.isSilent()) {
                    livingEntity.level.playSound(null, livingEntity.xo, livingEntity.yo, livingEntity.zo, SoundEvents.SCULK_CATALYST_BLOOM, livingEntity.getSoundSource(), 5, 0.8F);
                    livingEntity.playSound(SoundEvents.SCULK_CATALYST_BLOOM, 5, 0.8F);
                }
                if (livingEntity instanceof Player player) {
                    player.displayClientMessage(Component.translatable("text.sculksickness.infected_by.eat_vile_herb"), true);
                }
            }
        }
        return super.finishUsingItem(itemStack,level,livingEntity);
    }
}
