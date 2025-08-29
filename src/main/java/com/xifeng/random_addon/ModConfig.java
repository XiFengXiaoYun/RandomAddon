package com.xifeng.random_addon;

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
    @Config.Comment("Settings for nyx addon")
    public static Nyxs nyxs;

    public static class Nyxs {
        public static boolean hiddenMoon = true;
        public static int color = 0x585858;
    }

    public static class Attributes {

        @Config.Name("MaxLootingLevel")
        @Config.RangeDouble(min = 0.0, max = 114514.0)
        public static double maxLooting = 10.0;

        @Config.Name("MaxMiningLuckLevel")
        @Config.RangeDouble(min = 0.0, max = 114514.0)
        public static double maxLuck = 10.0;

        @Config.Name("MaxExpBonusLevel")
        @Config.RangeDouble(min = 1.0, max = 114514.0)
        public static double maxExpBonus = 10.0;
    }

    public static class VillagerTrade {
        @Config.Name("EnableTrade")
        public static boolean enableTrade = true;

        @Config.Name("Emerald price")
        @Config.RangeInt(min = 1, max = 64)
        public static int[] price = {10, 64};

        @Config.Name("ExtraItemForTrade")
        public static String extraItem = "minecraft:diamond";

        @Config.Name("Second item price")
        @Config.RangeInt(min = 1, max = 64)
        public static int[] price2 = {1, 6};
    }

    public static class Materials {
        @Config.Name("MaterialWhitelist")
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
        public static String[] stringMaterialWhiteList = new String[] {
                "string",
                "vine"
        };

        @Config.Name("ArrowShaftMaterialWhitelist")
        public static String[] shaftMaterialWhiteList = new String[] {
                "wood",
                "endrod",
                "ice",
                "reed",
                "blaze",
                "bone"
        };

        @Config.Name("ArrowFletchMaterialWhitelist")
        public static String[] fletchMaterialWhiteList = new String[] {
                "feather",
                "leaf"
        };

        @Config.Name("ArmorMaterialWhitelist")
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
