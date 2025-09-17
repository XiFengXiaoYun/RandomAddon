package com.xifeng.random_addon.vanilla;


import com.xifeng.random_addon.config.ModConfig;
import de.ellpeck.nyx.capabilities.NyxWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Attributes {
    public static final IAttribute LOOTLEVEL = new RangedAttribute(null, "ra.lootLevel", 0.0, 0.0, ModConfig.Attributes.maxLooting).setShouldWatch(false);
    public static final IAttribute FORTUNELEVEL = new RangedAttribute(null, "ra.fortuneLevel", 0.0, 0.0, ModConfig.Attributes.maxFortune).setShouldWatch(false);
    public static final IAttribute EXPBONUS = new RangedAttribute(null, "ra.expBonus", 1.0, 0.0, ModConfig.Attributes.maxExpBonus).setShouldWatch(false);

    public static int getAmount(double level) {
        double a = level - ((int) level);
        return Math.random() <= a ? ((int) level + 1) : (int) level;
    }

    public static final class Events {
        @SubscribeEvent
        public static void entityEvent(EntityEvent.EntityConstructing evt) {
            final Entity ent = evt.getEntity();
            if (ent instanceof EntityPlayer) {
                ((EntityPlayer) ent).getAttributeMap().registerAttribute(LOOTLEVEL);
                ((EntityPlayer) ent).getAttributeMap().registerAttribute(FORTUNELEVEL);
                ((EntityPlayer) ent).getAttributeMap().registerAttribute(EXPBONUS);
            }
        }


        @SubscribeEvent
        public static void lootEvent(LootingLevelEvent evt) {
            if(evt.getDamageSource().getTrueSource() == null || !(evt.getDamageSource().getTrueSource() instanceof EntityPlayer) || evt.getEntity().world.isRemote) return;
            final Entity entity = evt.getDamageSource().getTrueSource();
            double attributeValue = ((EntityPlayer) entity).getAttributeMap().getAttributeInstance(LOOTLEVEL).getAttributeValue();
            int lootBonus = getAmount(attributeValue);
            int oldLevel = evt.getLootingLevel();
            evt.setLootingLevel(oldLevel + lootBonus);
            //for test
            NyxWorld nyxWorld = NyxWorld.get(entity.world);
            if(nyxWorld.currentEvent == null) {
                System.out.print("current event is null");
                return;
            }
            System.out.print(nyxWorld.currentEvent.name);
        }

        @SubscribeEvent
        public static void expEvent(LivingExperienceDropEvent evt) {
            if(evt.getAttackingPlayer() == null) return;
            if(evt.getAttackingPlayer().world.isRemote) return;
            double expBonus = evt.getAttackingPlayer().getAttributeMap().getAttributeInstance(EXPBONUS).getAttributeValue();
            int oldExp = evt.getDroppedExperience();
            evt.setDroppedExperience((int) expBonus * oldExp);
        }
    }
}
