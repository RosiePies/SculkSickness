package com.rosiepies.sculksickness.blocks;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.blocks.util.TickableBlockEntity;
import com.rosiepies.sculksickness.register.SSBlockEntityInit;
import com.rosiepies.sculksickness.register.SSEffectInit;
import com.rosiepies.sculksickness.register.SSParticleInit;
import com.rosiepies.sculksickness.register.SSTagInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SculkBlossomBlock extends TickingBlossomBlock implements EntityBlock {

    public static final IntegerProperty BLOSSOM_AGE = IntegerProperty.create("blossom_age",0,3);
    public static final BooleanProperty CAN_SPEW = BooleanProperty.create("can_spew");
    public static final BooleanProperty DOES_AGE = BooleanProperty.create("does_age");
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final int ADD_PARTICLE_ATTEMPTS = 11;
    private static final int PARTICLE_XZ_RADIUS = SculkSickness.CONFIG.common.blossoms.blossomInfectRadius;
    private static final int PARTICLE_Y_MAX = SculkSickness.CONFIG.common.blossoms.blossomInfectRadius;

    public SculkBlossomBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BLOSSOM_AGE, 0).setValue(LIT, false).setValue(CAN_SPEW, false).setValue(DOES_AGE,true).setValue(WATERLOGGED,false));
    }

    @Override
    public void spawnAfterBreak(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, ItemStack itemStack, boolean bl) {
        if (SculkSickness.CONFIG.common.general.devLogs) SculkSickness.getLogger().info("Triggering spawnAfterBreak");
        super.spawnAfterBreak(blockState, serverLevel, blockPos, itemStack, bl);

        if (SculkSickness.CONFIG.common.general.devLogs) SculkSickness.getLogger().info("Trying to drop experience.");
        this.tryDropExperience(serverLevel, blockPos, itemStack, ConstantInt.of(5));
        if (blockState.getValue(CAN_SPEW)) {
            applySpores(serverLevel,blockPos);
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(WATERLOGGED, blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos()).getType() == Fluids.WATER);
    }
    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return TickableBlockEntity.getTickerHelper(level);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BLOSSOM_AGE,LIT,CAN_SPEW,DOES_AGE,WATERLOGGED);
    }
    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        int i = blockPos.getX();
        int j = blockPos.getY();
        int k = blockPos.getZ();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int l = 0; l < ADD_PARTICLE_ATTEMPTS; ++l) {
            mutableBlockPos.set(i + Mth.nextInt(randomSource, -PARTICLE_XZ_RADIUS, PARTICLE_XZ_RADIUS), j + randomSource.nextInt(PARTICLE_Y_MAX), k + Mth.nextInt(randomSource, -PARTICLE_XZ_RADIUS, PARTICLE_XZ_RADIUS));
            BlockState blockState2 = level.getBlockState(mutableBlockPos);
            if (!blockState2.isCollisionShapeFullBlock(level, mutableBlockPos)) {
                level.addParticle(SSParticleInit.SCULK_BLOSSOM_AIR.get(), (double)mutableBlockPos.getX() + randomSource.nextDouble(), (double)mutableBlockPos.getY() + randomSource.nextDouble(), (double)mutableBlockPos.getZ() + randomSource.nextDouble(), 0.0, 0.0, 0.0);
            }
        }

    }

    public BlockState setDoesAge(BlockState blockState, boolean bool) {
        return blockState.setValue(DOES_AGE, bool);
    }

    public boolean isMaxAge(BlockState blockState) {
        return blockState.getValue(BLOSSOM_AGE) == 4;
    }

    public boolean canSpew(BlockState blockState) {
        return blockState.getValue(CAN_SPEW);
    }
    private void applySpores(ServerLevel serverLevel, BlockPos blockPos) {
        if (SculkSickness.CONFIG.common.general.devLogs) SculkSickness.getLogger().info("Trying to apply Spore Splash!");

        AABB aABB = (new AABB(blockPos)).inflate(SculkSickness.CONFIG.common.blossoms.blossomInfectRadius/1.5).expandTowards(0.0, 0.0, 0.0);
        List<LivingEntity> list2 = serverLevel.getEntitiesOfClass(LivingEntity.class, aABB);

        SculkSickness.applyParticles(serverLevel, SSParticleInit.SCULK_BLOSSOM_SPEWING.get(), Vec3.atCenterOf(blockPos), new Vec3(SculkSickness.CONFIG.common.blossoms.blossomInfectRadius/3F,SculkSickness.CONFIG.common.blossoms.blossomInfectRadius/3F,SculkSickness.CONFIG.common.blossoms.blossomInfectRadius/3F), 0.05F, SculkSickness.CONFIG.common.blossoms.blossomInfectRadius*50, false, serverLevel.players());
        SculkSickness.applyParticles(serverLevel, ParticleTypes.SCULK_SOUL, Vec3.atCenterOf(blockPos), new Vec3(0.5,0,0.5), 0.05F, 50, false, serverLevel.players());
        serverLevel.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.BLOCKS, 1.5F, .5F);

        if (!list2.isEmpty()) {
            for (LivingEntity livingEntity : list2) {
                if (!livingEntity.hasEffect(SSEffectInit.SCULK_SICKNESS.get()) || !livingEntity.getType().is(SSTagInit.SCULK_IMMUNE) || !livingEntity.hasEffect(SculkSickness.getEffectFromStrings(livingEntity, SculkSickness.CONFIG.common.general.immunityEffects))) {
                    while (livingEntity.addEffect(new MobEffectInstance(SSEffectInit.SCULK_SICKNESS.get(), SSEffectInit.getStageInterval(livingEntity.getRandom()), 0, false, false, true))) {
                        SculkSickness.applyParticles(serverLevel, ParticleTypes.SCULK_SOUL, livingEntity.position(), new Vec3(0.5, 0, 0.5), 0.05F, 50, false, serverLevel.players());
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
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return SSBlockEntityInit.SCULK_BLOSSOM_BLOCK_ENTITY.get().create(blockPos, blockState);
    }
}
