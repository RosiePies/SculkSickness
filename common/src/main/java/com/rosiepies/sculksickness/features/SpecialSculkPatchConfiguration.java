package com.rosiepies.sculksickness.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record SpecialSculkPatchConfiguration(int chargeCount, int amountPerCharge, int spreadAttempts, int growthRounds, int spreadRounds, IntProvider extraRareGrowths, float mainGrowthChance, float catalystChance) implements FeatureConfiguration {
    public static final Codec<SpecialSculkPatchConfiguration> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                Codec.intRange(1, 32).fieldOf("charge_count").forGetter(SpecialSculkPatchConfiguration::chargeCount),
                Codec.intRange(1, 500).fieldOf("amount_per_charge").forGetter(SpecialSculkPatchConfiguration::amountPerCharge),
                Codec.intRange(1, 64).fieldOf("spread_attempts").forGetter(SpecialSculkPatchConfiguration::spreadAttempts),
                Codec.intRange(0, 8).fieldOf("growth_rounds").forGetter(SpecialSculkPatchConfiguration::growthRounds),
                Codec.intRange(0, 8).fieldOf("spread_rounds").forGetter(SpecialSculkPatchConfiguration::spreadRounds),
                IntProvider.CODEC.fieldOf("extra_rare_growths").forGetter(SpecialSculkPatchConfiguration::extraRareGrowths),
                Codec.floatRange(0.0F, 1.0F).fieldOf("main_growth_chance").forGetter(SpecialSculkPatchConfiguration::mainGrowthChance),
                Codec.floatRange(0.0F, 1.0F).fieldOf("catalyst_chance").forGetter(SpecialSculkPatchConfiguration::catalystChance)
                ).apply(instance, SpecialSculkPatchConfiguration::new);
    });

    public SpecialSculkPatchConfiguration(int chargeCount, int amountPerCharge, int spreadAttempts, int growthRounds, int spreadRounds, IntProvider extraRareGrowths, float mainGrowthChance, float catalystChance) {
        this.chargeCount = chargeCount;
        this.amountPerCharge = amountPerCharge;
        this.spreadAttempts = spreadAttempts;
        this.growthRounds = growthRounds;
        this.spreadRounds = spreadRounds;
        this.extraRareGrowths = extraRareGrowths;
        this.mainGrowthChance = mainGrowthChance;
        this.catalystChance = catalystChance;

    }

    public int chargeCount() {
        return this.chargeCount;
    }

    public int amountPerCharge() {
        return this.amountPerCharge;
    }

    public int spreadAttempts() {
        return this.spreadAttempts;
    }

    public int growthRounds() {
        return this.growthRounds;
    }

    public int spreadRounds() {
        return this.spreadRounds;
    }

    public IntProvider extraRareGrowths() {
        return this.extraRareGrowths;
    }

    public float catalystChance() {
        return this.catalystChance;
    }
    public float mainGrowthChance() {
        return this.mainGrowthChance;
    }
}

