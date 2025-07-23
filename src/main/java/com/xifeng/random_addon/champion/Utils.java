package com.xifeng.random_addon.champion;

import c4.champions.common.capability.CapabilityChampionship;
import c4.champions.common.rank.Rank;
import c4.champions.common.util.ChampionHelper;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLiving;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Objects;

@ZenClass("mods.ra.utils")
@ZenRegister
@ModOnly("champions")
public class Utils {
    @ZenMethod
    public static boolean isChampion(IEntityLiving living) {
        Entity ent = CraftTweakerMC.getEntity(living);
        if (ChampionHelper.isValidChampion(ent)) {
            return CapabilityChampionship.getChampionship((EntityLiving) ent) != null;
        }
        return false;
    }

    @ZenMethod
    public static int getTier(IEntityLiving living) {
        Entity ent = CraftTweakerMC.getEntity(living);
        if (ChampionHelper.isValidChampion(ent) && CapabilityChampionship.getChampionship((EntityLiving) ent) != null) {
            Rank rank = Objects.requireNonNull(CapabilityChampionship.getChampionship((EntityLiving) ent)).getRank();
            if(ChampionHelper.isElite(rank)) {
                return rank.getTier();
            }
        }
        return 0;
    }
}
