package com.xifeng.random_addon.reskillable;

import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Attribute {
    private final String attributeName;
    private final int operation;
    private final UUID uuid;
    private final double base;
    private AttributeModifier attributeModifier;
    private final int threshold;

    public Attribute(String attributeName, int operation, double base, int threshold) {
        this.attributeName = attributeName;
        String modifierName = attributeName + "_Modifier" + operation + base + threshold;
        this.operation = operation;
        this.base = base;
        this.uuid = UUID.nameUUIDFromBytes(modifierName.getBytes(StandardCharsets.UTF_8));
        this.attributeModifier = new AttributeModifier(uuid, modifierName, base, operation).setSaved(false);
        this.threshold = threshold;
    }

    public double getBase() {
        return base;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public AttributeModifier getAttributeModifier() {
        return attributeModifier;
    }

    public void setAmount(double amount) {
        this.attributeModifier = new AttributeModifier(uuid, attributeName + "_Modifier", amount, operation).setSaved(false);
    }

    public int getThreshold() {
        return threshold;
    }
}
