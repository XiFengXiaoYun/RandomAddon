package com.xifeng.random_addon.nyx.utils;

import com.xifeng.random_addon.nyx.CrescentMoon;
import com.xifeng.random_addon.nyx.DarkMoon;
import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.lunarevents.LunarEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class NyxUtil {
    public static final List<Function<NyxWorld, LunarEvent>> FACTORIES = Arrays.asList(
            DarkMoon::new,
            CrescentMoon::new
    );
    public static void registerAll(NyxWorld nyx) {
        FACTORIES.forEach(f -> {
            LunarEvent evt = f.apply(nyx);
            boolean exist = nyx.lunarEvents.stream()
                    .anyMatch(e -> e.getClass() == evt.getClass());
            if (!exist) nyx.lunarEvents.add(0, evt);
        });
    }

    public static MerchantRecipeList modifyTrade(MerchantRecipeList list, float discount) {
        MerchantRecipeList newList = new MerchantRecipeList();
        for(MerchantRecipe recipe : list) {
            NBTTagCompound nbt = recipe.writeToTags();
            //int uses = nbt.getInteger("uses");
            //int maxUses = nbt.getInteger("maxUses");
            int price = Math.max((int) (nbt.getCompoundTag("buy").getInteger("Count") * discount), 1);
            nbt.getCompoundTag("buy").setInteger("Count", price);
            //nbt.setInteger("uses", uses);
            //nbt.setInteger("maxUses", maxUses);
            MerchantRecipe recipe1 = new MerchantRecipe(nbt);

            newList.add(recipe1);
        }
        return newList;
    }

    public static class dataManager implements INBTSerializable<NBTTagCompound> {
        public int daysSinceLast;
        public int startDays;
        public int graceDays;
        public final NyxWorld nyxWorld;

        public dataManager(NyxWorld nyxWorld) {
            this.nyxWorld = nyxWorld;
        }

        public void update(boolean lastDayTime, LunarEvent event, World world, int startDays, int graceDays) {
            if(nyxWorld.currentEvent == event) {
                this.daysSinceLast = 0;
                this.graceDays = 0;
            }
            if(!lastDayTime && NyxWorld.isDaytime(world)) {
                ++this.daysSinceLast;
                if(this.startDays < startDays) {
                    ++this.startDays;
                }
                if(this.graceDays < graceDays) {
                    ++this.graceDays;
                }
            }
        }

        public boolean canStart(LunarEvent event, World world, int startDays, int graceDays, int interval, double chance) {
            if(nyxWorld.forcedEvent == event) {
                return true;
            } else if (this.startDays < startDays) {
                return false;
            } else if (this.graceDays < graceDays) {
                return false;
            } else if (interval > 0) {
                return this.daysSinceLast >= interval;
            }
            return world.rand.nextDouble() < chance;
        }

        public NBTTagCompound serializeNBT() {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("days_since_last", this.daysSinceLast);
            nbt.setInteger("start_days", this.startDays);
            nbt.setInteger("grace_days", this.graceDays);
            return nbt;
        }

        public void deserializeNBT(NBTTagCompound nbt) {
            this.daysSinceLast = nbt.getInteger("days_since_last");
            this.graceDays = nbt.getInteger("grace_days");
            this.startDays = nbt.getInteger("start_days");
        }
    }
}
