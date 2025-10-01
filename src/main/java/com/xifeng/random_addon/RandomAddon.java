package com.xifeng.random_addon;

import com.xifeng.random_addon.config.ModConfig;
import com.xifeng.random_addon.nyx.event.NyxEvents;
import com.xifeng.random_addon.vanilla.Attributes;
import com.xifeng.random_addon.vanilla.village.events.EventManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = "after:crafttweaker;after:champions;after:tconstruct;after:conarm;after:nyx")
public class RandomAddon {
    public static boolean nyxEnabled() {
        return ModConfig.Nyxs.enable && Loader.isModLoaded("nyx");
    }
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if(nyxEnabled()) MinecraftForge.EVENT_BUS.register(NyxEvents.class);
        MinecraftForge.EVENT_BUS.register(Attributes.Events.class);
        MinecraftForge.EVENT_BUS.register(EventManager.EventHandler.class);
    }
}
