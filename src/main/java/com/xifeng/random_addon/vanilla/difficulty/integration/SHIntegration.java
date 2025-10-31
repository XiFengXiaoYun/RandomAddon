package com.xifeng.random_addon.vanilla.difficulty.integration;

import com.xifeng.random_addon.config.ModConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.silentchaos512.scalinghealth.event.BlightHandler;
import net.silentchaos512.scalinghealth.world.ScalingHealthSavedData;

public class SHIntegration {
    public static boolean canBlightAdapt(Entity entity) {
        if(ModConfig.Difficulty.ScalingHealth.disableBlight) {
            return !BlightHandler.isBlight((EntityLivingBase) entity);
        }
        return true;
    }

    public static float modifyAdaptAbility(World world) {
        if(!ModConfig.Difficulty.enableScalingHealth) return 1.0f;
        double sh = ScalingHealthSavedData.get(world).difficulty;
        float modifier = (float) (sh / ModConfig.Difficulty.ScalingHealth.adaptAbilityIncreaseRate + 1.0);
        return world.rand.nextFloat() * modifier;
    }

    public static int modifyAdaptLevel(World world) {
        double sh = ScalingHealthSavedData.get(world).difficulty;
        int difficulty = (int) (1 + sh / ModConfig.Difficulty.ScalingHealth.adaptLevelIncreaseRate);
        return Math.min(difficulty, ModConfig.Difficulty.ScalingHealth.maxAdaptLevel);
    }
}
