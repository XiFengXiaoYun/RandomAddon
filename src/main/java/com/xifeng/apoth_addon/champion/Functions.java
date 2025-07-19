package com.xifeng.apoth_addon.champion;

import c4.champions.common.capability.IChampionship;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.IEntityLiving;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.EntityLivingAttackedEvent;
import crafttweaker.api.event.EntityLivingDeathEvent;
import crafttweaker.api.event.LivingKnockBackEvent;
import stanhebben.zenscript.annotations.ZenClass;

public class Functions {
    @ZenClass("mods.apothaddon.champions.affix.onInitialSpawn")
    @ZenRegister
    @ModOnly("champions")
    public interface onInitialSpawn {
        void handle(CustomAffixRepresentation affix, IEntityLiving living, IChampionship chp);
    }

    @ZenClass("mods.apothaddon.champions.affix.onSpawn")
    @ZenRegister
    @ModOnly("champions")
    public interface onSpawn {
        void handle(CustomAffixRepresentation affix, IEntityLiving living, IChampionship chp);
    }

    @ZenClass("mods.apothaddon.champions.affix.onUpdate")
    @ZenRegister
    @ModOnly("champions")
    public interface onUpdate {
        void handle(CustomAffixRepresentation affix, IEntityLiving living, IChampionship chp);
    }

    @ZenClass("mods.apothaddon.champions.affix.onAttack")
    @ZenRegister
    @ModOnly("champions")
    public interface onAttack {
        void handle(CustomAffixRepresentation affix, IEntityLiving living, IChampionship chp, IEntityLivingBase livingBase, IDamageSource source, float dmg, EntityLivingAttackedEvent evt);
        //EntityLivingAttackedEvent???
    }

    @ZenClass("mods.apothaddon.champions.affix.onAttacked")
    @ZenRegister
    @ModOnly("champions")
    public interface  onAttacked {
        void handle(CustomAffixRepresentation affix, IEntityLiving living, IChampionship chp, IDamageSource source, float dmg, EntityLivingAttackedEvent evt);
    }

    @ZenClass("mods.apothaddon.champions.affix.onHurt")
    @ZenRegister
    @ModOnly("champions")
    public interface onHurt {
        float handle(CustomAffixRepresentation affix, IEntityLiving living, IChampionship chp, IDamageSource source, float dmg, float newDmg);
        //Two floats, what is their meaning?
    }

    @ZenClass("mods.apothaddon.champions.affix.onHealed")
    @ZenRegister
    @ModOnly("champions")
    public interface onHealed {
        float handle(CustomAffixRepresentation affix, IEntityLiving living, IChampionship chp, float amount, float newAmount);
    }

    @ZenClass("mods.apothaddon.champions.affix.onDamaged")
    @ZenRegister
    @ModOnly("champions")
    public interface onDamaged {
        float handle(CustomAffixRepresentation affix, IEntityLiving living, IChampionship chp, IDamageSource source, float dmg, float newDmg);
    }

    @ZenClass("mods.apothaddon.champions.affix.onDeath")
    @ZenRegister
    @ModOnly("champions")
    public interface onDeath {
        void handle(CustomAffixRepresentation affix, IEntityLiving living, IChampionship chp, IDamageSource source, EntityLivingDeathEvent evt);
    }

    @ZenClass("mods.apothaddon.champions.affix.onKnockback")
    @ZenRegister
    @ModOnly("champions")
    public interface onKnockback {
        void handle(CustomAffixRepresentation affix, IEntityLiving living, IChampionship chp, LivingKnockBackEvent evt);
    }

    @ZenClass("mods.apothaddon.champions.affix.canApply")
    @ZenRegister
    @ModOnly("champions")
    public interface canApply {
        boolean handle(CustomAffixRepresentation affix, IEntityLiving living);
    }
}
