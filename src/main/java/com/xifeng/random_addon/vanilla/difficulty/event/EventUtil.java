package com.xifeng.random_addon.vanilla.difficulty.event;

import com.xifeng.random_addon.config.ModConfig;
import com.xifeng.random_addon.vanilla.difficulty.capability.IAdaptingCapability;
import com.xifeng.random_addon.vanilla.difficulty.integration.IntegrationHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class EventUtil {
    public static boolean shouldAdapt(Entity entity) {
        if(EventUtil.ListHelper.blackList(entity)) return false;
        if(entity instanceof EntityMob || EventUtil.ListHelper.whiteList(entity)) {
            return IntegrationHandler.shouldAdapt(entity);
        }
        return false;
    }

    public static boolean canAdaptDmg(String damageType) {
        boolean canAdapt = false;
        for(String name : ModConfig.Difficulty.damageSources) {
            if(damageType.equals(name)) {
                canAdapt = true;
                break;
            }
        }
        return canAdapt;
    }

    public static void calculateAdapt(IAdaptingCapability cap, World world) {
        if(world == null) return;
        float ability = world.rand.nextFloat() * IntegrationHandler.getAdaptAbility(world);
        int level = IntegrationHandler.getAdaptLevel(world);
        cap.setAdaptAbility(ability);
        cap.setAdaptLevel(level);
    }



    public static class ListHelper {
        private static final List<String> BLACK = Arrays.asList(ModConfig.Difficulty.blackList);
        private static final List<String> WHITE = Arrays.asList(ModConfig.Difficulty.whiteList);

        public static boolean whiteList(Entity entity) {
            return checkEntity(entity, WHITE, false);
        }
        public static boolean blackList(Entity entity) {
            return checkEntity(entity, BLACK, true);
        }
        public static boolean checkEntity(Entity entity, List<String> list, boolean result) {
            ResourceLocation rl = EntityList.getKey(entity);
            if (rl == null) return result;
            String name = rl.toString();
            for(String id : list) {
                if(id.equals(name)) return true;
            }
            return false;
        }
    }
}
