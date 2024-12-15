package com.rosiepies.sculksickness.config;

import com.rosiepies.sculksickness.SculkSickness;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("unused")
@Config(name = SculkSickness.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/sculk.png")
public class ModConfig extends PartitioningSerializer.GlobalData {

    @ConfigEntry.Category("common")
    @ConfigEntry.Gui.TransitiveObject()
    public Common common = new Common();

    @Config(name = "common")
    public static final class Common implements ConfigData {
        @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
        public General general = new General();

        public static final class General implements ConfigData {

            @Comment("Dev Logs: Enable developer logs, good idea not to enable this due to it being able to cause large log sizes.")
            public boolean devLogs = false;

            @Comment("Minimum Stage Interval: The minimum amount of seconds until the infection progresses to next stage.")
            public int minStageInterval = 120;
            @Comment("Maximum Stage Interval: The maximum amount of seconds until the infection progresses to next stage.")
            public int maxStageInterval = 600;
            @Comment("Is Effect Visible: If the effect is visible when infected by normal means.")
            public boolean isEffectVisible = true;

            @Comment("Immunity Effects: Effects which make the entity immune to the Sculk Sickness")
            public String[] immunityEffects = {"sculksickness:sculk_immunity"};

            @Comment("Sickening Items: Items which make the entity infected with Sculk Sickness (MUST BE EDIBLE)")
            public String[] sickeningItems = {"sculksickness:vile_herb"};

            @Comment("Recovery Items: Items which make the entity heal from the Sculk Sickness (MUST BE EDIBLE)")
            public String[] curativeItems = {"sculksickness:ambrosia_herb"};

            @Comment("Bone Shriek Range: Range from which a shrieker bone will infect players and entities with the darkness effect")
            public float boneShriekRange = 8;

            @Comment("Chance to apply Sculk Sickness when stepping on / breaking sculk blocks or being hit by and infected entity / sculk mob. Should be from 0 to below 1.")
            public double randomInfectChance = .1;
            @Comment("Step Infect: Does stepping on Sculk blocks infect an entity? (Not to be confused with the Sculk Blossom step mechanics, refer to blossomInfect)")
            public boolean stepInfect = false;
            @Comment("Break Infect: Does breaking any Sculk block infect the player? (Not to be confused with the Sculk Blossom break mechanics, refer to blossomInfect)")
            public boolean breakInfect = false;
            @Comment("Hit Infect: Does being hit by any Sculk entity or Sculk infected entity infect an entity?")
            public boolean hitInfect = true;
            @Comment("Sculk Blossom Infect: Does being around a spewing Sculk Blossom infect an entity.")
            public boolean blossomInfect = true;
        }

        @ConfigEntry.Gui.CollapsibleObject()
        public Blossoms blossoms = new Blossoms();

        public static final class Blossoms implements ConfigData {
            @Comment("Minimum Blossom Interval: The minimum amount of seconds until a Sculk Blossom ages.")
            public int minBlossomInterval = 30;
            @Comment("Maximum Blossom Interval: The maximum amount of seconds until a Sculk Blossom ages.")
            public int maxBlossomInterval = 180;
            @Comment("Blossom Max Spew Time: The amount of seconds until a Sculk Blossom stops spewing spores.")
            public int maxSpewTime = 8;
            @Comment("Blossom Min Spew Time: The amount of seconds until a Sculk Blossom stops spewing spores.")
            public int minSpewTime = 16;
            @Comment("Blossom Spew Radius: The radius a Sculk Blossom can infect an entity.")
            public int blossomInfectRadius = 8;
            @Comment("Blossom Spawn Chance: The chance that a Sculk Blossom spawns from a catalyst, higher = less chance.")
            public int blossomSpawnChance = 11;
            @Comment("Blossom Spawn Can Spew: If a blossom should spawn from a catalyst with the ability to spew spores.")
            public boolean blossomSpawnCanSpew = true;
        }

        @ConfigEntry.Gui.CollapsibleObject()
        public DarknessSymptom darknessSymptom = new DarknessSymptom();

        public static final class DarknessSymptom implements ConfigData {
            @ConfigEntry.BoundedDiscrete(min = 1, max = 5)
            @Comment("Stage for 'Darkness' (Grants darkness & amplifies custom shader effect), should be a number 1-5")
            public int applyDarknessAtStage = 3;
        }

        @ConfigEntry.Gui.CollapsibleObject()
        public WeaknessSymptom weaknessSymptom = new WeaknessSymptom();

        public static final class WeaknessSymptom implements ConfigData {
            @ConfigEntry.BoundedDiscrete(min = 1, max = 5)
            @Comment("Stage for Weakness Effect & Shaking, should be a whole number 1-5")
            public int applyWeaknessAtStage = 1;
        }

        @ConfigEntry.Gui.CollapsibleObject()
        public DamageSymptom damageSymptom = new DamageSymptom();

        public static final class DamageSymptom implements ConfigData {
            @ConfigEntry.BoundedDiscrete(min = 1, max = 5)
            @Comment("Stage for Damage, should be a whole number 1-5")
            public int applyDamageAtStage = 5;

            @Comment("Amount of damage taken per half second. Should be a whole number above 0. 1 = 0.5 hearts of damage.")
            public int applyDamageAmountPerHalfSecond = 3;

            @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
            @Comment("Chance to apply damage per half second. Should be from 0 to below 1.")
            public double randomDamageChance = .1;
        }
    }
}
