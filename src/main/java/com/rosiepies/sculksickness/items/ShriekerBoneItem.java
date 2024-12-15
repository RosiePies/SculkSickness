package com.rosiepies.sculksickness.items;

import com.rosiepies.sculksickness.SculkSickness;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ShriekerBoneItem extends Item {
    private final float shriekRange;
    public ShriekerBoneItem(Properties properties, float shriekRange) {
        super(properties);
        this.shriekRange = shriekRange;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!level.isClientSide()) {

            SculkSickness.applyParticles((ServerLevel) level, new ShriekParticleOption(0), player.position(), Vec3.ZERO, 2F, 2, false, (Collection<ServerPlayer>) level.players());
            SculkSickness.applyParticles((ServerLevel) level, new ShriekParticleOption(5), player.position(), Vec3.ZERO, 2F, 2, false, (Collection<ServerPlayer>) level.players());
            SculkSickness.applyParticles((ServerLevel) level, new ShriekParticleOption(10), player.position(), Vec3.ZERO, 2F, 2, false, (Collection<ServerPlayer>) level.players());
            SculkSickness.applyParticles((ServerLevel) level, new ShriekParticleOption(15), player.position(), Vec3.ZERO, 2F, 2, false, (Collection<ServerPlayer>) level.players());
            SculkSickness.applyParticles((ServerLevel) level, new ShriekParticleOption(20), player.position(), Vec3.ZERO, 2F, 2, false, (Collection<ServerPlayer>) level.players());

            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.NEUTRAL, 1.0F, 1.0F);

            AABB aABB = player.getBoundingBox().inflate(this.shriekRange, this.shriekRange/2, this.shriekRange);
            List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, aABB);
            Iterator<LivingEntity> var11 = list.iterator();

            LivingEntity livingEntity;
            while (var11.hasNext()) {
                livingEntity = var11.next();
                if (!livingEntity.is(player)) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 600));
                }
            }

            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
                if (itemStack.isEmpty()) {
                    player.getInventory().removeItem(itemStack);
                }
            }

            player.startUsingItem(interactionHand);
            player.getCooldowns().addCooldown(this, 300);
            player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.fail(itemStack);
    }
}
