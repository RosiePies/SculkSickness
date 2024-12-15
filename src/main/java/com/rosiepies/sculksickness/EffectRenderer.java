package com.rosiepies.sculksickness;

import com.google.gson.JsonSyntaxException;
import com.rosiepies.sculksickness.register.SSEffectInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class EffectRenderer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ResourceLocation SCULKSICKNESS_ACTIVE = new ResourceLocation(SculkSickness.MOD_ID, "shaders/post/sculksickness_active.json");
    private static final ResourceLocation SCULKSICKNESS_BEFORE = new ResourceLocation(SculkSickness.MOD_ID, "shaders/post/sculksickness_before.json");
    private static final ResourceLocation SCULKSICKNESS_DORMANT = new ResourceLocation(SculkSickness.MOD_ID, "shaders/post/sculksickness_dormant.json");


    private static PostChain sculkSicknessActiveShader;
    private static PostChain sculkSicknessBeforeShader;
    private static PostChain sculkSicknessDormantShader;

    private static int lastWidth = 0;
    private static int lastHeight = 0;
    private static PostChain lastShader = null;

    public static void renderShaderEffect(float renderTickTime) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            makeColorShaders();

            PostChain activeShader = null;
            if (SculkSickness.compareAmplifier(player.getEffect(SSEffectInit.SCULK_SICKNESS.get()),SculkSickness.CONFIG.common.darknessSymptom.applyDarknessAtStage - 1)) {
                activeShader = sculkSicknessActiveShader;
            } else if (SculkSickness.compareAmplifier(player.getEffect(SSEffectInit.SCULK_SICKNESS.get()),SculkSickness.CONFIG.common.darknessSymptom.applyDarknessAtStage - 2)) {
                activeShader = sculkSicknessBeforeShader;
            } else if (SculkSickness.compareAmplifier(player.getEffect(SSEffectInit.SCULK_SICKNESS.get()),0)) {
                activeShader = sculkSicknessDormantShader;
            }

            if (activeShader != null) {
                if (lastShader != activeShader) {
                    lastShader = activeShader;
                    lastWidth = 0;
                    lastHeight = 0;
                }
                updateShaderGroupSize(activeShader);
                activeShader.process(renderTickTime);
                Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
            }
        }
    }

    private static PostChain createShaderGroup(ResourceLocation location) {
        try {
            Minecraft mc = Minecraft.getInstance();
            return new PostChain(mc.getTextureManager(), mc.getResourceManager(), mc.getMainRenderTarget(), location);
        } catch (IOException ioexception) {
            LOGGER.warn("Failed to load shader: {}", location, ioexception);
        } catch (JsonSyntaxException jsonsyntaxexception) {
            LOGGER.warn("Failed to parse shader: {}", location, jsonsyntaxexception);
        }
        return null;
    }

    private static void makeColorShaders() {
        if (sculkSicknessActiveShader == null) {
            sculkSicknessActiveShader = createShaderGroup(SCULKSICKNESS_ACTIVE);
        }
        if (sculkSicknessBeforeShader == null) {
            sculkSicknessBeforeShader = createShaderGroup(SCULKSICKNESS_BEFORE);
        }
        if (sculkSicknessDormantShader == null) {
            sculkSicknessDormantShader = createShaderGroup(SCULKSICKNESS_DORMANT);
        }
    }

    private static void updateShaderGroupSize(PostChain shaderGroup) {
        if (shaderGroup != null) {
            Minecraft mc = Minecraft.getInstance();
            int width = mc.getWindow().getWidth();
            int height = mc.getWindow().getHeight();
            if (width != lastWidth || height != lastHeight) {
                lastWidth = width;
                lastHeight = height;
                shaderGroup.resize(width, height);
            }
        }
    }
}
