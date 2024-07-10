package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.features.SpecialSculkPatch;
import com.rosiepies.sculksickness.features.SpecialSculkPatchConfiguration;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;

public class FeatureInit {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(SculkSickness.MOD_ID, Registries.FEATURE);

    public static final RegistrySupplier<Feature<?>> BLOSSOM_PATCH = FEATURES.register("blossom_patch",() -> new SpecialSculkPatch(SpecialSculkPatchConfiguration.CODEC));


    public static void init() {
        FEATURES.register();
    }
}
