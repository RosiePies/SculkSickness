package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

public class SSDamageInit {
    private static final ResourceKey<DamageType> SCULK_CORROSION_DAMAGE_KEY =
            ResourceKey.create(Registries.DAMAGE_TYPE,
                    new ResourceLocation(SculkSickness.MOD_ID, "sculk_corrosion"));

    public static Holder<DamageType> getSculkCorrosionDamageType(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(SCULK_CORROSION_DAMAGE_KEY);
    }

    public static boolean isSculkCorrosionDamage(DamageSource damageSource) {
        return containsDamage(damageSource);
    }
    public static DamageSource causeSculkCorrosionDamage(Entity entity) {

        return new DamageSource(getSculkCorrosionDamageType(entity.level().registryAccess()), entity);
    }

    private static TagKey<DamageType> SCULK_CORROSION_DAMGAGES = null;
    public static boolean containsDamage(DamageSource damageSource) {

        if (SCULK_CORROSION_DAMGAGES == null) {
            SCULK_CORROSION_DAMGAGES = TagKey.create(Registries.DAMAGE_TYPE,
                    new ResourceLocation(SculkSickness.MOD_ID, "sculk_corrosion"));
        }
        return damageSource.is(SCULK_CORROSION_DAMGAGES);
    }
}
