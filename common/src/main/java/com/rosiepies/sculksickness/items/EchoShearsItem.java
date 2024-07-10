package com.rosiepies.sculksickness.items;

import com.rosiepies.sculksickness.blocks.SculkBlossomBlock;
import com.rosiepies.sculksickness.register.TagInit;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.state.BlockState;

public class EchoShearsItem extends DiggerShearsItem {

    public EchoShearsItem(float attackDamage, float attackSpeed, Tier tier, Properties properties) {
        super(attackDamage, attackSpeed, tier, TagInit.MINEABLE_WITH_ECHO_SHEARS, properties);
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        if (!blockState.is(Blocks.COBWEB) && !blockState.is(BlockTags.LEAVES)) {
            if (blockState.is(BlockTags.WOOL)) {
                return 5.0F;
            } else if (blockState.is(TagInit.SCULK_BLOCKS)) {
                return 8.0F;
            } else {
                return !blockState.is(Blocks.VINE) && !blockState.is(Blocks.GLOW_LICHEN) ? super.getDestroySpeed(itemStack, blockState) : 2.0F;
            }
        } else {
            return 15.0F;
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block instanceof SculkBlossomBlock sculkBlossomBlock && !sculkBlossomBlock.canSpew(blockState)) {
            if (!sculkBlossomBlock.isMaxAge(blockState)) {
                Player player = useOnContext.getPlayer();
                ItemStack itemStack = useOnContext.getItemInHand();
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
                }

                level.playSound(player, blockPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlockAndUpdate(blockPos, sculkBlossomBlock.setDoesAge(blockState,false));
                if (player != null) {
                    itemStack.hurtAndBreak(1, player, (playerx) -> {
                        playerx.broadcastBreakEvent(useOnContext.getHand());
                    });
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        return super.useOn(useOnContext);
    }
}
