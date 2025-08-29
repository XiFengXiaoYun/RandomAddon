package com.xifeng.random_addon.nyx.utils;

import com.xifeng.random_addon.nyx.HiddenMoon;
import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.lunarevents.LunarEvent;
import net.minecraft.world.World;

public class NyxUtil extends NyxWorld {
    //public final World world;

    public NyxUtil(World world) {
        super(world);
        //this.world = world;
        super.lunarEvents.add(new HiddenMoon(this));
    }

    public void outPut() {
        for(LunarEvent evt : super.lunarEvents) {
            System.out.print(evt.name);
        }
    }
}
