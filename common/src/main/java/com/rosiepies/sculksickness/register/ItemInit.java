package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.items.EchoShearsItem;
import com.rosiepies.sculksickness.items.HerbItem;
import com.rosiepies.sculksickness.items.MortarPestleItem;
import com.rosiepies.sculksickness.items.ShriekerBoneItem;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;

import java.util.List;

@SuppressWarnings("unused")
public class ItemInit {
    private static final ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;
    private static final Component ECHO_UPGRADE = Component.translatable(Util.makeDescriptionId("upgrade", new ResourceLocation(SculkSickness.MOD_ID,"echo_upgrade"))).withStyle(TITLE_FORMAT);
    private static final Component ECHO_UPGRADE_APPLIES_TO = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(SculkSickness.MOD_ID,"smithing_template.echo_upgrade.applies_to"))).withStyle(DESCRIPTION_FORMAT);
    private static final Component ECHO_UPGRADE_INGREDIENTS = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(SculkSickness.MOD_ID,"smithing_template.echo_upgrade.ingredients"))).withStyle(DESCRIPTION_FORMAT);
    private static final Component ECHO_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(SculkSickness.MOD_ID,"smithing_template.echo_upgrade.base_slot_description")));
    private static final Component ECHO_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(SculkSickness.MOD_ID,"smithing_template.echo_upgrade.additions_slot_description")));
    private static final ResourceLocation EMPTY_SLOT_REDSTONE_DUST = new ResourceLocation("item/empty_slot_redstone_dust");
    private static final ResourceLocation EMPTY_SLOT_SHEARS = new ResourceLocation(SculkSickness.MOD_ID, "item/empty_slot_shears");
    private static final ResourceLocation EMPTY_SLOT_ECHO_SHARD = new ResourceLocation(SculkSickness.MOD_ID,"item/empty_slot_echo_shard");

    private static final ResourceLocation EMPTY_SLOT_BLOCK = new ResourceLocation(SculkSickness.MOD_ID,"item/empty_slot_block");

    private static List<ResourceLocation> createEchoAppliableIconList() {
        return List.of(EMPTY_SLOT_SHEARS);
    }
    private static List<ResourceLocation> createEchoMaterialIconList() {
        return List.of(EMPTY_SLOT_REDSTONE_DUST, EMPTY_SLOT_ECHO_SHARD,EMPTY_SLOT_BLOCK);
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(SculkSickness.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> TEST_ITEM = ITEMS.register("test_item",() -> new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> ECHO_SMITHING_TEMPLATE = ITEMS.register("echo_upgrade_smithing_template", () ->
            new SmithingTemplateItem(
                    ECHO_UPGRADE_APPLIES_TO,
                    ECHO_UPGRADE_INGREDIENTS,
                    ECHO_UPGRADE,
                    ECHO_UPGRADE_BASE_SLOT_DESCRIPTION,
                    ECHO_UPGRADE_ADDITIONS_SLOT_DESCRIPTION,
                    createEchoAppliableIconList(),
                    createEchoMaterialIconList()));
    public static final RegistrySupplier<BlockItem> SCULK_BLOSSOM_ITEM = ITEMS.register("sculk_blossom",() -> new BlockItem(BlockInit.SCULK_BLOSSOM_BLOCK.get(), new Item.Properties()
            .arch$tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)));

    public static final RegistrySupplier<BlockItem> ECHO_DUST_BLOCK_ITEM = ITEMS.register("echo_dust_block",() -> new BlockItem(BlockInit.ECHO_DUST_BLOCK.get(), new Item.Properties()
            .arch$tab(CreativeModeTabs.BUILDING_BLOCKS)));

    public static final RegistrySupplier<Item> MORTAR_AND_PESTLE = ITEMS.register("mortar_and_pestle",() -> new MortarPestleItem(new Item.Properties()
            .durability(128)
            .arch$tab(CreativeModeTabs.TOOLS_AND_UTILITIES)));
    public static final RegistrySupplier<Item> MORTAR_AND_ECHO = ITEMS.register("mortar_and_echo",() -> new MortarPestleItem(new Item.Properties()
            .durability(256)
            .arch$tab(CreativeModeTabs.TOOLS_AND_UTILITIES)));
    public static final RegistrySupplier<Item> ECHO_DUST = ITEMS.register("echo_dust",() -> new Item(new Item.Properties()
            .arch$tab(CreativeModeTabs.INGREDIENTS)));
    public static final RegistrySupplier<Item> BLOOM_FRUIT = ITEMS.register("bloom_fruit",() -> new Item(new Item.Properties()
            .arch$tab(CreativeModeTabs.FOOD_AND_DRINKS)
            .food((new FoodProperties.Builder())
                    .nutrition(4)
                    .saturationMod(0.3F)
                    .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 140, 1), 1.0F)
                    .effect(new MobEffectInstance(MobEffects.DARKNESS, 200), 1.0F)
                    .effect(new MobEffectInstance(MobEffects.WEAKNESS, 140, 2), 1.0F)
                    .alwaysEat()
                    .build())));
    public static final RegistrySupplier<Item> AXOLOTL_LEG = ITEMS.register("axolotl_leg",() -> new Item(new Item.Properties()
            .arch$tab(CreativeModeTabs.FOOD_AND_DRINKS)
            .food((new FoodProperties.Builder())
                    .nutrition(2)
                    .saturationMod(0.1F)
                    .alwaysEat()
                    .effect(new MobEffectInstance(EffectInit.REVITALIZATION.get(),100,0), 1.0F)
                    .build())));
    public static final RegistrySupplier<Item> SHRIEKER_BONE = ITEMS.register("shrieker_bone",() -> new ShriekerBoneItem(new Item.Properties()
            .arch$tab(CreativeModeTabs.INGREDIENTS),6));
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

    public static final RegistrySupplier<Item> ECHO_SHEARS = ITEMS.register("echo_shears",() -> new EchoShearsItem(-4, 0.0f,Tiers.NETHERITE,new Item.Properties()
            .arch$tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
            .durability(714)));

    public static final RegistrySupplier<Item> SICKNESS_ICON = ITEMS.register("sickness_icon",() -> new Item(new Item.Properties()));

    public static void init() {
        ITEMS.register();
    }
    public static void addNewItems() {
        CreativeTabRegistry.append(TabInit.SCULK_SICKNESS_TAB,
                ItemInit.SCULK_BLOSSOM_ITEM.get().asItem(),
                ItemInit.VILE_HERB.get(),
                ItemInit.AMBROSIA_HERB.get(),
                ItemInit.AXOLOTL_LEG.get(),
                ItemInit.BLOOM_FRUIT.get(),
                ItemInit.SHRIEKER_BONE.get(),
                ItemInit.ECHO_DUST.get(),
                ItemInit.ECHO_DUST_BLOCK_ITEM.get().asItem(),
                ItemInit.ECHO_SMITHING_TEMPLATE.get(),
                ItemInit.ECHO_SHEARS.get(),
                ItemInit.MORTAR_AND_PESTLE.get(),
                ItemInit.MORTAR_AND_ECHO.get()
        );
        CreativeTabRegistry.append(CreativeModeTabs.INGREDIENTS,
                ItemInit.ECHO_SMITHING_TEMPLATE.get()
        );
    }
}
