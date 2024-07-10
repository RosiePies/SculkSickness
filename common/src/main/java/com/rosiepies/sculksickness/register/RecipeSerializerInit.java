package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.recipes.MortarPestleRecipe;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class RecipeSerializerInit {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(SculkSickness.MOD_ID, Registry.RECIPE_SERIALIZER_REGISTRY);

    public static final RegistrySupplier<RecipeSerializer<ShapelessRecipe>> MORTAR_PESTLE_SERIALIZER = RECIPE_SERIALIZERS.register("pestling", MortarPestleRecipe.Serializer::new);


    public static void init() {
        RECIPE_SERIALIZERS.register();
    }
}
