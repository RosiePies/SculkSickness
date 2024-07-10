package com.rosiepies.sculksickness.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MortarPestleItem extends Item {
    public MortarPestleItem(Properties properties) {
        super(properties.durability(128));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
