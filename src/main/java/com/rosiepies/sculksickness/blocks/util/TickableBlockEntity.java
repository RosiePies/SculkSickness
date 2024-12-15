package com.rosiepies.sculksickness.blocks.util;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public interface TickableBlockEntity {
    void tick();

    static <T extends BlockEntity>BlockEntityTicker<T> getTickerHelper(Level level) {
        return level.isClientSide() ? null : (level2,pos0,blockState2,blockEntity) -> ((TickableBlockEntity) blockEntity).tick();
    }
}

