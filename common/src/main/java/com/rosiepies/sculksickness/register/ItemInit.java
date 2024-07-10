package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.items.EchoShearsItem;
import com.rosiepies.sculksickness.items.HerbItem;
import com.rosiepies.sculksickness.items.MortarPestleItem;
import com.rosiepies.sculksickness.items.ShriekerBoneItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;

@SuppressWarnings("unused")
public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(SculkSickness.MOD_ID, Registry.ITEM_REGISTRY);

    public static final RegistrySupplier<Item> TEST_ITEM = ITEMS.register("test_item",() -> new Item(new Item.Properties()));

    public static final RegistrySupplier<BlockItem> SCULK_BLOSSOM_ITEM = ITEMS.register("sculk_blossom",() -> new BlockItem(BlockInit.SCULK_BLOSSOM_BLOCK.get(), new Item.Properties()
            .tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistrySupplier<BlockItem> ECHO_DUST_BLOCK_ITEM = ITEMS.register("echo_dust_block",() -> new BlockItem(BlockInit.ECHO_DUST_BLOCK.get(), new Item.Properties()
            .tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistrySupplier<Item> MORTAR_AND_PESTLE = ITEMS.register("mortar_and_pestle",() -> new MortarPestleItem(new Item.Properties()
            .durability(128)
            .tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistrySupplier<Item> MORTAR_AND_ECHO = ITEMS.register("mortar_and_echo",() -> new MortarPestleItem(new Item.Properties()
            .durability(256)
            .tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistrySupplier<Item> ECHO_DUST = ITEMS.register("echo_dust",() -> new Item(new Item.Properties()
            .tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistrySupplier<Item> BLOOM_FRUIT = ITEMS.register("bloom_fruit",() -> new Item(new Item.Properties()
            .tab(CreativeModeTab.TAB_FOOD)
            .food((new FoodProperties.Builder())
                    .nutrition(4)
                    .saturationMod(0.3F)
                    .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 140, 1), 1.0F)
                    .effect(new MobEffectInstance(MobEffects.DARKNESS, 200), 1.0F)
                    .effect(new MobEffectInstance(MobEffects.WEAKNESS, 140, 2), 1.0F)
                    .alwaysEat()
                    .build())));
    public static final RegistrySupplier<Item> AXOLOTL_LEG = ITEMS.register("axolotl_leg",() -> new Item(new Item.Properties()
            .tab(CreativeModeTab.TAB_MATERIALS)
            .food((new FoodProperties.Builder())
                    .nutrition(2)
                    .saturationMod(0.1F)
                    .alwaysEat()
                    .effect(new MobEffectInstance(EffectInit.REVITALIZATION.get(),100,0), 1.0F)
                    .build())));
    public static final RegistrySupplier<Item> SHRIEKER_BONE = ITEMS.register("shrieker_bone",() -> new ShriekerBoneItem(new Item.Properties()
            .tab(CreativeModeTab.TAB_MATERIALS),6));
    public static final RegistrySupplier<Item> VILE_HERB = ITEMS.register("vile_herb",() -> new HerbItem(new Item.Properties()
            .food((new FoodProperties.Builder())
                    .nutrition(3)
                    .saturationMod(0.2F)
                    .alwaysEat()
                    .build()),false));
    public static final RegistrySupplier<Item> AMBROSIA_HERB = ITEMS.register("ambrosia_herb",() -> new HerbItem(new Item.Properties()
            .rarity(Rarity.EPIC)
            .food((new FoodProperties.Builder())
                    .nutrition(5)
                    .saturationMod(.6F)
                    .alwaysEat()
                    .build()),true));

    public static final RegistrySupplier<Item> ECHO_SHEARS = ITEMS.register("echo_shears",() -> new EchoShearsItem(-4,0,Tiers.NETHERITE,new Item.Properties()
            .tab(CreativeModeTab.TAB_TOOLS)
            .durability(714)));

    public static final RegistrySupplier<Item> SICKNESS_ICON = ITEMS.register("sickness_icon",() -> new Item(new Item.Properties()));

    public static void init() {
        ITEMS.register();
    }
}
