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

    public static void applyModifier(EntityPlayer player, TraitAttributeEntry entry) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        entry.getModifierMap().forEach(multimap::put);
        player.getAttributeMap().applyAttributeModifiers(multimap);
    }

    public static void applyModifier(EntityPlayer player, SkillAttributeEntry entry, int level) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if(entry.getAttributeModifiers().containsKey(level)) {
            Attribute ra = entry.getAttributeModifiers().get(level);
            String id = ra.getAttributeName();
            AttributeModifier modifier = ra.getAttributeModifier();
            multimap.put(id, modifier);
        }
        for(Attribute ra : entry.getAttributes()) {
            String id = ra.getAttributeName();
            int threshold =  ra.getThreshold();
            if(level % threshold == 0) {
                double newAmount = (double) level / threshold * ra.getBase();
                ra.setAmount(newAmount);
                multimap.put(id, ra.getAttributeModifier());
            }
        }
        player.getAttributeMap().applyAttributeModifiers(multimap);
    }

    //玩家登录或重生时，更新玩家的属性修饰符
    public static void syncAttribute(EntityPlayer player) {
        PlayerData data = PlayerDataHandler.get(player);
        Collection<PlayerSkillInfo> infos = data.getAllSkillInfo();
        for (PlayerSkillInfo info : infos) {
            SkillAttributeEntry skill = getSkillEntry(info.skill.getKey());
            if (skill != null) {
                applyModifier(player, skill, info.getLevel());
            }
            for (Unlockable unlockable : info.skill.getUnlockables()) {
                TraitAttributeEntry trait = getTraitEntry(unlockable.getName());
                if (trait != null && info.isUnlocked(unlockable)) {
                    applyModifier(player, trait);
                }
            }
        }
    }
}
