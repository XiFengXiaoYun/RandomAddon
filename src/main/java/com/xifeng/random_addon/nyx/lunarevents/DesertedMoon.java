package com.xifeng.random_addon.nyx.lunarevents;
/*
import com.xifeng.random_addon.config.ModConfig;
import com.xifeng.random_addon.nyx.utils.NyxUtil;
import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.lunarevents.LunarEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

//荒芜之月，农作物停止生长，动物也不再自然生成，也不能繁殖
public class DesertedMoon extends LunarEvent {

    public final int startNight = ModConfig.Nyxs.DesertedMoon.startNight;
    public final int interval = ModConfig.Nyxs.DesertedMoon.interval;
    public final int graceDay = ModConfig.Nyxs.DesertedMoon.graceDay;
    public final double chance = ModConfig.Nyxs.DesertedMoon.chance;
    NyxUtil.dataManager manager = new NyxUtil.dataManager(this.nyxWorld);

    public DesertedMoon(NyxWorld nyxWorld) {
        super("deserted_moon", nyxWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info.ra.deserted_moon", new Object()).setStyle(new Style().setColor(TextFormatting.YELLOW));
    }

    @Override
    public boolean shouldStart(boolean b) {
        if(!ModConfig.Nyxs.DesertedMoon.enable) return false;
        return b && !NyxWorld.isDaytime(this.world) && manager.canStart(this, this.world, startNight, graceDay, interval, chance);
    }

    @Override
    public boolean shouldStop(boolean b) {
        return NyxWorld.isDaytime(this.world);
    }

    public int getSkyColor() {
        return ModConfig.Nyxs.DesertedMoon.color;
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

 */
