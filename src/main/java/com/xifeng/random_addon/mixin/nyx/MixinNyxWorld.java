package com.xifeng.random_addon.mixin.nyx;

import com.xifeng.random_addon.nyx.utils.NyxUtil;
import de.ellpeck.nyx.capabilities.NyxWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NyxWorld.class, remap = false)
public abstract class MixinNyxWorld {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void register(World world, CallbackInfo ci) {
        NyxWorld nyxWorld = (NyxWorld)(Object) this;
        NyxUtil.registerAll(nyxWorld);
    }
}
