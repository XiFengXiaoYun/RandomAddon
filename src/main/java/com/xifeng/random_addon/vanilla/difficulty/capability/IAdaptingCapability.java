package com.xifeng.random_addon.vanilla.difficulty.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.Map;

public interface IAdaptingCapability {
    int getHit(String damageType);

    void recordHit(String damageType);

    //List<String> getAdaptedDamage();

    float getDmgModifier(String damageType);

    //决定适应伤害的比例
    float adaptAbility();

    //决定可适应伤害的种类
    int adaptLevel();

    void setAdaptLevel(int level);

    void setAdaptAbility(float amount);

    class AdaptationStorage implements Capability.IStorage<IAdaptingCapability> {

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IAdaptingCapability> capability, IAdaptingCapability instance, EnumFacing side) {
            NBTTagCompound root = new NBTTagCompound();
            NBTTagCompound damages = new NBTTagCompound();
            for(Map.Entry<String, Integer> e : ((AdaptingCapability) instance).map.entrySet()) {
                damages.setInteger(e.getKey(), e.getValue());
            }
            root.setTag("adapt", damages);
            root.setInteger("adapt_level", instance.adaptLevel());
            root.setFloat("adapt_ability", instance.adaptAbility());
            return root;
        }

        @Override
        public void readNBT(Capability<IAdaptingCapability> capability, IAdaptingCapability instance, EnumFacing side, NBTBase nbt) {
            NBTTagCompound nbtTagCompound = (NBTTagCompound) nbt;
            ((AdaptingCapability) instance).map.clear();
            for(String key : nbtTagCompound.getCompoundTag("adapt").getKeySet()) {
                ((AdaptingCapability) instance).map.put(key, nbtTagCompound.getCompoundTag("adapt").getInteger(key));
            }
            instance.setAdaptAbility(nbtTagCompound.getFloat("adapt_ability"));
            instance.setAdaptLevel(nbtTagCompound.getInteger("adapt_level"));
        }
    }
}
