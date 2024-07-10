package com.rosiepies.sculksickness.damagesource;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.register.EffectInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class SculkCorrosionDamageSource extends DamageSource {
    public SculkCorrosionDamageSource() {
        super("sculk_corrosion");
    }

    @Override
    public boolean isBypassArmor() {
        return true;
    }
    @Override
    public boolean isBypassMagic() {
        return true;
    }
    @Override
    public boolean isBypassEnchantments() {
        return true;
    }

    @Override
    public Component getLocalizedDeathMessage(LivingEntity entity) {
        if (!(entity instanceof Player)) return super.getLocalizedDeathMessage(entity);
        String msg = "death.attack.sculk_corrosion";
        return Component.translatable(msg, entity.getDisplayName());
    }
}
