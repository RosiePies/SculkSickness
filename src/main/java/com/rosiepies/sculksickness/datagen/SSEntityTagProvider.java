package com.rosiepies.sculksickness.datagen;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.register.SSTagInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SSEntityTagProvider extends EntityTypeTagsProvider {
    public SSEntityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, SculkSickness.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(SSTagInit.SCULK_ENTITIES)
                .add(EntityType.WARDEN)
                .addOptionalTag(new ResourceLocation("deeperdarker","sculk"));
        this.tag(SSTagInit.SCULK_IMMUNE)
                .addTag(SSTagInit.SCULK_ENTITIES)
                .addOptionalTag(new ResourceLocation("sculkhorde","infested_block"));
    }
}
