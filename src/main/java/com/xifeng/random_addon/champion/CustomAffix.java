package com.xifeng.random_addon.champion;

import c4.champions.common.affix.IAffix;
import c4.champions.common.affix.core.AffixBase;
import c4.champions.common.affix.core.AffixCategory;
import c4.champions.common.capability.IChampionship;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.events.handling.MCEntityLivingAttackedEvent;
import crafttweaker.mc1120.events.handling.MCEntityLivingDeathEvent;
import crafttweaker.mc1120.events.handling.MCLivingKnockBackEvent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

public class CustomAffix extends AffixBase implements IAffix {
    String identifier = null;
    int tier = 1;
    AffixCategory category = AffixCategory.DEFENSE;

    Functions.onInitialSpawn onInitialSpawn = null;
    Functions.onSpawn onSpawn = null;
    Functions.onUpdate onUpdate = null;
    Functions.onKnockback onKnockback = null;
    Functions.onDeath onDeath = null;
    Functions.onHealed onHealed = null;
    Functions.onDamaged onDamaged = null;
    Functions.onAttack onAttack = null;
    Functions.onHurt onHurt = null;
    Functions.canApply canApply = null;
    Functions.onAttacked onAttacked = null;

    public CustomAffix(String identifier, AffixCategory category) {
        super(identifier, category);
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public AffixCategory getCategory() {
        return super.getCategory();
    }

    @Override
    public void onInitialSpawn(EntityLiving entityLiving, IChampionship iChampionship) {
        if(onInitialSpawn != null) {
            onInitialSpawn.handle(CraftTweakerMC.getIEntityLiving(entityLiving));
        } else {
            super.onInitialSpawn(entityLiving, iChampionship);
        }
    }

    @Override
    public void onSpawn(EntityLiving entityLiving, IChampionship iChampionship) {
        if(onSpawn != null) {
            onSpawn.handle(CraftTweakerMC.getIEntityLiving(entityLiving));
            super.onSpawn(entityLiving, iChampionship);
        } else {
            super.onSpawn(entityLiving, iChampionship);
        }
    }

    @Override
    public void onUpdate(EntityLiving entityLiving, IChampionship iChampionship) {
        if(onUpdate != null) {
            onUpdate.handle(CraftTweakerMC.getIEntityLiving(entityLiving));
            super.onUpdate(entityLiving, iChampionship);
        } else {
            super.onUpdate(entityLiving, iChampionship);
        }
    }

    @Override
    public void onAttack(EntityLiving entityLiving, IChampionship iChampionship, EntityLivingBase entityLivingBase, DamageSource damageSource, float v, LivingAttackEvent livingAttackEvent) {
        if(onAttack != null) {
            onAttack.handle( CraftTweakerMC.getIEntityLiving(entityLiving), CraftTweakerMC.getIEntityLivingBase(entityLivingBase), CraftTweakerMC.getIDamageSource(damageSource), v, new MCEntityLivingAttackedEvent(livingAttackEvent));
        } else {
            super.onAttack(entityLiving, iChampionship, entityLivingBase, damageSource, v, livingAttackEvent);
        }
    }

    @Override
    public void onAttacked(EntityLiving entityLiving, IChampionship iChampionship, DamageSource damageSource, float v, LivingAttackEvent livingAttackEvent) {
        if(onAttacked != null) {
            onAttacked.handle(CraftTweakerMC.getIEntityLiving(entityLiving), CraftTweakerMC.getIDamageSource(damageSource), v, new MCEntityLivingAttackedEvent(livingAttackEvent));
        } else {
            super.onAttacked(entityLiving, iChampionship, damageSource, v, livingAttackEvent);
        }
    }

    @Override
    public float onHurt(EntityLiving entityLiving, IChampionship iChampionship, DamageSource damageSource, float v, float v1) {
        if(onHurt != null) {
            return onHurt.handle(CraftTweakerMC.getIEntityLiving(entityLiving), CraftTweakerMC.getIDamageSource(damageSource), v, v1);
        } else {
            return super.onHurt(entityLiving, iChampionship, damageSource, v, v1);
        }
    }

    @Override
    public float onHealed(EntityLiving entityLiving, IChampionship iChampionship, float v, float v1) {
        if(onHealed != null) {
            return onHealed.handle(CraftTweakerMC.getIEntityLiving(entityLiving), v, v1);
        } else {
            return super.onHealed(entityLiving, iChampionship, v, v1);
        }
    }

    @Override
    public float onDamaged(EntityLiving entityLiving, IChampionship iChampionship, DamageSource damageSource, float v, float v1) {
        if(onDamaged != null) {
            return onDamaged.handle(CraftTweakerMC.getIEntityLiving(entityLiving), CraftTweakerMC.getIDamageSource(damageSource), v, v1);
        } else {
            return super.onDamaged(entityLiving, iChampionship, damageSource, v, v1);
        }
    }

    @Override
    public void onDeath(EntityLiving entityLiving, IChampionship iChampionship, DamageSource damageSource, LivingDeathEvent livingDeathEvent) {
        if(onDeath != null) {
            onDeath.handle(CraftTweakerMC.getIEntityLiving(entityLiving), CraftTweakerMC.getIDamageSource(damageSource), new MCEntityLivingDeathEvent(livingDeathEvent));
        } else {
            super.onDeath(entityLiving, iChampionship, damageSource, livingDeathEvent);
        }
    }

    @Override
    public void onKnockback(EntityLiving entityLiving, IChampionship iChampionship, LivingKnockBackEvent livingKnockBackEvent) {
        if(onKnockback != null) {
            onKnockback.handle(CraftTweakerMC.getIEntityLiving(entityLiving), new MCLivingKnockBackEvent(livingKnockBackEvent));
        } else {
            super.onKnockback(entityLiving, iChampionship, livingKnockBackEvent);
        }
    }

    @Override
    public boolean canApply(EntityLiving entityLiving) {
        if(canApply != null) {
            return canApply.handle(CraftTweakerMC.getIEntityLiving(entityLiving));
        } else {
            return super.canApply(entityLiving);
        }
    }

    @Override
    public int getTier() {
        return tier;
    }
}
