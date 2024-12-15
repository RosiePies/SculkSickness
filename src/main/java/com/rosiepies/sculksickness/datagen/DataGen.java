package com.rosiepies.sculksickness.datagen;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.datagen.loot.SSBlockLootTable;
import com.rosiepies.sculksickness.datagen.loot.SSBlockLootTableProvider;
import com.rosiepies.sculksickness.datagen.loot.SSLootModifierProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = SculkSickness.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        SSBlockTagProvider blockTags = new SSBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new SSEntityTagProvider(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), SSBlockLootTableProvider.create(packOutput));
        generator.addProvider(event.includeServer(), new SSLootModifierProvider(packOutput));

        generator.addProvider(event.includeServer(), new SSRecipeProvider(packOutput));

    }
}
