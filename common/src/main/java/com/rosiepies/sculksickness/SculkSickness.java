package com.rosiepies.sculksickness;

import com.rosiepies.sculksickness.config.ModConfig;
import com.rosiepies.sculksickness.events.ModEvents;
import com.rosiepies.sculksickness.register.*;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;


public class SculkSickness {
    public static final String MOD_ID = "sculksickness";
    private static final Logger LOGGER = LoggerFactory.getLogger(SculkSickness.MOD_ID);
    public static Logger getLogger() {
        return LOGGER;
    }

    public static ModConfig CONFIG;


    public static void init() {
        AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        BlockInit.init();
        ParticleInit.init();
        ItemInit.init();
        EffectInit.init();
        ModEvents.init();
        BlockEntityInit.init();
        FeatureInit.init();
        RecipeSerializerInit.init();
        PotionInit.init();
        TabInit.init();
    }

    public static int applyParticles(ServerLevel serverLevel, ParticleOptions particleOptions, Vec3 vec3, Vec3 vec32, float f, int i, boolean bl, Collection<ServerPlayer> collection) {
        int j = 0;
        for (ServerPlayer serverPlayer : collection) {
            if (serverLevel.sendParticles(serverPlayer, particleOptions, bl, vec3.x, vec3.y, vec3.z, i, vec32.x, vec32.y, vec32.z, f)) {
                ++j;
            }
        }
        return j;
    }

    public static void runFeatureFromString(LivingEntity entity, String modID, String id) {
        // Throw this wherever appropriate, as long as you have access to the Entity object

        if (entity.level().isClientSide()) // This cannot be run on the client-side, only server-side.
            return;

        var registries = entity.level().registryAccess();
        var featureRegistry = registries.registryOrThrow(Registries.CONFIGURED_FEATURE);

        var feature = featureRegistry.get(new ResourceLocation(modID, id));
        if (entity.getServer() != null && feature != null) {
            SculkSickness.placeFeature(entity.getServer().getLevel(entity.level().dimension()), Holder.direct(feature), entity.blockPosition());
        }
        // do whatever
    }

    public static MobEffect getEffectFromStrings(LivingEntity entity, String[] idArray) {
        // Throw this wherever appropriate, as long as you have access to the Entity object
        if (entity.level().isClientSide()) // This cannot be run on the client-side, only server-side.
            return null;

        for (String id : idArray) {
            var registries = entity.level().registryAccess();
            var mobEffectRegistry = registries.registryOrThrow(Registries.MOB_EFFECT);

            var mobEffect = mobEffectRegistry.get(new ResourceLocation(id));
            if (entity.getServer() != null && mobEffect != null) {
                return mobEffect;
            }
        }
        return null;
        // do whatever
    }

    public static void placeFeature(ServerLevel serverLevel, Holder<ConfiguredFeature<?,?>> holder, BlockPos blockPos) {
        ConfiguredFeature<?,?> configuredFeature = holder.value();
        configuredFeature.place(serverLevel, serverLevel.getChunkSource().getGenerator(), serverLevel.getRandom(), blockPos);
    }

    public static boolean compareAmplifier (MobEffectInstance mobEffectInstance, int integer) {
        if (mobEffectInstance != null) return mobEffectInstance.getAmplifier() >= integer;
        return false;
    }
}