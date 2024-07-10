package com.rosiepies.sculksickness.forge;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.forge.events.ModEntityEvents;
import com.rosiepies.sculksickness.recipes.BrewingRecipes;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import javax.swing.text.html.parser.Entity;

@Mod(SculkSickness.MOD_ID)
public class SculkSicknessForge {
    public SculkSicknessForge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(SculkSickness.MOD_ID, eventBus);
        SculkSickness.init();
        eventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        FMLJavaModLoadingContext.get().getModEventBus().register(new BrewingRecipes());
    }
}