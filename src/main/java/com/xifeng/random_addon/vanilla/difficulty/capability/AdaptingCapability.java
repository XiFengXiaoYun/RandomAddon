package com.xifeng.random_addon.vanilla.difficulty.capability;

import com.xifeng.random_addon.config.ModConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AdaptingCapability implements IAdaptingCapability {
    private int adaptLevel = 0;
    private float adaptAbility = 0;
    public final Map<String, Integer> map = new ConcurrentHashMap<>();

    @Override
    public int getHit(String damageType) {
        return map.getOrDefault(damageType, 0);
    }

    @Override
    public float adaptAbility() {
        return adaptAbility;
    }

    @Override
    public int adaptLevel() {
        return this.adaptLevel;
    }

    @Override
    public void setAdaptLevel(int level) {
        this.adaptLevel = level;
    }

    @Override
    public void setAdaptAbility(float amount) {
        this.adaptAbility = amount;
    }

    @Override
    public void recordHit(String damageType) {
        if(map.containsKey(damageType)) {
            map.merge(damageType, 1, Integer::sum);
        } else {
            if(map.size() < this.adaptLevel) {
                map.merge(damageType, 1, Integer::sum);
            }
        }
    }

    /*@Override
    public List<String> getAdaptedDamage() {
        return new ArrayList<>(this.map.keySet());
    }
     */

    @Override
    public float getDmgModifier(String damageType) {
        int hit = this.getHit(damageType);
        int threshold = Math.max(ModConfig.Difficulty.minHits, ModConfig.Difficulty.maxHits - this.adaptLevel);
        int times = hit / threshold;
        if(times > 0) {
            return Math.max(ModConfig.Difficulty.minDmgModifier, 1 - times * ModConfig.Difficulty.reduction * this.adaptAbility);
        }
        return 1.0f;
    }
}
