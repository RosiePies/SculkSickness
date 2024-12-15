package com.rosiepies.sculksickness;

import com.rosiepies.sculksickness.events.EntityEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public abstract class EventRegistry {
    static {
        MinecraftForge.EVENT_BUS.register(EntityEvents.class);
    }
}
