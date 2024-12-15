package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class SSTagInit {
    public static final TagKey<Block> SCULK_BLOCKS = create(Registries.BLOCK, SculkSickness.id("sculk_blocks"));
    public static final TagKey<EntityType<?>> SCULK_IMMUNE = create(Registries.ENTITY_TYPE, SculkSickness.id("sculk_immune"));
    public static final TagKey<EntityType<?>> SCULK_ENTITIES = create(Registries.ENTITY_TYPE, SculkSickness.id("sculk_entities"));


    private static <T> TagKey<T> create(ResourceKey<Registry<T>> registry, ResourceLocation resourceLocation) {
        return TagKey.create(registry, resourceLocation);
    }
}
