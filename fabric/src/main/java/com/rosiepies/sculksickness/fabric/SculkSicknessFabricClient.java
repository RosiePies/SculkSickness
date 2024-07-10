package com.rosiepies.sculksickness.fabric;

import com.rosiepies.sculksickness.SculkSicknessClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

@Environment(EnvType.CLIENT)
public class SculkSicknessFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SculkSicknessClient.initClient();
        SculkSicknessClient.initParticles(
                (particleType, spriteSetFunction) -> ParticleFactoryRegistry.getInstance().register(particleType, (ParticleFactoryRegistry.PendingParticleFactory) spriteSetFunction::apply));
    }
}