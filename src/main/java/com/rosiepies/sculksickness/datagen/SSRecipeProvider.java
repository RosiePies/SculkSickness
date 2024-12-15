package com.rosiepies.sculksickness.datagen;

import com.rosiepies.sculksickness.register.SSItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class SSRecipeProvider extends RecipeProvider implements IConditionBuilder {



    public SSRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SSItemInit.VILE_HERB.get())
                .requires(SSItemInit.SHRIEKER_BONE.get(),2)
                .requires(SSItemInit.BLOOM_FRUIT.get(),2)
                .requires(Items.WHEAT)
                .unlockedBy(getHasName(SSItemInit.BLOOM_FRUIT.get()), has(SSItemInit.BLOOM_FRUIT.get()))
                .unlockedBy(getHasName(SSItemInit.SHRIEKER_BONE.get()), has(SSItemInit.SHRIEKER_BONE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SSItemInit.AMBROSIA_HERB.get(), 2)
                .requires(Items.GLOW_BERRIES,2)
                .requires(Items.ENCHANTED_GOLDEN_APPLE)
                .requires(SSItemInit.VILE_HERB.get(),2)
                .unlockedBy(getHasName(SSItemInit.VILE_HERB.get()), has(SSItemInit.VILE_HERB.get()))
                .save(consumer);
    }
}
