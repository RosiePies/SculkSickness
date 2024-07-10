package com.rosiepies.sculksickness.recipes;

import com.rosiepies.sculksickness.register.ItemInit;
import com.rosiepies.sculksickness.register.PotionInit;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;

public class BrewingRecipes {
    public BrewingRecipes() {
        PotionBrewing.addMix(Potions.AWKWARD, ItemInit.BLOOM_FRUIT.get(), PotionInit.RESONANT_DEEP.get());
        PotionBrewing.addMix(PotionInit.RESONANT_DEEP.get(), Items.REDSTONE, PotionInit.RESONANT_DEEP_LONG.get());
        PotionBrewing.addMix(PotionInit.RESONANT_DEEP.get(), Items.GLOWSTONE_DUST, PotionInit.RESONANT_DEEP_STRONG.get());

        PotionBrewing.addMix(Potions.WEAKNESS, ItemInit.AMBROSIA_HERB.get(), PotionInit.SCULK_IMMUNITY.get());
        PotionBrewing.addMix(PotionInit.SCULK_IMMUNITY.get(), Items.REDSTONE, PotionInit.SCULK_IMMUNITY_LONG.get());

        PotionBrewing.addMix(Potions.AWKWARD, ItemInit.AXOLOTL_LEG.get(), PotionInit.REVITALIZATION.get());
        PotionBrewing.addMix(PotionInit.REVITALIZATION.get(), Items.REDSTONE, PotionInit.REVITALIZATION_LONG.get());
        PotionBrewing.addMix(PotionInit.REVITALIZATION.get(), Items.GLOWSTONE_DUST, PotionInit.REVITALIZATION_STRONG.get());

        PotionBrewing.addMix(PotionInit.SCULK_IMMUNITY.get(), ItemInit.VILE_HERB.get(), PotionInit.SCULK_SICKNESS.get());
    }
}
