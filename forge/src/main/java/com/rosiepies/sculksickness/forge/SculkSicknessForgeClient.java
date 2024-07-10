package com.rosiepies.sculksickness.forge;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.SculkSicknessClient;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SculkSickness.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SculkSicknessForgeClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        SculkSicknessClient.initClient();
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        SculkSicknessClient.initParticles(
                (particleType, spriteSetFunction) -> event.register(particleType, (ParticleEngine.SpriteParticleRegistration) spriteSetFunction::apply));
    }
}