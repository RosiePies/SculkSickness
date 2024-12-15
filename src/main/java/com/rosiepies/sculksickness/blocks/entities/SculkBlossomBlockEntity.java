package com.rosiepies.sculksickness.blocks.entities;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.blocks.SculkBlossomBlock;
import com.rosiepies.sculksickness.blocks.util.TickableBlockEntity;
import com.rosiepies.sculksickness.register.SSBlockEntityInit;
import com.rosiepies.sculksickness.register.SSEffectInit;
import com.rosiepies.sculksickness.register.SSParticleInit;
import com.rosiepies.sculksickness.register.SSTagInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SculkBlossomBlockEntity extends BlockEntity implements TickableBlockEntity {

    private int ageTime;
    private int spewingTime;
    private int maxAgeTime;
    private int maxSpewingTime;
    private boolean returnedMaxAgeTime;
    private boolean returnedMaxSpewingTime;
    private int blossomAge;
    protected final RandomSource random;

    public SculkBlossomBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SSBlockEntityInit.SCULK_BLOSSOM_BLOCK_ENTITY.get(), blockPos, blockState);
        this.random = RandomSource.create();
    }


    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        CompoundTag modData = nbt.getCompound(SculkSickness.MOD_ID);
        this.spewingTime = modData.getInt("spewingTime");
        this.blossomAge = modData.getInt("blossomAge");
        this.ageTime = modData.getInt("ageTime");
        this.maxAgeTime = modData.getInt("maxAgeTime");
        this.maxSpewingTime = modData.getInt("maxSpewingTime");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);

        var modData = new CompoundTag();
        modData.putInt("spewingTime", this.spewingTime);
        modData.putInt("blossomAge", this.blossomAge);
        modData.putInt("ageTime", this.ageTime);
        modData.putInt("maxAgeTime", this.maxAgeTime);
        modData.putInt("maxSpewingTime", this.maxSpewingTime);
        nbt.put(SculkSickness.MOD_ID, modData);
    }

    public boolean isSpewing() {
        return this.spewingTime > 0;
    }

    private int getBloomInterval() {
        return (int) (20 * getRandomInterval(SculkSickness.CONFIG.common.blossoms.minBlossomInterval, SculkSickness.CONFIG.common.blossoms.maxBlossomInterval));
    }

    private int getSpewTime() {
        return (int) (20 * getRandomInterval(SculkSickness.CONFIG.common.blossoms.minSpewTime, SculkSickness.CONFIG.common.blossoms.maxSpewTime));
    }

    private float getRandomInterval(float min, float max) {
        return (this.random.nextFloat() * (max - min)) + min;
    }

    @Override
    public void tick() {
        if (!returnedMaxSpewingTime) {
            returnedMaxSpewingTime = true;
            maxSpewingTime = getSpewTime();
            if (SculkSickness.CONFIG.common.general.devLogs)
                SculkSickness.getLogger().info("Getting new maxSpewingTime! New maxSpewingTime is: " + maxSpewingTime);
        }
        if (!returnedMaxAgeTime) {
            returnedMaxAgeTime = true;
            maxAgeTime = getBloomInterval();
            if (SculkSickness.CONFIG.common.general.devLogs)
                SculkSickness.getLogger().info("Getting new maxAgeTime! New maxAgeTime is: " + maxAgeTime);
        }

        if (level == null) return;
        if (!getBlockState().getValue(SculkBlossomBlock.DOES_AGE)) return;

        ageTime++;
        if (ageTime > maxAgeTime && !isSpewing() && blossomAge < 3) {
            returnedMaxAgeTime = false;
            ageTime = 0;

            blossomAge = blossomAge + 1;
            BlockState blockState = getBlockState().setValue(SculkBlossomBlock.BLOSSOM_AGE, this.blossomAge);
            level.setBlock(getBlockPos(), blockState, 3);
            setChanged(level, getBlockPos(), getBlockState());
            if (SculkSickness.CONFIG.common.general.devLogs) SculkSickness.getLogger().info("Growing blossom!");

            if (level.getServer() == null) return;
            SculkSickness.applyParticles(level.getServer().getLevel(level.dimension()), SSParticleInit.SCULK_EFFECT.get(), Vec3.atCenterOf(this.worldPosition), new Vec3(0.5, 0, 0.5), 0.05F, 50, false, (Collection<ServerPlayer>) level.players());
            level.playSound(null, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), SoundEvents.SCULK_BLOCK_CHARGE, SoundSource.BLOCKS, 3, Mth.randomBetween(level.random, 1.25F, (level.random.nextFloat() * (1.2F - 0.7F)) + 0.7F));
        } else if (ageTime > maxAgeTime && blossomAge == 3 && getBlockState().getValue(SculkBlossomBlock.CAN_SPEW)) {
            spewingTime++;
            if (spewingTime < maxSpewingTime) {

                if (level.getServer() == null) return;
                SculkSickness.applyParticles(level.getServer().getLevel(level.dimension()), SSParticleInit.SCULK_BLOSSOM_SPEWING.get(), Vec3.atCenterOf(this.worldPosition), new Vec3(0.125, 0.25, 0.125), 0F, 12, false, (Collection<ServerPlayer>) level.players());
                level.playSound(null, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 3, Mth.randomBetween(level.random, .85F, 1.25F));

                AABB aABB = (new AABB(this.getBlockPos())).inflate(SculkSickness.CONFIG.common.blossoms.blossomInfectRadius).expandTowards(0.0, 0.0, 0.0);
                List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, aABB);
                Iterator<LivingEntity> var11 = list.iterator();

                LivingEntity livingEntity;
                while (var11.hasNext()) {
                    livingEntity = var11.next();
                    if (livingEntity.hasEffect(SSEffectInit.SCULK_SICKNESS.get()) || livingEntity.getType().is(SSTagInit.SCULK_IMMUNE) || !livingEntity.hasEffect(SculkSickness.getEffectFromStrings(livingEntity, SculkSickness.CONFIG.common.general.immunityEffects))) {
                        if (!SculkSickness.CONFIG.common.general.blossomInfect) return;
                        while (livingEntity.addEffect(new MobEffectInstance(SSEffectInit.SCULK_SICKNESS.get(), SSEffectInit.getStageInterval(livingEntity.getRandom()), 0, false, false, true))) {
                            if (level.getServer() != null) {
                                SculkSickness.applyParticles((level.getServer()).getLevel(level.dimension()), ParticleTypes.SCULK_SOUL, livingEntity.position(), new Vec3(0.5, 0, 0.5), 0.05F, 50, false, (Collection<ServerPlayer>) level.players());
                            }
                            if (!livingEntity.isSilent()) {
                                livingEntity.level().playSound(null, livingEntity.xo, livingEntity.yo, livingEntity.zo, SoundEvents.SCULK_CATALYST_BLOOM, livingEntity.getSoundSource(), 1.75F, 0.8F);
                                livingEntity.playSound(SoundEvents.SCULK_CATALYST_BLOOM, 1.75F, 0.8F);
                            }
                            if (livingEntity instanceof Player player) {
                                player.displayClientMessage(Component.translatable("text.sculksickness.infected_by.sculk_blossom"), true);
                            }
                        }
                    }
                }
            } else{
                returnedMaxAgeTime = false;
                returnedMaxSpewingTime = false;

                spewingTime = 0;
                blossomAge = 0;
                ageTime = 0;
                if (SculkSickness.CONFIG.common.general.devLogs)
                    SculkSickness.getLogger().info("Resetting values to zero!");

                BlockState blockState = getBlockState().setValue(SculkBlossomBlock.BLOSSOM_AGE, this.blossomAge);
                level.setBlock(getBlockPos(), blockState, 3);
                setChanged(level, getBlockPos(), getBlockState());

                BlockState blockState1 = getBlockState().setValue(SculkBlossomBlock.LIT, false);
                level.setBlock(getBlockPos(), blockState1, 3);
                setChanged(level, getBlockPos(), getBlockState());
            }
        }

        blossomAge = getBlockState().getValue(SculkBlossomBlock.BLOSSOM_AGE);

        if (blossomAge == 3) {
            BlockState blockState1 = getBlockState().setValue(SculkBlossomBlock.LIT, true);
            level.setBlock(getBlockPos(), blockState1, 3);
            setChanged(level, getBlockPos(), getBlockState());
        } else {
            BlockState blockState1 = getBlockState().setValue(SculkBlossomBlock.LIT, false);
            level.setBlock(getBlockPos(), blockState1, 3);
            setChanged(level, getBlockPos(), getBlockState());
        }
    }
}