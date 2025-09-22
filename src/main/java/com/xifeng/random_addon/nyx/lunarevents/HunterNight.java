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

//效果：造成的伤害提升，受到的伤害减少，掠夺等级增加，击败任意生物均有几率掉落绿宝石，获取的经验提升
public class HunterNight extends LunarEvent {
    public final int startNight = ModConfig.Nyxs.HunterNight.startNight;
    public final int interval = ModConfig.Nyxs.HunterNight.interval;
    public final int graceDay = ModConfig.Nyxs.HunterNight.graceDay;
    public final double chance = ModConfig.Nyxs.HunterNight.chance;
    NyxUtil.dataManager manager = new NyxUtil.dataManager(this.nyxWorld);

    public HunterNight(NyxWorld nyxWorld) {
        super("hunter_night", nyxWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info.ra.hunter_night", new Object()).setStyle(new Style().setColor(TextFormatting.DARK_AQUA));
    }

    @Override
    public boolean shouldStart(boolean b) {
        if(!ModConfig.Nyxs.HunterNight.enable) return false;
        return b && !NyxWorld.isDaytime(this.world) && manager.canStart(this, this.world, startNight, graceDay, interval, chance);
    }

    @Override
    public boolean shouldStop(boolean b) {
        return NyxWorld.isDaytime(this.world);
    }

    public int getSkyColor() {
        return ModConfig.Nyxs.HunterNight.color;
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
