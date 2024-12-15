package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.blocks.entities.SculkBlossomBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SSBlockEntityInit {
        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
                DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, SculkSickness.MOD_ID);

        public static final RegistryObject<BlockEntityType<SculkBlossomBlockEntity>> SCULK_BLOSSOM_BLOCK_ENTITY =
                BLOCK_ENTITIES.register("sculk_blossom",
                        () -> BlockEntityType.Builder.of(SculkBlossomBlockEntity::new, SSBlockInit.SCULK_BLOSSOM_BLOCK.get()).build(null));


        public static void init(IEventBus eventBus) {
            BLOCK_ENTITIES.register(eventBus);
        }
}
