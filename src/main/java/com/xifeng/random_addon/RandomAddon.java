package com.xifeng.random_addon;

import com.xifeng.random_addon.config.ModConfig;
import com.xifeng.random_addon.nyx.event.NyxEvents;
import com.xifeng.random_addon.reskillable.SkillUpEventHandler;
import com.xifeng.random_addon.reskillable.SkillUtils;
import com.xifeng.random_addon.vanilla.Attributes;
import com.xifeng.random_addon.vanilla.difficulty.capability.AdaptingCapability;
import com.xifeng.random_addon.vanilla.difficulty.capability.IAdaptingCapability;
import com.xifeng.random_addon.vanilla.difficulty.event.AdaptEvent;
import com.xifeng.random_addon.vanilla.village.events.EventManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = "after:crafttweaker;after:champions;after:tconstruct;after:conarm;after:nyx;after:scalinghealth;after:reskillable;after:compatskills")
public class RandomAddon {
    public static boolean nyxEnabled() {
        return ModConfig.Nyxs.enable && Loader.isModLoaded("nyx");
    }
    public static  boolean difficultyEnabled() {
        return ModConfig.Difficulty.enable;
    }
    public static boolean reskillableEnabled() {return ModConfig.ReskillableCompact.enable && Loader.isModLoaded("reskillable");}
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if(nyxEnabled()) {
            MinecraftForge.EVENT_BUS.register(NyxEvents.class);
        }

        if(difficultyEnabled()) {
            MinecraftForge.EVENT_BUS.register(AdaptEvent.class);
            CapabilityManager.INSTANCE.register(IAdaptingCapability.class, new IAdaptingCapability.AdaptationStorage(), AdaptingCapability::new);
        }

        if(reskillableEnabled()) {
            MinecraftForge.EVENT_BUS.register(SkillUpEventHandler.class);
        }

        MinecraftForge.EVENT_BUS.register(Attributes.Events.class);
        MinecraftForge.EVENT_BUS.register(EventManager.EventHandler.class);
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if(reskillableEnabled()) {
            SkillUtils.getAttributeFromConfig();
        }
    }
}
