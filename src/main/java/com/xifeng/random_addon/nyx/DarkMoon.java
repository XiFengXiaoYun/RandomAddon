package com.xifeng.random_addon.nyx;

import com.xifeng.random_addon.config.ModConfig;
import com.xifeng.random_addon.nyx.utils.NyxUtil;
import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.lunarevents.LunarEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

//效果：末影人大量生成，玩家在黑暗中呆久了会受到黑暗伤害，死亡信息为“xxx被黑暗吞噬了”
public class DarkMoon extends LunarEvent {
    public final int startNight = ModConfig.Nyxs.DarkMoon.startNight;
    public final int interval = ModConfig.Nyxs.DarkMoon.interval;
    public final int graceDay = ModConfig.Nyxs.DarkMoon.graceDay;
    public final double chance = ModConfig.Nyxs.DarkMoon.chance;
    NyxUtil.dataManager manager = new NyxUtil.dataManager(this.nyxWorld);

    public DarkMoon(NyxWorld nyxWorld) {
        super("dark_moon", nyxWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info.ra.dark_moon", new Object()).setStyle(new Style().setColor(TextFormatting.GRAY));
    }

    @Override
    public boolean shouldStart(boolean b) {
        if(!ModConfig.Nyxs.DarkMoon.enable) return false;
        return b && !NyxWorld.isDaytime(this.world) && manager.canStart(this, this.world, startNight, graceDay, interval, chance);
    }

    @Override
    public boolean shouldStop(boolean b) {
        return NyxWorld.isDaytime(this.world);
    }

    public int getSkyColor() {
        return ModConfig.Nyxs.DarkMoon.color;
    }

    public void update(boolean b) {
        manager.update(b, this, this.world, startNight, graceDay);
    }

    public NBTTagCompound serializeNBT() {
        return manager.serializeNBT();
    }

    public void deserializeNBT(NBTTagCompound nbt) {
        manager.deserializeNBT(nbt);
    }
}
