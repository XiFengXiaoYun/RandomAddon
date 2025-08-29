package com.xifeng.random_addon.vanilla;


import com.xifeng.random_addon.ModConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Random;

public class Attributes {
    private static final IAttribute LOOTLEVEL = new RangedAttribute(null, "ra.lootLevel", 0.0, 0.0, ModConfig.Attributes.maxLooting).setShouldWatch(false);
    public static final IAttribute MINELUCK = new RangedAttribute(null, "ra.miningLuck", 0.0, 0.0, ModConfig.Attributes.maxLuck).setShouldWatch(false);
    private static final IAttribute EXPBONUS = new RangedAttribute(null, "ra.expBonus", 1.0, 0.0, ModConfig.Attributes.maxExpBonus).setShouldWatch(false);

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

        //TODO 配置白名单
        @SubscribeEvent
        public static void mineEvent(HarvestDropsEvent evt) {
            if(evt.isSilkTouching() || evt.getWorld().isRemote || evt.getHarvester() == null || !evt.getState().getBlock().getTranslationKey().contains("ore")) return;
            List<ItemStack> drops = evt.getDrops();
            double miningLuck = evt.getHarvester().getAttributeMap().getAttributeInstance(MINELUCK).getAttributeValue();
            Random rand = new Random();
            int amount = rand.nextInt(getAmount(miningLuck) + 1) + 1;
            IBlockState state = evt.getState();
            for(int i = 0; i < drops.size(); i++) {
                Item item = drops.get(i).getItem();
                if(item != Item.getItemFromBlock(state.getBlock())) {
                    ItemStack itemStack = new ItemStack(item, amount);
                    drops.set(i, itemStack);
                }
            }
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
