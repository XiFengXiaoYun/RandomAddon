package com.xifeng.random_addon.vanilla;


import com.xifeng.random_addon.config.ModConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//TODO: 新的属性修饰符：攻击范围：增加横扫攻击范围；护甲穿透()；
public class Attributes {
    private static final IAttribute LOOTLEVEL = new RangedAttribute(null, "ra.lootLevel", 0.0, 0.0, ModConfig.Attributes.maxLooting).setShouldWatch(false);
    public static final IAttribute FORTUNELEVEL = new RangedAttribute(null, "ra.fortuneLevel", 0.0, 0.0, ModConfig.Attributes.maxFortune).setShouldWatch(false);
    private static final IAttribute EXPBONUS = new RangedAttribute(null, "ra.expBonus", 1.0, 0.0, ModConfig.Attributes.maxExpBonus).setShouldWatch(false);
    private static final IAttribute ARMORPIERCE = new RangedAttribute(null, "ra.armorPierce", 0.0, 0.0, 1.0).setShouldWatch(false);

    public static int getAmount(double level) {
        double a = level - ((int) level);
        return Math.random() <= a ? ((int) level + 1) : (int) level;
    }

    public static final class Events {
        @SubscribeEvent
        public static void entityEvent(EntityEvent.EntityConstructing evt) {
            final Entity ent = evt.getEntity();
            if (ent instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) ent;
                player.getAttributeMap().registerAttribute(LOOTLEVEL);
                player.getAttributeMap().registerAttribute(FORTUNELEVEL);
                player.getAttributeMap().registerAttribute(EXPBONUS);
                player.getAttributeMap().registerAttribute(ARMORPIERCE);
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
            if(evt.getAttackingPlayer() == null) return;
            if(evt.getAttackingPlayer().world.isRemote) return;
            double expBonus = evt.getAttackingPlayer().getAttributeMap().getAttributeInstance(EXPBONUS).getAttributeValue();
            int oldExp = evt.getDroppedExperience();
            evt.setDroppedExperience((int) expBonus * oldExp);
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void attackEvent(LivingHurtEvent evt) {
            if(evt.getSource().getTrueSource() != null && evt.getSource().getTrueSource() instanceof EntityPlayer && !evt.getSource().isDamageAbsolute() && !evt.getSource().isUnblockable()) {
                EntityPlayer player = (EntityPlayer) evt.getSource().getTrueSource();
                double armorPierce = player.getAttributeMap().getAttributeInstance(ARMORPIERCE).getAttributeValue();
                if(armorPierce <= 0.0) return;
                float oldAmount = evt.getAmount();
                evt.setAmount((float) (oldAmount * (1.0 - armorPierce)));
                evt.getEntityLiving().hurtResistantTime = 0;
                evt.getEntityLiving().lastDamage = 0;
                evt.getEntityLiving().attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor(), (float) (oldAmount * armorPierce));
            }
        }
    }
}
