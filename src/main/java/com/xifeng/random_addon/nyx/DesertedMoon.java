package com.xifeng.random_addon.nyx;

import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.lunarevents.LunarEvent;
import net.minecraft.util.text.ITextComponent;

//荒芜之月，农作物停止生长，动物也不再自然生成，也不能繁殖
public class DesertedMoon extends LunarEvent {

    public DesertedMoon(NyxWorld nyxWorld) {
        super("deserted_moon", nyxWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return null;
    }

    @Override
    public boolean shouldStart(boolean b) {
        return false;
    }

    @Override
    public boolean shouldStop(boolean b) {
        return false;
    }
}
