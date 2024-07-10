package com.rosiepies.sculksickness.forge;

import com.rosiepies.sculksickness.forge.events.ModEntityEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public abstract class EventRegistry {
    static {
        MinecraftForge.EVENT_BUS.register(ModEntityEvents.class);
    }
}
