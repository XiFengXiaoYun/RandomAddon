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

//新月：村民交易价格降低，刷怪减少20%
public class CrescentMoon extends LunarEvent {
    public final int startNight = ModConfig.Nyxs.CrescentMoon.startNight;
    public final int interval = ModConfig.Nyxs.CrescentMoon.interval;
    public final int graceDay = ModConfig.Nyxs.CrescentMoon.graceDay;
    public final double chance = ModConfig.Nyxs.CrescentMoon.chance;
    NyxUtil.dataManager manager = new NyxUtil.dataManager(this.nyxWorld);

    public CrescentMoon(NyxWorld nyxWorld) {
        super("crescent_moon", nyxWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info.ra.crescent_moon", new Object()).setStyle(new Style().setColor(TextFormatting.AQUA));
    }

    @Override
    public boolean shouldStart(boolean b) {
        if(!ModConfig.Nyxs.CrescentMoon.enable) return false;
        return b && !NyxWorld.isDaytime(this.world) && manager.canStart(this, this.world, startNight, graceDay, interval, chance);
    }

    @Override
    public boolean shouldStop(boolean b) {
        return NyxWorld.isDaytime(this.world);
    }

    public int getSkyColor() {
        return ModConfig.Nyxs.CrescentMoon.color;
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
