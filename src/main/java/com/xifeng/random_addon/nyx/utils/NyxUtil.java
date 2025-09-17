package com.xifeng.random_addon.nyx.utils;

import com.xifeng.random_addon.nyx.DesertedMoon;
import com.xifeng.random_addon.nyx.HiddenMoon;
import de.ellpeck.nyx.capabilities.NyxWorld;

public class NyxUtil {

    public static void registerLunarEvents(NyxWorld nyxWorld) {
        nyxWorld.lunarEvents.add(0, new HiddenMoon(nyxWorld));
        //nyxWorld.lunarEvents.add(0, new DesertedMoon(nyxWorld));
    }
}
