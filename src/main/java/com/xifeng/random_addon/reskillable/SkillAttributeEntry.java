package com.xifeng.random_addon.reskillable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;


import java.util.*;

public class SkillAttributeEntry {

    private final String skillName;
    private final Multimap<Integer, Attribute> attributeModifiers = HashMultimap.create();
    private final List<Attribute> attributes = new ArrayList<>();

    public SkillAttributeEntry(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillName() {
        return skillName;
    }

    public Multimap<Integer, Attribute> getAttributeModifiers() {
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
