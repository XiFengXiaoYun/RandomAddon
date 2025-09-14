package com.xifeng.random_addon.mixin.vanilla;


import com.xifeng.random_addon.vanilla.Attributes;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Block.class)
public abstract class MixinBlock {
    @ModifyVariable(
            method = "harvestBlock",
            at = @At(value = "STORE", ordinal = 0, id = "i"),
            print = true,
            remap = false
    )
    private int get(int original, World world, EntityPlayer player) {
        return original + newMod$getPlayerFortune(player);
    }

    @Unique
    private int newMod$getPlayerFortune(EntityPlayer player) {
        if(player != null) {
            double level = player.getAttributeMap().getAttributeInstance(Attributes.MINELUCK).getAttributeValue();
            return Attributes.getAmount(level);
        }
        return 0;
    }
}
