package com.xifeng.random_addon.nyx.event;

import com.xifeng.random_addon.Tags;
import com.xifeng.random_addon.config.ModConfig;
import com.xifeng.random_addon.nyx.CrescentMoon;
import com.xifeng.random_addon.nyx.DarkMoon;
import com.xifeng.random_addon.nyx.utils.NyxUtil;
import de.ellpeck.nyx.capabilities.NyxWorld;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.village.MerchantTradeOffersEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public final class NyxEvents {
    public static DamageSource dark = new DamageSource("dark").setDamageBypassesArmor().setDifficultyScaled();

    @SubscribeEvent
    public static void onSpawn(LivingSpawnEvent.CheckSpawn evt) {
        EntityLivingBase entity = evt.getEntityLiving();
        World world = evt.getWorld();
        if(evt.getSpawner() == null) {
            NyxWorld nyxWorld = NyxWorld.get(world);
            if(nyxWorld != null && nyxWorld.currentEvent instanceof DarkMoon && !world.isRemote) {
                if(entity instanceof EntityEnderman) {
                    evt.setResult(Event.Result.ALLOW);
                } else if(world.rand.nextDouble() <= ModConfig.Nyxs.DarkMoon.enderManChance) {
                    evt.setResult(Event.Result.DENY);
                    world.spawnEntity(new EntityEnderman(world));
                }
            }
            if(nyxWorld != null && nyxWorld.currentEvent instanceof CrescentMoon ) {
                if(Math.random() <= ModConfig.Nyxs.CrescentMoon.mobReduction) {
                    evt.setResult(Event.Result.DENY);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent evt) {
        if(evt.phase == TickEvent.Phase.START) {
            NyxWorld nyxWorld = NyxWorld.get(evt.player.world);
            if(!(nyxWorld.currentEvent instanceof DarkMoon)) return;
            EntityPlayer player = evt.player;
            if(player.world.getWorldTime() % 60 == 0 && !player.world.isRemote && player.world.getLightFromNeighbors(player.getPosition()) <= 4) {
                player.attackEntityFrom(dark, 0.5f);
            }
        }
    }

    @SubscribeEvent
    public static void onTrade(MerchantTradeOffersEvent evt) {
        World world = evt.getPlayer().world;
        NyxWorld nyxWorld = NyxWorld.get(world);
        if(nyxWorld != null) {
            MerchantRecipeList list = evt.getList();
            if (list != null) {
                MerchantRecipeList newList = NyxUtil.modifyTrade(list, 0.5f);
                if(!world.isRemote) {
                    evt.setList(newList);
                }
            }
        }
    }
}
