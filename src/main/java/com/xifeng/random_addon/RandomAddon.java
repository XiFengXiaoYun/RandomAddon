package com.xifeng.random_addon;

import com.xifeng.random_addon.config.ModConfig;
import com.xifeng.random_addon.nyx.event.NyxEvents;
import com.xifeng.random_addon.vanilla.Attributes;
import com.xifeng.random_addon.vanilla.difficulty.AdaptingCapability;
import com.xifeng.random_addon.vanilla.difficulty.CapabilityProvider;
import com.xifeng.random_addon.vanilla.difficulty.IAdaptingCapability;
import com.xifeng.random_addon.vanilla.village.events.EventManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = "after:crafttweaker;after:champions;after:tconstruct;after:conarm;after:nyx;after:scalinghealth")
public class RandomAddon {
    private static boolean nyxEnabled() {
        return ModConfig.Nyxs.enable && Loader.isModLoaded("nyx");
    }
    private static  boolean difficultyEnabled() {
        return ModConfig.Difficulty.enable;
    }
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if(nyxEnabled()) MinecraftForge.EVENT_BUS.register(NyxEvents.class);

        if(difficultyEnabled()) {
            MinecraftForge.EVENT_BUS.register(CapabilityProvider.EventHandler.class);
            CapabilityManager.INSTANCE.register(IAdaptingCapability.class, new IAdaptingCapability.AdaptationStorage(), AdaptingCapability::new);
        }

        MinecraftForge.EVENT_BUS.register(Attributes.Events.class);
        MinecraftForge.EVENT_BUS.register(EventManager.EventHandler.class);
    }
}
