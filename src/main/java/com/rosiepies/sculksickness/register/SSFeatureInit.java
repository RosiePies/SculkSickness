package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.features.SpecialSculkPatch;
import com.rosiepies.sculksickness.features.SpecialSculkPatchConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SSFeatureInit {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, SculkSickness.MOD_ID);

    public static final RegistryObject<Feature<?>> BLOSSOM_PATCH = FEATURES.register("blossom_patch",() -> new SpecialSculkPatch(SpecialSculkPatchConfiguration.CODEC));


    public static void init(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
