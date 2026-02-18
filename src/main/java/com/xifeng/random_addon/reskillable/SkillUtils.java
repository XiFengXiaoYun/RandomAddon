package com.xifeng.random_addon.reskillable;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Collection;

public class SkillUtils {

    public static boolean checkSkillMatch(String otherSkill) {
        return AttributeRegistry.allSkillAttributes().containsKey(otherSkill);
    }

    public static boolean checkTraitMatch(String otherTrait) {
        return AttributeRegistry.allTraitAttributes().containsKey(otherTrait);
    }

    public static TraitAttributeEntry getTraitEntry(String traitName) {
        return AttributeRegistry.allTraitAttributes().get(traitName);
    }

    public static SkillAttributeEntry getSkillEntry(String skill) {
        return AttributeRegistry.allSkillAttributes().get(skill);
    }

    public static void applyTraitModifier(EntityPlayer player, TraitAttributeEntry entry) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        entry.getModifierMap().forEach(multimap::put);
        player.getAttributeMap().applyAttributeModifiers(multimap);
    }

    public static void applyAllModifiers(EntityPlayer player, SkillAttributeEntry entry, int newLevel) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        handleSkillLevel(multimap, entry, newLevel);
        handleSkill(multimap, entry, newLevel);
        player.getAttributeMap().applyAttributeModifiers(multimap);
    }

    public static void removeModifier(EntityPlayer player, SkillAttributeEntry entry) {
        Multimap<String, AttributeModifier> multimapToRemove = HashMultimap.create();

        entry.getAttributeModifiers().forEach((key, value) -> multimapToRemove.put(value.getAttributeName(), value.getAttributeModifier()));

        entry.getAttributes().forEach(attribute -> multimapToRemove.put(attribute.getAttributeName(), attribute.getAttributeModifier()));

        player.getAttributeMap().removeAttributeModifiers(multimapToRemove);
    }

    public static void removeModifier(EntityPlayer player, TraitAttributeEntry entry) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        entry.getModifierMap().forEach(multimap::put);
        player.getAttributeMap().removeAttributeModifiers(multimap);
    }

    //玩家登录或重生时，更新玩家的属性修饰符
    public static void syncAttribute(EntityPlayer player) {
        PlayerData data = PlayerDataHandler.get(player);
        Collection<PlayerSkillInfo> infos = data.getAllSkillInfo();
        for (PlayerSkillInfo info : infos) {
            SkillAttributeEntry skill = getSkillEntry(info.skill.getKey());
            if (skill != null) {
                applyAllModifiers(player, skill, info.getLevel());
            }
            for (Unlockable unlockable : info.skill.getUnlockables()) {
                TraitAttributeEntry trait = getTraitEntry(unlockable.getName());
                if (trait != null && info.isUnlocked(unlockable)) {
                    applyTraitModifier(player, trait);
                }
            }
        }
    }

    private static void handleSkillLevel(Multimap<String, AttributeModifier> multimap, SkillAttributeEntry entry, int level) {
        entry.getAttributeModifiers().forEach((key, value) -> {
            String id = value.getAttributeName();
            AttributeModifier modifier = value.getAttributeModifier();
            if(level >= key) multimap.put(id, modifier);
        });
    }

    private static void handleSkill(Multimap<String, AttributeModifier> multimap, SkillAttributeEntry entry, int level) {
        for(Attribute a : entry.getAttributes()) {
            String id = a.getAttributeName();
            int threshold =  a.getThreshold();
            if(level % threshold == 0) {
                double newAmount = (double) level / threshold * a.getBase();
                a.setAmount(newAmount);
                multimap.put(id, a.getAttributeModifier());
            }
        }
    }
}
