package com.xifeng.random_addon.config;

import net.minecraftforge.common.config.Config;

@Config(modid = "random_addon")
public class ModConfig {

    @Config.Name("Attributes Settings")
    @Config.Comment({"Settings about attributes"})
    public static Attributes attributes;

    @Config.Name("Trade Settings")
    @Config.Comment({"Settings about trades"})
    public static VillagerTrade trade;

    @Config.Name("Material Settings")
    @Config.Comment({"Settings about materials"})
    public static Materials material;

    @Config.Name("Nyx Integration Settings")
    @Config.Comment("Settings for nyx's new lunar events")
    public static Nyxs nyxs;

    @Config.Name("Difficulty System Settings")
    @Config.Comment("The difficulty system include mob's adaptation, which can reduce the damage it get")
    public static Difficulty difficulty;

    @Config.Name("Reskillable Settings")
    @Config.Comment("Settings about reskillable")
    public static ReskillableCompact reskillable;

    public static class ReskillableCompact {
        @Config.Comment("Set false to disable reskillable integration")
        public static boolean enable = true;

        @Config.Comment("Add skill-bound attribute for skills. The format is skillName:threshold:attributeName:attributeAmount:operation, threshold means when skillLevel % threshold == 0, then we get attribute bonus ")
        public static String[] skillToAttribute = new String[] {
            "reskillable.defense:1:generic.armor:0.25:0",
            "reskillable.building:1:generic.armorToughness:0.25:0",
            "reskillable.farming:1:generic.maxHealth:0.25:0",
            "reskillable.attack:1:generic.attackDamage:0.25:0",
            "reskillable.agility:1:generic.movementSpeed:0.01:1"
        };

        @Config.Comment("Add skillLevel-bound attribute modifiers for skills. The format is skillName:skillLevel:attributeName:attributeAmount:operation")
        public static String[] skillLevelToAttribute = new String[] {
                "reskillable.defense:32:generic.armor:0.125:2",
                "reskillable.farming:32:generic.maxHealth:0.25:2",
                "reskillable.attack:32:generic.attackDamage:0.25:2"
        };

        @Config.Comment("Add trait-bound attribute for traits. The format is traitName:attributeName:attributeAmount:operation")
        public static String[] traitToAttribute = new String[] {
                "reskillable.battle_spirit:generic.attackDamage:2.0:0",
                "reskillable.lucky_fisherman:generic.luck:1.0:0",
                "reskillable.drop_guarantee:ra.lootLevel:1.0:0",
                "reskillable.undershirt:generic.armor:2.0:0",
                "reskillable.sidestep:generic.movementSpeed:0.125:2",
                "reskillable.more_wheat:ra.expBonus:0.25:2",
                "reskillable.obsidian_smasher:ra.fortuneLevel:1.0:0"
        };
    }

    public static class Difficulty {
        @Config.Comment("Enable the difficulty system")
        public static boolean enable = true;

        @Config.Comment("Enable scaling health integration, disable this if you meet some problems or just don't want use it")
        public static boolean enableScalingHealth = true;

        @Config.Comment("Damage sources that can be adapted by a mob")
        public static String[] damageSources = new String[] {
                "mob",
                "player",
                "onFire",
                "inFire",
                "lava",
                "magic"
        };

        @Config.Comment("If only damage caused by player can be adapted by a mob")
        public static boolean onlyPlayer = true;

        @Config.Comment("The minimum hits required before a mob gets adapted to the damage, the actual hits is determined by the mobs ability level")
        public static int minHits = 3;

        @Config.Comment("The maximum hits required before a mob gets adapted to the damage, the actual hits is determined by the mobs ability level")
        public static int maxHits = 6;

        @Config.Comment("The minimum damage modifier after being adapted by a mob")
        public static float minDmgModifier = 0.5f;

        @Config.Comment("The damage reduction per hit after a mob get adapted to this damage, the actual damage reduction is determined by the mob's adaptation ability")
        public static float reduction = 0.0625f;

        @Config.Comment("Mob blacklist")
        public static String[] blackList = new String[] {
                "minecraft:creeper"
        };

        @Config.Comment("Mob whitelist")
        public static String[] whiteList = {
        };

        @Config.Name("Scaling Health integration Settings")
        @Config.Comment("Control the integration with the scaling health mod")
        public static ScalingHealth scalingHealth;

        public static class ScalingHealth {
            @Config.Comment("The max adapt level can be reached with the world difficulty increasing")
            public static int maxAdaptLevel = 4;

            @Config.Comment("Control how many world difficulty need to be increased to raise 1 level of mob's adapt level")
            public static double adaptLevelIncreaseRate = 100;

            @Config.Comment("The adapt ability is determined by WorldDifficulty / this ")
            public static double adaptAbilityIncreaseRate = 100;

            @Config.Comment("If true, disable blight mobs' adapt ability")
            public static boolean disableBlight = true;
        }

        @Config.Name("Nyx integration Settings")
        @Config.Comment("Control the integration with nyx mod")
        public static NyxIntegration nyxIntegration;

        public static class NyxIntegration {
            @Config.Comment("Should mobs don't have adapt ability on crescent moon ")
            public static boolean enable = true;
        }
    }

    public static class Nyxs {
        @Config.Comment("General setting")
        public static boolean enable=true;

        @Config.Comment("DarkMoon settings")
        public static DarkMoon darkMoon;

        @Config.Comment("CrescentMoon settings")
        public static CrescentMoon crescentMoon;

        @Config.Comment("DesertedMoon settings")
        public static DesertedMoon desertedMoon;

        @Config.Comment("BlueMoon settings")
        public static BlueMoon blueMoon;

        @Config.Comment("HunterNight settings")
        public static HunterNight hunterNight;

        @Config.Comment("PeacefulMoon settings")
        public static PeacefulMoon peacefulMoon;

        @Config.Comment("PeacefulMoon settings")
        public static MinerNight minerNight;

        public static class MinerNight {
            @Config.Comment("Enable this lunar event")
            public static boolean enable = true;
            @Config.Comment("The color of the night")
            public static int color = 0xfdeca6;
            @Config.Comment("The first day this lunar event will occur")
            public static int startNight = 7;
            @Config.Comment("The minimum days should pass when next one occur. Overrides the chance setting if this is greater than 0")
            public static int interval = 0;
            @Config.Comment("The amount of days that should pass until the next one happens again")
            public static int graceDay = 0;
            @Config.Comment("The chance that this lunar event will occur when all the conditions are met")
            public static double chance = 0.125;
            @Config.Comment("The bonus fortune level you will get during this lunar event")
            public static int bonusFortuneLevel = 3;
            @Config.Comment("The extra item you will get when harvest blocks during this lunar event")
            public static String dropItem = "minecraft:diamond";
        }

        public static class PeacefulMoon {
            @Config.Comment("Enable this lunar event")
            public static boolean enable = true;
            @Config.Comment("The color of the night")
            public static int color = 0xffaec8;
            @Config.Comment("The first day this lunar event will occur")
            public static int startNight = 6;
            @Config.Comment("The minimum days should pass when next one occur. Overrides the chance setting if this is greater than 0")
            public static int interval = 0;
            @Config.Comment("The amount of days that should pass until the next one happens again")
            public static int graceDay = 0;
            @Config.Comment("The chance that this lunar event will occur when all the conditions are met")
            public static double chance = 0.125;
            @Config.Comment("The extra enchantment level the enchant table will get during this lunar event")
            public static int bonusEnchantLevel = 20;
            @Config.Comment("The extra regeneration speed")
            public static double bonusHealSpeed = 1.0;
        }

        public static class HunterNight {
            @Config.Comment("Enable this lunar event")
            public static boolean enable = true;
            @Config.Comment("The color of the night")
            public static int color = 0xec1c24;
            @Config.Comment("The first day this lunar event will occur")
            public static int startNight = 5;
            @Config.Comment("The minimum days should pass when next one occur. Overrides the chance setting if this is greater than 0")
            public static int interval = 0;
            @Config.Comment("The amount of days that should pass until the next one happens again")
            public static int graceDay = 0;
            @Config.Comment("The chance that this lunar event will occur when all the conditions are met")
            public static double chance = 0.125;
            @Config.Comment("The damage reduction when player get hurt by a mob")
            public static double damageReduction = 0.25;
            @Config.Comment("The extra damage when player attack a mob")
            public static double damageBonus = 0.25;
            @Config.Comment("The extra looting level gained by a player during this lunar event")
            public static int lootingLevelBonus = 3;
            @Config.Comment("The extra experience gained by a player during this lunar event")
            public static double expBonus = 1.0;
            @Config.Comment("The extra item to be dropped when kill a mob during this lunar event")
            public static String dropItem = "minecraft:emerald";
        }

        public static class BlueMoon {
            @Config.Comment("Enable this lunar event")
            public static boolean enable = true;
            @Config.Comment("The color of the night")
            public static int color = 0x3f48cc;
            @Config.Comment("The first day this lunar event will occur")
            public static int startNight = 4;
            @Config.Comment("The minimum days should pass when next one occur. Overrides the chance setting if this is greater than 0")
            public static int interval = 0;
            @Config.Comment("The amount of days that should pass until the next one happens again")
            public static int graceDay = 0;
            @Config.Comment("The chance that this lunar event will occur when all the conditions are met")
            public static double chance = 0.125;
            @Config.Comment("The extra luck a player will get during this lunar event")
            public static int bonusLuck = 3;
        }

        public static class DesertedMoon {
            @Config.Comment("Enable this lunar event")
            public static boolean enable = true;
            @Config.Comment("The color of the night")
            public static int color = 0xb97a56;
            @Config.Comment("The first day this lunar event will occur")
            public static int startNight = 3;
            @Config.Comment("The minimum days should pass when next one occur. Overrides the chance setting if this is greater than 0")
            public static int interval = 0;
            @Config.Comment("The amount of days that should pass until the next one happens again")
            public static int graceDay = 0;
            @Config.Comment("The chance that this lunar event will occur when all the conditions are met")
            public static double chance = 0.125;
        }


        public static class DarkMoon {
            @Config.Comment("Enable this lunar event")
            public static boolean enable = true;
            @Config.Comment("The color of the night")
            public static int color = 0x585858;
            @Config.Comment("The first day this lunar event will occur")
            public static int startNight = 2;
            @Config.Comment("The minimum days should pass when next one occur. Overrides the chance setting if this is greater than 0")
            public static int interval = 0;
            @Config.Comment("The amount of days that should pass until the next one happens again")
            public static int graceDay = 0;
            @Config.Comment("The chance that this lunar event will occur when all the conditions are met")
            public static double chance = 0.125;
            @Config.Comment("The chance that a mob will be replaced by an EnderMan when the mob spawn during Dark Moon")
            public static double enderManChance = 0.875;
            @Config.Comment("Player will get hurt by the darkness during this lunar event, this is the damage interval (in ticks)")
            public static int damageInterval = 60;
            @Config.Comment("Player will get hurt by the darkness during this lunar event, this is the maximum light level to be considered as darkness")
            public static int lightLevel = 4;
            @Config.Comment("The damage that a player will get when in darkness")
            public static double darkDamage = 0.5;
        }

        public static class CrescentMoon {
            @Config.Comment("Enable this lunar event")
            public static boolean enable = true;
            @Config.Comment("The color of the night")
            public static int color = 0x8cfffb;
            @Config.Comment("The first day this lunar event will occur")
            public static int startNight = 1;
            @Config.Comment("The minimum days should pass when next one occur. Overrides the chance setting if this is greater than 0")
            public static int interval = 0;
            @Config.Comment("The amount of days that should pass until the next one happens again")
            public static int graceDay = 0;
            @Config.Comment("The chance that this lunar event will occur when all the conditions are met")
            public static double chance = 0.125;
            @Config.Comment("Mob spawn reduction during crescent moon")
            public static double mobReduction = 0.5;
            @Config.Comment("The extra enchantment level the enchant table will get during this lunar event")
            public static int bonusEnchantLevel = 10;
            @Config.Comment("The extra regeneration speed")
            public static double bonusHealSpeed = 0.5;
        }
    }

    public static class Attributes {

        @Config.Name("MaxLootingLevel")
        @Config.RangeDouble(min = 0.0, max = 114514.0)
        public static double maxLooting = 10.0;

        @Config.Name("MaxFortuneLevel")
        @Config.RangeDouble(min = 0.0, max = 114514.0)
        public static double maxFortune = 10.0;

        @Config.Name("MaxExpBonusLevel")
        @Config.RangeDouble(min = 1.0, max = 114514.0)
        public static double maxExpBonus = 10.0;
    }

    public static class VillagerTrade {
        @Config.Name("EnableTrade")
        public static boolean enableTrade = true;

        @Config.Name("Emerald price")
        @Config.Comment("The first is min price, the second is max price")
        @Config.RangeInt(min = 1, max = 64)
        public static int[] price = {10, 64};

        @Config.Name("ExtraItemForTrade")
        public static String extraItem = "minecraft:diamond";

        @Config.Name("Extra item's price")
        @Config.Comment("The first is min price, the second is max price")
        @Config.RangeInt(min = 1, max = 64)
        public static int[] price2 = {1, 6};
    }

    public static class Materials {
        @Config.Name("MaterialWhitelist")
        @Config.Comment("Tools containing these materials can be traded by villagers")
        public static String[] materialWhiteList = new String[] {
                "wood",
                "iron",
                "slime",
                "stone",
                "flint",
                "cactus",
                "bone",
                "prismarine",
                "endstone",
                "paper",
                "sponge",
                "firewood",
                "pigiron",
                "knightslime",
                "blueslime",
                "magmaslime",
                "netherrack",
                "cobalt",
                "ardite",
                "manyullyn"
        };

        @Config.Name("BowMaterialWhitelist")
        @Config.Comment("Bows containing these materials can be traded by villagers")
        public static String[] bowMaterialWhiteList = new String[] {
                "wood",
                "iron",
                "slime",
                "stone",
                "flint",
                "cactus",
                "bone",
                "prismarine",
                "endstone",
                "paper",
                "sponge",
                "firewood",
                "pigiron",
                "knightslime",
                "blueslime",
                "magmaslime",
                "netherrack",
                "cobalt",
                "ardite",
                "manyullyn"
        };

        @Config.Name("BowstringMaterialWhitelist")
        @Config.Comment("Bows with string made of these materials can be traded by villagers")
        public static String[] stringMaterialWhiteList = new String[] {
                "string",
                "vine"
        };

        @Config.Name("ArrowShaftMaterialWhitelist")
        @Config.Comment("Arrows with shaft made of these materials can be traded by villagers")
        public static String[] shaftMaterialWhiteList = new String[] {
                "wood",
                "endrod",
                "ice",
                "reed",
                "blaze",
                "bone"
        };

        @Config.Name("ArrowFletchMaterialWhitelist")
        @Config.Comment("Arrows with fletch made of these materials can be traded by villagers")
        public static String[] fletchMaterialWhiteList = new String[] {
                "feather",
                "leaf"
        };

        @Config.Name("ArmorMaterialWhitelist")
        @Config.Comment("Armors containing these materials can be traded by villagers")
        public static String[] armorMaterialWhiteList = new String[] {
                "wood",
                "iron",
                "slime",
                "stone",
                "flint",
                "cactus",
                "bone",
                "prismarine",
                "endstone",
                "paper",
                "sponge",
                "firewood",
                "pigiron",
                "knightslime",
                "blueslime",
                "magmaslime",
                "netherrack",
                "cobalt",
                "ardite",
                "manyullyn"
        };
    }
}
