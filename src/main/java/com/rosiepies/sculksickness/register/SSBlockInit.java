package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.blocks.SculkBlossomBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.ToIntFunction;

public class SSBlockInit {
    private static ToIntFunction<BlockState> litBlockEmission(int i) {
        return (arg) -> {
            return (Boolean)arg.getValue(BlockStateProperties.LIT) ? i : 0;
        };
    }

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, SculkSickness.MOD_ID);


    public static final RegistryObject<Block> SCULK_BLOSSOM_BLOCK =
            BLOCKS.register("sculk_blossom",
                    () -> new SculkBlossomBlock(
                            BlockBehaviour.Properties.of()
                                    .noCollission()
                                    .strength(0.2F)
                                    .sound(SoundType.SCULK)
                                    .lightLevel(litBlockEmission(13))));

    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
