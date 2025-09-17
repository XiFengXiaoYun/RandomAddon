package com.xifeng.random_addon.vanilla.village.trades;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.armor.ArmorPart;
import com.google.common.collect.Lists;
import com.xifeng.random_addon.config.ModConfig;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.harvest.TinkerHarvestTools;
import slimeknights.tconstruct.tools.melee.TinkerMeleeWeapons;
import slimeknights.tconstruct.tools.ranged.TinkerRangedWeapons;

import java.util.List;
import java.util.Random;

public class TradeUtil {
    private static final String[] armorMat = ModConfig.Materials.armorMaterialWhiteList;
    private static final List<ToolPart> toolParts = Lists.newArrayList(TinkerTools.swordBlade, TinkerTools.wideGuard, TinkerTools.toolRod);
    private static final List<ArmorPart> armorParts = Lists.newArrayList(ConstructsRegistry.bootsCore, ConstructsRegistry.armorPlate, ConstructsRegistry.armorTrim);
    //private static final List<ToolPart> bowParts = Lists.newArrayList(TinkerTools.bowLimb, TinkerTools.bowLimb, TinkerTools.bowString);
    //private static final List<ToolPart> arrowParts = Lists.newArrayList(TinkerTools.arrowHead, TinkerTools.arrowShaft, TinkerTools.fletching);
    private final ArmorCore[] armorCores = new ArmorCore[] {};
    private final ToolCore[] meleeWeapons = {
            TinkerMeleeWeapons.broadSword,
            TinkerMeleeWeapons.longSword,
            TinkerMeleeWeapons.rapier
    };
    private final ToolCore[] harvestTools = {
            TinkerHarvestTools.hatchet,
            TinkerHarvestTools.pickaxe,
            TinkerHarvestTools.shovel
    };

    private Material getRandomMat(String[] materials, ToolPart part, Random random) {
        Material material;
        do {
            material = TinkerRegistry.getMaterial(materials[random.nextInt(materials.length)]);
        } while (!part.canUseMaterial(material));
        return material;
    }

    public ItemStack randSword(Random random) {
        List<Material> materialList = Lists.newArrayList();
        for(ToolPart part : toolParts) {
            materialList.add(getRandomMat(ModConfig.Materials.materialWhiteList, part, random));
        }
        ToolCore toolCore = meleeWeapons[random.nextInt(3)];
        return toolCore.buildItem(materialList);
    }

    public ItemStack randTool(Random random) {
        List<Material> materialList = Lists.newArrayList();
        for(ToolPart part : toolParts) {
            materialList.add(getRandomMat(ModConfig.Materials.materialWhiteList, part, random));
        }
        ToolCore toolCore = harvestTools[random.nextInt(3)];
        return toolCore.buildItem(materialList);
    }

    public ItemStack randArmor(Random random) {
        List<Material> materialList = Lists.newArrayList();
        for(ArmorPart part : armorParts) {
            Material material = TinkerRegistry.getMaterial(armorMat[random.nextInt(armorMat.length)]);
            while (!part.canUseMaterial(material)) {
                material = TinkerRegistry.getMaterial(armorMat[random.nextInt(armorMat.length)]);
            }
            materialList.add(material);
        }
        ArmorCore core = ArmoryRegistry.armor.toArray(armorCores)[random.nextInt(4)];
        return core.buildItem(materialList);
    }

    public ItemStack randBow(Random random) {
        List<Material> materialList = Lists.newArrayList();
        Material limb1 = getRandomMat(ModConfig.Materials.bowMaterialWhiteList, TinkerTools.bowLimb, random);
        Material limb2 = getRandomMat(ModConfig.Materials.bowMaterialWhiteList, TinkerTools.bowLimb, random);
        Material string = getRandomMat(ModConfig.Materials.stringMaterialWhiteList, TinkerTools.bowString, random);

        materialList.add(limb1);
        materialList.add(limb2);
        materialList.add(string);

        return TinkerRangedWeapons.shortBow.buildItem(materialList);
    }

    public ItemStack randArrow(Random random) {
        List<Material> materialList = Lists.newArrayList();
        Material shaft = getRandomMat(ModConfig.Materials.shaftMaterialWhiteList, TinkerTools.arrowShaft, random);
        Material head = getRandomMat(ModConfig.Materials.materialWhiteList, TinkerTools.arrowHead, random);
        Material fletch = getRandomMat(ModConfig.Materials.fletchMaterialWhiteList, TinkerTools.fletching, random);

        materialList.add(shaft);
        materialList.add(head);
        materialList.add(fletch);

        return TinkerRangedWeapons.arrow.buildItem(materialList);
    }
}
