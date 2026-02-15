package com.xifeng.random_addon.reskillable;

import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.HashMap;
import java.util.Map;

public class TraitAttributeEntry {
    private final String traitName;
    private final Map<String, AttributeModifier> modifierMap = new HashMap<>();

    public TraitAttributeEntry(String traitName) {
        this.traitName = traitName;
    }

    public Map<String, AttributeModifier> getModifierMap() {
        return modifierMap;
    }

    public void addModifier(String attributeName, AttributeModifier modifier) {
        modifierMap.put(attributeName, modifier);
    }

    public String getTraitName() {
        return traitName;
    }
}
