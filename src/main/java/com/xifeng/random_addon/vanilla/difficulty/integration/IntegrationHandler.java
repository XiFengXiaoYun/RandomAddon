package com.xifeng.random_addon.vanilla.difficulty.integration;

import com.xifeng.random_addon.config.ModConfig;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

public class IntegrationHandler {
    public static boolean enableSH() {
        return Loader.isModLoaded("scalinghealth") && ModConfig.Difficulty.enableScalingHealth;
    }

    public static boolean enableNyx() {
        return Loader.isModLoaded("nyx") && ModConfig.Difficulty.NyxIntegration.enable;
    }

    public static boolean shouldAdapt(Entity entity) {
        if(enableSH()) return SHIntegration.canBlightAdapt(entity);
        return true;
    }

    public static float getAdaptAbility(World world) {
        float base = 1.0f;
        if(enableSH()) base *= SHIntegration.modifyAdaptAbility(world);
        if(enableNyx()) base *= NyxIntegration.modifyAdapt(world);
        return base;
    }

    public static int getAdaptLevel(World world) {
        if(enableSH()) return SHIntegration.modifyAdaptLevel(world);
        return world.getDifficulty().getId();
    }
}
