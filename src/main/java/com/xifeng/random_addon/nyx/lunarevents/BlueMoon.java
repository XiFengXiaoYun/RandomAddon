package com.xifeng.random_addon.nyx.lunarevents;

import com.xifeng.random_addon.config.ModConfig;
import com.xifeng.random_addon.nyx.utils.NyxUtil;
import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.lunarevents.LunarEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

//运气加2，
public class BlueMoon extends LunarEvent {
    public final int startNight = ModConfig.Nyxs.BlueMoon.startNight;
    public final int interval = ModConfig.Nyxs.BlueMoon.interval;
    public final int graceDay = ModConfig.Nyxs.BlueMoon.graceDay;
    public final double chance = ModConfig.Nyxs.BlueMoon.chance;
    NyxUtil.dataManager manager = new NyxUtil.dataManager(this.nyxWorld);

    public BlueMoon(NyxWorld nyxWorld) {
        super("blue_moon", nyxWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info.ra.blue_moon", new Object()).setStyle(new Style().setColor(TextFormatting.BLUE));
    }

    @Override
    public boolean shouldStart(boolean b) {
        if(!ModConfig.Nyxs.BlueMoon.enable) return false;
        return b && !NyxWorld.isDaytime(this.world) && manager.canStart(this, this.world, startNight, graceDay, interval, chance);
    }

    @Override
    public boolean shouldStop(boolean b) {
        return NyxWorld.isDaytime(this.world);
    }

    public int getSkyColor() {
        return ModConfig.Nyxs.BlueMoon.color;
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
