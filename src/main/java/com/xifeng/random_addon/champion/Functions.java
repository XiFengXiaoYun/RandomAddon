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
    @ZenClass("mods.ra.champions.affix.OnInitialSpawn")
    @ZenRegister
    @ModOnly("champions")
    public interface onInitialSpawn {
        void handle(IEntityLiving living);
    }

    @ZenClass("mods.ra.champions.affix.OnSpawn")
    @ZenRegister
    @ModOnly("champions")
    public interface onSpawn {
        void handle(IEntityLiving living);
    }

    @ZenClass("mods.ra.champions.affix.OnUpdate")
    @ZenRegister
    @ModOnly("champions")
    public interface onUpdate {
        void handle(IEntityLiving living);
    }

    @ZenClass("mods.ra.champions.affix.OnAttack")
    @ZenRegister
    @ModOnly("champions")
    public interface onAttack {
        void handle(IEntityLiving living, IEntityLivingBase livingBase, IDamageSource source, float dmg, EntityLivingAttackedEvent evt);
    }

    @ZenClass("mods.ra.champions.affix.CalcDamage")
    @ZenRegister
    @ModOnly("champions")
    public interface calcDamage {
        float handle(IEntityLiving living, IEntityLivingBase livingBase, IDamageSource source, float oldDmg, float newDmg);
    }

    @ZenClass("mods.ra.champions.affix.OnAttacked")
    @ZenRegister
    @ModOnly("champions")
    public interface  onAttacked {
        void handle(IEntityLiving living, IDamageSource source, float dmg, EntityLivingAttackedEvent evt);
    }

    @ZenClass("mods.ra.champions.affix.OnHurt")
    @ZenRegister
    @ModOnly("champions")
    public interface onHurt {
        float handle(IEntityLiving living, IDamageSource source, float dmg, float newDmg);
    }

    @ZenClass("mods.ra.champions.affix.OnHealed")
    @ZenRegister
    @ModOnly("champions")
    public interface onHealed {
        float handle(IEntityLiving living, float amount, float newAmount);
    }

    @ZenClass("mods.ra.champions.affix.OnDamaged")
    @ZenRegister
    @ModOnly("champions")
    public interface onDamaged {
        float handle(IEntityLiving living, IDamageSource source, float dmg, float newDmg);
    }

    @ZenClass("mods.ra.champions.affix.OnDeath")
    @ZenRegister
    @ModOnly("champions")
    public interface onDeath {
        void handle(IEntityLiving living, IDamageSource source, EntityLivingDeathEvent evt);
    }

    @ZenClass("mods.ra.champions.affix.OnKnockback")
    @ZenRegister
    @ModOnly("champions")
    public interface onKnockback {
        void handle(IEntityLiving living, LivingKnockBackEvent evt);
    }

    @ZenClass("mods.ra.champions.affix.CanApply")
    @ZenRegister
    @ModOnly("champions")
    public interface canApply {
        boolean handle(IEntityLiving living);
    }
}
