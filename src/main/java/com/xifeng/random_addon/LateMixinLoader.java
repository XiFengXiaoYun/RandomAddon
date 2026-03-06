package com.xifeng.random_addon;

import net.minecraftforge.fml.common.Loader;
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
        addMixinConfig("mixins.random_addon.nyx.json", "nyx");
    }

    private static void addMixinConfig(final String mixinConfig, String mod) {
        MIXIN_CONFIGS.put(mixinConfig, () -> Loader.isModLoaded(mod));
        RandomAddon.LOGGER.info("Loaded mod " + mod + " for mixin " + mixinConfig);
    }
}
