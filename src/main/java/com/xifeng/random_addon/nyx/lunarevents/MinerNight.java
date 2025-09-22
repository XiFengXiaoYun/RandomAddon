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

//时运等级加3，挖掘任意矿石均有几率掉落钻石
public class MinerNight extends LunarEvent {
    public final int startNight = ModConfig.Nyxs.MinerNight.startNight;
    public final int interval = ModConfig.Nyxs.MinerNight.interval;
    public final int graceDay = ModConfig.Nyxs.MinerNight.graceDay;
    public final double chance = ModConfig.Nyxs.MinerNight.chance;
    NyxUtil.dataManager manager = new NyxUtil.dataManager(this.nyxWorld);

    public MinerNight(NyxWorld nyxWorld) {
        super("miner_night", nyxWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info.ra.miner_night", new Object()).setStyle(new Style().setColor(TextFormatting.DARK_AQUA));
    }

    @Override
    public boolean shouldStart(boolean b) {
        if(!ModConfig.Nyxs.MinerNight.enable) return false;
        return b && !NyxWorld.isDaytime(this.world) && manager.canStart(this, this.world, startNight, graceDay, interval, chance);
    }

    @Override
    public boolean shouldStop(boolean b) {
        return NyxWorld.isDaytime(this.world);
    }

    public int getSkyColor() {
        return ModConfig.Nyxs.MinerNight.color;
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
