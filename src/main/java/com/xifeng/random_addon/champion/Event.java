package com.xifeng.random_addon.champion;

import c4.champions.common.affix.AffixRegistry;
import c4.champions.common.affix.core.AffixBase;
import c4.champions.common.capability.CapabilityChampionship;
import c4.champions.common.capability.IChampionship;
import c4.champions.common.util.ChampionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class Event {
    @SubscribeEvent
    public static void attackEvent(LivingDamageEvent event) {
        if(event.getSource().getTrueSource() == null) return;
        Entity trueSource = event.getSource().getTrueSource();
        if(!(trueSource instanceof EntityLiving)) return;
        EntityLiving living = (EntityLiving) trueSource;
        if (ChampionHelper.isValidChampion(living)) {
            IChampionship chp = CapabilityChampionship.getChampionship(living);
            if (chp != null) {
                float amount = event.getAmount();
                float newAmount = amount;
                for (String aff : chp.getAffixes()) {
                    AffixBase affix = AffixRegistry.getAffix(aff);
                    if (affix instanceof CustomAffix) {
                        newAmount = ((CustomAffix) affix).calcDamage(living, event.getEntityLiving(), event.getSource(), amount, newAmount);
                    }
                }
                event.setAmount(newAmount);
            }
        }
    }
}
