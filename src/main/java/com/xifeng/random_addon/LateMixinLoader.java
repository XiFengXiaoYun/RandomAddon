package com.xifeng.random_addon;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.*;
import java.util.function.BooleanSupplier;

public class LateMixinLoader implements ILateMixinLoader {
    private static final Map<String, BooleanSupplier> MIXIN_CONFIGS = new LinkedHashMap<>();

    @Override
    public List<String> getMixinConfigs() {
        return new ArrayList<>(MIXIN_CONFIGS.keySet());
    }

    static {
        addMixinConfig("mixins.random_addon.nyx.json");
    }

    private static void addMixinConfig(final String mixinConfig) {
        MIXIN_CONFIGS.put(mixinConfig, () -> true);
    }
}
