package com.rosiepies.sculksickness.fabric;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.recipes.BrewingRecipes;
import net.fabricmc.api.ModInitializer;

public class SculkSicknessFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SculkSickness.init();
        new BrewingRecipes();
    }
}