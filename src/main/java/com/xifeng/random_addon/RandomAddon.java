package com.xifeng.random_addon;

import com.xifeng.random_addon.nyx.utils.NyxUtil;
import com.xifeng.random_addon.vanilla.Attributes;
import com.xifeng.random_addon.vanilla.village.events.EventManager;
import de.ellpeck.nyx.capabilities.NyxWorld;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = "after:crafttweaker;after:champions;after:tconstruct;after:conarm;after:nyx")
public class RandomAddon {
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(Attributes.Events.class);
        MinecraftForge.EVENT_BUS.register(EventManager.EventHandler.class);
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        World world = server.getWorld(0);
        if(world != null) {
            NyxWorld nyxWorld = NyxWorld.get(world);
            NyxUtil.registerLunarEvents(nyxWorld);
        }
    }
}
