package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.blocks.entity.SculkBlossomBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(SculkSickness.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<BlockEntityType<SculkBlossomBlockEntity>> SCULK_BLOSSOM_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("sculk_blossom",
                    () -> BlockEntityType.Builder.of(SculkBlossomBlockEntity::new,BlockInit.SCULK_BLOSSOM_BLOCK.get()).build(null));


    public static void init() {
        BLOCK_ENTITIES.register();
    }
}
