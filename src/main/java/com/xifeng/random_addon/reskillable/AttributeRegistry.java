package com.xifeng.random_addon.reskillable;

import com.xifeng.random_addon.config.ModConfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AttributeRegistry {
    private static final Map<String, SkillAttributeEntry> SKILL_ATTRIBUTES = new HashMap<>();
    private static final Map<String, TraitAttributeEntry> TRAIT_ATTRIBUTES = new HashMap<>();

    public static SkillAttributeEntry getSkillAttribute(String skillName) {
        return SKILL_ATTRIBUTES.computeIfAbsent(skillName, SkillAttributeEntry::new);
    }

    public static TraitAttributeEntry getTraitAttribute(String traitName) {
        return TRAIT_ATTRIBUTES.computeIfAbsent(traitName, TraitAttributeEntry::new);
    }

    public static Map<String, SkillAttributeEntry> allSkillAttributes() {
        return Collections.unmodifiableMap(new HashMap<>(SKILL_ATTRIBUTES));
    }

    public static Map<String, TraitAttributeEntry> allTraitAttributes() {
        return Collections.unmodifiableMap(new HashMap<>(TRAIT_ATTRIBUTES));
    }

    //从配置文件中读取并初始化
    //TODO: 使用Json简化配置
    public static void initSkillAttributes() {
        for (String entry : ModConfig.ReskillableCompact.skillLevelToAttribute) {
            String[] elements = entry.split(":");
            String skillName = elements[0];
            int skillLevel = Integer.parseInt(elements[1]);
            String attributeName = elements[2];
            double amount = Double.parseDouble(elements[3]);
            int operation = Integer.parseInt(elements[4]);
            SkillAttributeEntry skillAttributeEntry = getSkillAttribute(skillName);
            Attribute attribute = new Attribute(attributeName, operation, amount, 1);
            skillAttributeEntry.addAttributeModifier(attribute,  skillLevel);
        }
        for (String entry : ModConfig.ReskillableCompact.skillToAttribute) {
            String[] elements = entry.split(":");
            String skillName = elements[0];
            int threshold = Integer.parseInt(elements[1]);
            String attributeName = elements[2];
            double amount = Double.parseDouble(elements[3]);
            int operation = Integer.parseInt(elements[4]);
            SkillAttributeEntry skillAttributeEntry = getSkillAttribute(skillName);
            Attribute attribute = new Attribute(attributeName, operation, amount, threshold);
            skillAttributeEntry.addAttribute(attribute);
        }
    }

    public static void initTraitAttributes() {
        for  (String entry : ModConfig.ReskillableCompact.traitToAttribute) {
            String[] elements = entry.split(":");
            String traitName = elements[0];
            String attributeName = elements[1];
            double amount = Double.parseDouble(elements[2]);
            int operation = Integer.parseInt(elements[3]);
            TraitAttributeEntry traitAttributeEntry = getTraitAttribute(traitName);
            Attribute attribute = new Attribute(attributeName, operation, amount, 1);
            traitAttributeEntry.addModifier(attributeName, attribute.getAttributeModifier());
        }
    }
}
