package com.xifeng.random_addon.champion;

import c4.champions.common.affix.AffixRegistry;
import c4.champions.common.affix.core.AffixCategory;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenClass("mods.ra.champions.AffixBuilder")
@ZenRegister
@ModOnly("champions")
public class CustomAffixBuilder {
    @ZenProperty
    public String id;

    @ZenProperty
    public int tier = 1;

    public AffixCategory category = AffixCategory.DEFENSE;

    @ZenProperty
    public Functions.canApply canApply = null;

    @ZenProperty
    public Functions.onAttacked onAttacked = null;

    @ZenProperty
    public Functions.onHurt onHurt = null;

    @ZenProperty
    public Functions.onInitialSpawn onInitialSpawn = null;

    @ZenProperty
    public Functions.onSpawn onSpawn = null;

    @ZenProperty
    public Functions.onAttack onAttack = null;

    @ZenProperty
    public Functions.onDamaged onDamaged = null;

    @ZenProperty
    public Functions.onDeath onDeath = null;

    @ZenProperty
    public Functions.onHealed onHealed = null;

    @ZenProperty
    public Functions.onKnockback onKnockback = null;

    @ZenProperty
    public Functions.onUpdate onUpdate = null;

    public CustomAffixBuilder(String id) {
        this.id = id;
    }

    @ZenMethod
    public static CustomAffixBuilder create(String id) {
        return new CustomAffixBuilder(id);
    }

    @ZenMethod
    public void register() {
        CustomAffix affix = new CustomAffix(id, category);
        affix.canApply = this.canApply;
        affix.onKnockback = this.onKnockback;
        affix.onDeath = this.onDeath;
        affix.onDamaged = this.onDamaged;
        affix.onHealed = this.onHealed;
        affix.onHurt = this.onHurt;
        affix.onAttacked = this.onAttacked;
        affix.onAttack = this.onAttack;
        affix.onUpdate = this.onUpdate;
        affix.onSpawn = this.onSpawn;
        affix.onInitialSpawn = this.onInitialSpawn;
        affix.identifier = this.id;
        affix.tier = this.tier;
        affix.category = this.category;

        AffixRegistry.registerAffix(id, affix);

        //return new CustomAffixRepresentation(affix);
    }

}
