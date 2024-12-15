package com.rosiepies.sculksickness.mixin;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.blocks.SculkBlossomBlock;
import com.rosiepies.sculksickness.register.SSBlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(SculkBlock.class)
public class BlossomPlacer {

    @Inject(method = "getRandomGrowthState", at = @At("TAIL"), cancellable = true)
    private void getRandomGrowthState(LevelAccessor levelAccessor, BlockPos blockPos, RandomSource randomSource, boolean bl, CallbackInfoReturnable<BlockState> cir) {
        BlockState blockState;
        if (randomSource.nextInt(SculkSickness.CONFIG.common.blossoms.blossomSpawnChance) == 0) {
            blockState = SSBlockInit.SCULK_BLOSSOM_BLOCK.get().defaultBlockState().setValue(SculkBlossomBlock.CAN_SPEW, SculkSickness.CONFIG.common.blossoms.blossomSpawnCanSpew);
            cir.setReturnValue(blockState.hasProperty(BlockStateProperties.WATERLOGGED) && !levelAccessor.getFluidState(blockPos).isEmpty() ? blockState.setValue(BlockStateProperties.WATERLOGGED, true) : blockState);
        }
    }

    /**
     * @author
     * RosiePies
     * @reason
     * Adding Sculk Blossom to Sculk growths.
     */
    @Overwrite
    private static boolean canPlaceGrowth(LevelAccessor levelAccessor, BlockPos blockPos) {
        BlockState blockState = levelAccessor.getBlockState(blockPos.above());
        if (blockState.isAir() || blockState.is(Blocks.WATER) && blockState.getFluidState().is(Fluids.WATER)) {
            int i = 0;
            Iterator<BlockPos> var4 = BlockPos.betweenClosed(blockPos.offset(-4, 0, -4), blockPos.offset(4, 2, 4)).iterator();

            do {
                if (!var4.hasNext()) {
                    return(true);
                }

                BlockPos blockPos2 = var4.next();
                BlockState blockState2 = levelAccessor.getBlockState(blockPos2);
                if (blockState2.is(Blocks.SCULK_SENSOR) || blockState2.is(Blocks.SCULK_SHRIEKER) || blockState2.is(SSBlockInit.SCULK_BLOSSOM_BLOCK.get())) {
                    ++i;
                }
            } while(i <= 2);
            return(false);
        } else return(false);
    }
}
