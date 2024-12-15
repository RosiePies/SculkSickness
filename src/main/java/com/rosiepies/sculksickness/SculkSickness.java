package com.rosiepies.sculksickness;

import com.mojang.logging.LogUtils;
import com.rosiepies.sculksickness.config.ModConfig;
import com.rosiepies.sculksickness.recipes.Brewing;
import com.rosiepies.sculksickness.register.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Collection;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SculkSickness.MOD_ID)
public class SculkSickness {

    public static final String MOD_ID = "sculksickness";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static Logger getLogger() {
        return LOGGER;
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.tryBuild(MOD_ID, path);
    }

    public static ModConfig CONFIG;


    public SculkSickness() {
        AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        SSTabInit.init(modEventBus);
        SSBlockEntityInit.init(modEventBus);
        SSBlockInit.init(modEventBus);
        SSEffectInit.init(modEventBus);
        SSItemInit.init(modEventBus);
        SSParticleInit.init(modEventBus);
        SSPotionInit.init(modEventBus);
        SSLootModifiersInit.init(modEventBus);
        SSFeatureInit.init(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        if (SculkSickness.CONFIG.common.general.devLogs) getLogger().info("Setting up Common " + MOD_ID);

        FMLJavaModLoadingContext.get().getModEventBus().register(new Brewing());
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(SSItemInit.SCULK_BLOSSOM_ITEM);
        }

        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(SSItemInit.BLOOM_FRUIT);
            event.accept(SSItemInit.VILE_HERB);
            event.accept(SSItemInit.AMBROSIA_HERB);
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(SSItemInit.SHRIEKER_BONE);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        if (SculkSickness.CONFIG.common.general.devLogs) getLogger().info("Server starting");
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

    public static Item getItemFromStrings(LivingEntity entity, String[] idArray) {
        // Throw this wherever appropriate, as long as you have access to the Entity object
        if (entity.level().isClientSide()) // This cannot be run on the client-side, only server-side.
            return null;

        for (String id : idArray) {
            var registries = entity.level().registryAccess();
            var itemRegistry = registries.registryOrThrow(Registries.ITEM);

            var item = itemRegistry.get(new ResourceLocation(id));
            if (entity.getServer() != null && item != null) {
                return item;
            }
        }
        return null;
        // do whatever
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

    public static void placeFeature(ServerLevel serverLevel, Holder<ConfiguredFeature<?,?>> holder, BlockPos blockPos) {
        ConfiguredFeature<?,?> configuredFeature = holder.value();
        configuredFeature.place(serverLevel, serverLevel.getChunkSource().getGenerator(), serverLevel.getRandom(), blockPos);

        if (SculkSickness.CONFIG.common.general.devLogs) getLogger().info("Placing feature: {}", configuredFeature);
    }

    public static boolean compareAmplifier (MobEffectInstance mobEffectInstance, int integer) {
        if (mobEffectInstance != null) return mobEffectInstance.getAmplifier() >= integer;
        return false;
    }
}
