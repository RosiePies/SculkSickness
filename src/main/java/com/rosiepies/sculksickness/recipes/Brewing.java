package com.rosiepies.sculksickness.recipes;

import com.rosiepies.sculksickness.register.SSItemInit;
import com.rosiepies.sculksickness.register.SSPotionInit;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;

public class Brewing {
    public Brewing() {

        PotionBrewing.addMix(Potions.WEAKNESS, SSItemInit.AMBROSIA_HERB.get(), SSPotionInit.SCULK_IMMUNITY.get());
        PotionBrewing.addMix(SSPotionInit.SCULK_IMMUNITY.get(), Items.REDSTONE, SSPotionInit.SCULK_IMMUNITY_LONG.get());

        PotionBrewing.addMix(SSPotionInit.SCULK_IMMUNITY.get(), SSItemInit.VILE_HERB.get(), SSPotionInit.SCULK_SICKNESS.get());
    }
}
