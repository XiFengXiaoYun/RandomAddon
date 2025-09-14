package com.xifeng.random_addon.nyx;

import com.xifeng.random_addon.ModConfig;
import com.xifeng.random_addon.nyx.utils.NyxUtil;
import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.lunarevents.LunarEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

//效果：末影人大量生成，玩家在黑暗中呆久了会受到黑暗伤害，死亡信息为“xxx被黑暗吞噬了”
public class HiddenMoon extends LunarEvent {
    public HiddenMoon(NyxWorld nyxWorld) {
        super("hidden_moon", nyxWorld);
    }

    @Override
    public ITextComponent getStartMessage() {
        return new TextComponentTranslation("info.ra.hidden_moon", new Object()).setStyle(new Style().setColor(TextFormatting.DARK_BLUE));
    }

    @Override
    public boolean shouldStart(boolean b) {
        //return ModConfig.Nyxs.hiddenMoon && !this.world.isDaytime();
        return !this.world.isDaytime();
    }

    @Override
    public boolean shouldStop(boolean b) {
        return NyxWorld.isDaytime(this.world);
    }

    public int getSkyColor() {
        return ModConfig.Nyxs.color;
    }
}
