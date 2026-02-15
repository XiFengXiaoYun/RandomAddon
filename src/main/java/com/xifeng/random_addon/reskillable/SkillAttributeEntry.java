package com.xifeng.random_addon.reskillable;

import java.util.*;

public class SkillAttributeEntry {

    private final String skillName;
    private final Map<Integer, Attribute> attributeModifiers = new HashMap<>();
    private final List<Attribute> attributes = new ArrayList<>();

    public SkillAttributeEntry(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillName() {
        return skillName;
    }

    public Map<Integer, Attribute> getAttributeModifiers() {
        return attributeModifiers;
    }

    public void addAttributeModifier(Attribute attributeModifier, int level) {
        attributeModifiers.put(level, attributeModifier);
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }
}
