package com.rosiepies.sculksickness.datagen.loot;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.loot.AddItemModifier;
import com.rosiepies.sculksickness.register.SSItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class SSLootModifierProvider extends GlobalLootModifierProvider {
    public SSLootModifierProvider(PackOutput output) {
        super(output, SculkSickness.MOD_ID);
    }

    @Override
    protected void start() {
        add("shrieker_bone_from_ancient_city", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(new ResourceLocation("chests/ancient_city")).build(),
                LootItemRandomChanceCondition.randomChance(1.5f).build()
        }, SSItemInit.SHRIEKER_BONE.get()));
        add("bloom_fruit_from_ancient_city", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(new ResourceLocation("chests/ancient_city")).build(),
                LootItemRandomChanceCondition.randomChance(2.5f).build()
        }, SSItemInit.BLOOM_FRUIT.get()));
        add("vile_herb_from_ancient_city", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(new ResourceLocation("chests/ancient_city")).build(),
                LootItemRandomChanceCondition.randomChance(1f).build()
        }, SSItemInit.VILE_HERB.get()));
        add("ambrosia_herb_from_ancient_city", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(new ResourceLocation("chests/ancient_city")).build(),
                LootItemRandomChanceCondition.randomChance(0.2f).build()
        }, SSItemInit.AMBROSIA_HERB.get()));
    }
}
