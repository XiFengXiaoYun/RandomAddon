package com.xifeng.random_addon.vanilla.difficulty.integration;

import com.xifeng.random_addon.nyx.lunarevents.CrescentMoon;
import de.ellpeck.nyx.capabilities.NyxWorld;
import net.minecraft.world.World;

public class NyxIntegration {
    public static float modifyAdapt(World world) {
        NyxWorld nyxWorld = NyxWorld.get(world);
        if(nyxWorld != null && nyxWorld.currentEvent instanceof CrescentMoon) return 0;
        return 1.0f;
    }
}
