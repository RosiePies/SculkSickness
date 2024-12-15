package com.rosiepies.sculksickness.datagen;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.register.SSBlockInit;
import com.rosiepies.sculksickness.register.SSTagInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SSBlockTagProvider extends BlockTagsProvider {
    public SSBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, SculkSickness.MOD_ID, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(SSTagInit.SCULK_BLOCKS)
                .add(SSBlockInit.SCULK_BLOSSOM_BLOCK.get())
                .add(Blocks.SCULK)
                .add(Blocks.SCULK_CATALYST)
                .add(Blocks.SCULK_SENSOR)
                .add(Blocks.SCULK_SHRIEKER)
                .add(Blocks.SCULK_CATALYST)
                .add(Blocks.SCULK_VEIN)
                .add(Blocks.CALIBRATED_SCULK_SENSOR)
                .addOptionalTag(new ResourceLocation("sculkhorde","infested_block"));
    }
}
