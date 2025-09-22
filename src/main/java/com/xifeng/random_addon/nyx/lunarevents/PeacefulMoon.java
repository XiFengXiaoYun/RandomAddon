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

//敌对生物不再生成，玩家生命恢复速度提升，附魔台等级提升
public class PeacefulMoon extends LunarEvent {
    public final int startNight = ModConfig.Nyxs.PeacefulMoon.startNight;
    public final int interval = ModConfig.Nyxs.PeacefulMoon.interval;
    public final int graceDay = ModConfig.Nyxs.PeacefulMoon.graceDay;
    public final double chance = ModConfig.Nyxs.PeacefulMoon.chance;
    NyxUtil.dataManager manager = new NyxUtil.dataManager(this.nyxWorld);

    public PeacefulMoon(NyxWorld nyxWorld) {
        super("peaceful_moon", nyxWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info.ra.peaceful_moon", new Object()).setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE));
    }

    @Override
    public boolean shouldStart(boolean b) {
        if(!ModConfig.Nyxs.PeacefulMoon.enable) return false;
        return b && !NyxWorld.isDaytime(this.world) && manager.canStart(this, this.world, startNight, graceDay, interval, chance);
    }

    @Override
    public boolean shouldStop(boolean b) {
        return NyxWorld.isDaytime(this.world);
    }

    public int getSkyColor() {
        return ModConfig.Nyxs.PeacefulMoon.color;
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
