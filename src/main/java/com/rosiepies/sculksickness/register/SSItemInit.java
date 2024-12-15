package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.items.ShriekerBoneItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SSItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, SculkSickness.MOD_ID);

    public static final RegistryObject<BlockItem> SCULK_BLOSSOM_ITEM = ITEMS.register("sculk_blossom",
            () -> new BlockItem(SSBlockInit.SCULK_BLOSSOM_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> BLOOM_FRUIT = ITEMS.register("bloom_fruit",
            () -> new Item(new Item.Properties()
                    .food((new FoodProperties.Builder())
                            .nutrition(8)
                            .saturationMod(0.8F)
                            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 900, 0), 1.0F)
                            .build())));

    public static final RegistryObject<Item> SHRIEKER_BONE = ITEMS.register("shrieker_bone",
            () -> new ShriekerBoneItem(new Item.Properties(),SculkSickness.CONFIG.common.general.boneShriekRange));

    public static final RegistryObject<Item> VILE_HERB = ITEMS.register("vile_herb",
            () -> new Item(new Item.Properties()
                    .food((new FoodProperties.Builder())
                            .nutrition(4)
                            .saturationMod(1.2F)
                            .alwaysEat()
                            .build())));

    public static final RegistryObject<Item> AMBROSIA_HERB = ITEMS.register("ambrosia_herb",
            () -> new Item(new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .food((new FoodProperties.Builder())
                            .nutrition(4)
                            .saturationMod(1.2F)
                            .effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1.0F)
                            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3000, 0), 1.0F)
                            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 3000, 0), 1.0F)
                            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 1), 1.0F)
                            .alwaysEat()
                            .build())));

    public static final RegistryObject<Item> SICKNESS_ICON = ITEMS.register("sickness_icon",() -> new Item(new Item.Properties()));

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
