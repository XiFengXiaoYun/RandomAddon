package com.xifeng.random_addon;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

@IFMLLoadingPlugin.Name("RACore")
public class ModPlugin implements IFMLLoadingPlugin {
    private static final Map<String, BooleanSupplier> MIXIN_CONFIGS = new LinkedHashMap<>();
    static {
        addMixin("mixins.random_addon.json");
        addMixin("mixins.ra.vanilla.json");
    }
    private static void addMixin(String mixinName) {
        MIXIN_CONFIGS.put(mixinName, ()->true);
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        MIXIN_CONFIGS.forEach((config, supply)->{
            Mixins.addConfiguration(config);
        });
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
