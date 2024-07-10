package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.blocks.SculkBlossomBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.ToIntFunction;

@SuppressWarnings("unused")
public class BlockInit {
    private static ToIntFunction<BlockState> litBlockEmission(int i) {
        return (arg) -> {
            return (Boolean)arg.getValue(BlockStateProperties.LIT) ? i : 0;
        };
    }

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(SculkSickness.MOD_ID, Registry.BLOCK_REGISTRY);


    public static final RegistrySupplier<SculkBlossomBlock> SCULK_BLOSSOM_BLOCK =
            BLOCKS.register("sculk_blossom",
                    () -> new SculkBlossomBlock(
                            BlockBehaviour.Properties.of(Material.PLANT)
                                    .noCollission()
                                    .strength(0.2F)
                                    .sound(SoundType.SCULK)
                                    .lightLevel(litBlockEmission(13))));

    public static final RegistrySupplier<Block> ECHO_DUST_BLOCK =
            BLOCKS.register("echo_dust_block", () -> new Block(
                    BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN)
                            .requiresCorrectToolForDrops()
                            .strength(3.0F, 6.0F)
                            .sound(new SoundType(
                                    0.95F,
                                    0.3F,
                                    SoundEvents.AMETHYST_BLOCK_BREAK,
                                    SoundEvents.AMETHYST_BLOCK_STEP,
                                    SoundEvents.AMETHYST_BLOCK_PLACE,
                                    SoundEvents.SCULK_CATALYST_HIT,
                                    SoundEvents.SCULK_CATALYST_HIT))
                            .lightLevel((blockStatex) -> {
                                return 3;
                            })));

    public static void init() {
        BLOCKS.register();
    }
}
