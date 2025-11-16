package com.xifeng.random_addon.reskillable;

import com.xifeng.random_addon.RandomAddon;
import com.xifeng.random_addon.config.ModConfig;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class TraitAttributeEntry {
    private final String traitName;
    private final String attributeName;
    private final int operation;
    private final double amount;
    private final UUID uuid;

    public TraitAttributeEntry(String traitName, String attributeName, int operation, double amount) {
        this.attributeName = attributeName;
        this.traitName = traitName;
        this.operation = operation;
        this.amount = amount;
        this.uuid = UUID.nameUUIDFromBytes(traitName.getBytes(StandardCharsets.UTF_8));
    }

    public String getTraitName() {
        return this.traitName;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public int getOperation() {
        return this.operation;
    }

    public double getAmount() {
        return this.amount;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public static void initFromConfig(List<TraitAttributeEntry> traitList) {
        for(String entry : ModConfig.ReskillableCompact.traitToAttribute) {
            int op = 0;
            double amount = 0;
            String[] element = entry.split(":");
            if(!SkillUtils.checkTraitExists(element[0])) {
                RandomAddon.LOGGER.warn("Trait with name {} doesn't exist! Check if it's a spelling mistake!", element[0]);
            } else {
                try {
                    op = Integer.parseInt(element[3]);
                    amount = Double.parseDouble(element[2]);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
            traitList.add(new TraitAttributeEntry(element[0], element[1], op, amount));
            RandomAddon.LOGGER.info("Add one TraitAttributeEntry from config!");
        }
    }
}
