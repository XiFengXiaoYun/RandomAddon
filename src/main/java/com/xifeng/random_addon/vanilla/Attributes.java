package com.xifeng.random_addon.vanilla;


import com.xifeng.random_addon.ModConfig;
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
    public static final IAttribute MINELUCK = new RangedAttribute(null, "ra.fortuneLevel", 10.0, 0.0, ModConfig.Attributes.maxLuck).setShouldWatch(false);
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
                ((EntityPlayer) ent).getAttributeMap().registerAttribute(MINELUCK);
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
        }

        @SubscribeEvent
        public static void expEvent(LivingExperienceDropEvent evt) {
            if(evt.getAttackingPlayer().world.isRemote) return;
            double expBonus = evt.getAttackingPlayer().getAttributeMap().getAttributeInstance(EXPBONUS).getAttributeValue();
            int oldExp = evt.getDroppedExperience();
            evt.setDroppedExperience((int) expBonus * oldExp);
        }
    }
}
