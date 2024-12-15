package com.rosiepies.sculksickness.datagen.loot;

import com.rosiepies.sculksickness.register.SSBlockInit;
import com.rosiepies.sculksickness.register.SSItemInit;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class SSBlockLootTable extends BlockLootSubProvider {
    public SSBlockLootTable() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.add(SSBlockInit.SCULK_BLOSSOM_BLOCK.get(),
                block -> createSilkTouchOnlyTable(SSItemInit.SCULK_BLOSSOM_ITEM.get()));
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return SSBlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
