package com.rosiepies.sculksickness.blocks;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.blocks.util.TickableBlockEntity;
import com.rosiepies.sculksickness.register.BlockEntityInit;
import com.rosiepies.sculksickness.register.EffectInit;
import com.rosiepies.sculksickness.register.ParticleInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SculkBlossomBlock extends SpecialBlossomBlock implements EntityBlock, SimpleWaterloggedBlock {

    public static final IntegerProperty BLOSSOM_AGE = IntegerProperty.create("blossom_age",0,3);
    public static final BooleanProperty CAN_SPEW = BooleanProperty.create("can_spew");
    public static final BooleanProperty DOES_AGE = BooleanProperty.create("does_age");
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;



    public SculkBlossomBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BLOSSOM_AGE, 0).setValue(LIT, false).setValue(CAN_SPEW, false).setValue(DOES_AGE,true).setValue(WATERLOGGED,false));
    }




    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return BlockEntityInit.SCULK_BLOSSOM_BLOCK_ENTITY.get().create(pos, state);
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
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(WATERLOGGED, blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos()).getType() == Fluids.WATER);
    }
    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    public void spawnAfterBreak(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, ItemStack itemStack, boolean bl) {
        super.spawnAfterBreak(blockState, serverLevel, blockPos, itemStack, bl);
        if (bl) {
            this.tryDropExperience(serverLevel, blockPos, itemStack, ConstantInt.of(5));
            if (blockState.getValue(CAN_SPEW)) {
                applySpores(serverLevel,blockPos);
            }
        }
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
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

        SculkSickness.applyParticles(serverLevel, ParticleInit.SCULK_BLOSSOM_SPEWING.get(), Vec3.atCenterOf(blockPos), new Vec3(SculkSickness.CONFIG.common.blossoms.blossomInfectRadius/3F,SculkSickness.CONFIG.common.blossoms.blossomInfectRadius/3F,SculkSickness.CONFIG.common.blossoms.blossomInfectRadius/3F), 0.05F, SculkSickness.CONFIG.common.blossoms.blossomInfectRadius*50, false, serverLevel.players());
        SculkSickness.applyParticles(serverLevel, ParticleTypes.SCULK_SOUL, Vec3.atCenterOf(blockPos), new Vec3(0.5,0,0.5), 0.05F, 50, false, serverLevel.players());
        serverLevel.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.BLOCKS, 1.5F, .5F);

        if (!list2.isEmpty()) {
            for (LivingEntity livingEntity : list2) {
                livingEntity.addEffect(new MobEffectInstance(EffectInit.SCULK_SICKNESS.get(), EffectInit.getStageInterval(livingEntity.getRandom()), 0, false, SculkSickness.CONFIG.common.general.isEffectVisible,true));
            }
        }
    }
}
