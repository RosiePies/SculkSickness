package com.rosiepies.sculksickness.mixin;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.register.SSEffectInit;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class ShakingWeakness {

    protected ShakingWeakness(EntityRendererProvider.Context context) {
        super();
    }

    @Inject(method = "isShaking", at = @At("HEAD"), cancellable = true)
    private void sicknessShaking(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        boolean isEffectAmplifier = (SculkSickness.compareAmplifier(entity.getEffect(SSEffectInit.SCULK_SICKNESS.get()),SculkSickness.CONFIG.common.weaknessSymptom.applyWeaknessAtStage - 1));
        if (isEffectAmplifier) {
            cir.setReturnValue(true);
        }
    }
}
