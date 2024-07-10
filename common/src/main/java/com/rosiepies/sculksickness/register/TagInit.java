package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

@SuppressWarnings("SameParameterValue")
public class TagInit {
    public static final TagKey<Block> SCULK_BLOCKS = create(Registry.BLOCK_REGISTRY,sculkSicknessLoc("sculk_blocks"));
    public static final TagKey<EntityType<?>> SCULK_IMMUNE = create(Registry.ENTITY_TYPE_REGISTRY,sculkSicknessLoc("sculk_immune"));
    public static final TagKey<EntityType<?>> SCULK_ENTITIES = create(Registry.ENTITY_TYPE_REGISTRY,sculkSicknessLoc("sculk_entities"));

    public static TagKey<?> getTagOrNull(TagKey<?> tagKey) {
        try {
            return tagKey;
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
        return tagKey;
    }


    private static <T> TagKey<T> create(ResourceKey<Registry<T>> registry, ResourceLocation resourceLocation) {
        return TagKey.create(registry, resourceLocation);
    }

    private static ResourceLocation forgeLoc(String path)
    {
        return new ResourceLocation("forge", path);
    }
    private static ResourceLocation sculkSicknessLoc(String path)
    {
        return new ResourceLocation(SculkSickness.MOD_ID, path);
    }
    private static ResourceLocation sculkHordeLoc(String path)
    {
        return new ResourceLocation("sculkhorde", path);
    }
}
