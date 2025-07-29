package com.xifeng.random_addon.champion;
/*

 */
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
    @ZenClass("mods.ra.champions.affix.onInitialSpawn")
    @ZenRegister
    @ModOnly("champions")
    public interface onInitialSpawn {
        void handle(IEntityLiving living);
    }

    @ZenClass("mods.ra.champions.affix.onSpawn")
    @ZenRegister
    @ModOnly("champions")
    public interface onSpawn {
        void handle(IEntityLiving living);
    }

    @ZenClass("mods.ra.champions.affix.onUpdate")
    @ZenRegister
    @ModOnly("champions")
    public interface onUpdate {
        void handle(IEntityLiving living);
    }

    @ZenClass("mods.ra.champions.affix.onAttack")
    @ZenRegister
    @ModOnly("champions")
    public interface onAttack {
        void handle(IEntityLiving living, IEntityLivingBase livingBase, IDamageSource source, float dmg, EntityLivingAttackedEvent evt);
    }

    @ZenClass("mods.ra.champions.affix.onAttacked")
    @ZenRegister
    @ModOnly("champions")
    public interface  onAttacked {
        void handle(IEntityLiving living, IDamageSource source, float dmg, EntityLivingAttackedEvent evt);
    }

    @ZenClass("mods.ra.champions.affix.onHurt")
    @ZenRegister
    @ModOnly("champions")
    public interface onHurt {
        float handle(IEntityLiving living, IDamageSource source, float dmg, float newDmg);
    }

    @ZenClass("mods.ra.champions.affix.onHealed")
    @ZenRegister
    @ModOnly("champions")
    public interface onHealed {
        float handle(IEntityLiving living, float amount, float newAmount);
    }

    @ZenClass("mods.ra.champions.affix.onDamaged")
    @ZenRegister
    @ModOnly("champions")
    public interface onDamaged {
        float handle(IEntityLiving living, IDamageSource source, float dmg, float newDmg);
    }

    @ZenClass("mods.ra.champions.affix.onDeath")
    @ZenRegister
    @ModOnly("champions")
    public interface onDeath {
        void handle(IEntityLiving living, IDamageSource source, EntityLivingDeathEvent evt);
    }

    @ZenClass("mods.ra.champions.affix.onKnockback")
    @ZenRegister
    @ModOnly("champions")
    public interface onKnockback {
        void handle(IEntityLiving living, LivingKnockBackEvent evt);
    }

    @ZenClass("mods.ra.champions.affix.canApply")
    @ZenRegister
    @ModOnly("champions")
    public interface canApply {
        boolean handle(IEntityLiving living);
    }
}
