package com.rosiepies.sculksickness.blocks;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.register.ParticleInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpecialBlossomBlock extends Block {
    private static final VoxelShape SHAPE = Block.box(2.0, 0, 2.0, 14.0, 3.0, 14.0);

    private static final int ADD_PARTICLE_ATTEMPTS = 11;
    private static final int PARTICLE_XZ_RADIUS = SculkSickness.CONFIG.common.blossoms.blossomInfectRadius;
    private static final int PARTICLE_Y_MAX = SculkSickness.CONFIG.common.blossoms.blossomInfectRadius;

    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return blockState.isFaceSturdy(blockGetter,blockPos,Direction.UP);
    }

    public SpecialBlossomBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.below();
        return this.mayPlaceOn(levelReader.getBlockState(blockPos2), levelReader, blockPos2);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {


        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }



    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        return direction == Direction.DOWN && !this.canSurvive(blockState, levelAccessor, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
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
                level.addParticle(ParticleInit.SCULK_BLOSSOM_AIR.get(), (double)mutableBlockPos.getX() + randomSource.nextDouble(), (double)mutableBlockPos.getY() + randomSource.nextDouble(), (double)mutableBlockPos.getZ() + randomSource.nextDouble(), 0.0, 0.0, 0.0);
            }
        }

    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }
}
