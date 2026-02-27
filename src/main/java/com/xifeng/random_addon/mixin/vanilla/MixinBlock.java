package com.xifeng.random_addon.mixin.vanilla;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.xifeng.random_addon.vanilla.Attributes;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Block.class)
public abstract class MixinBlock {

    @WrapOperation(
            method = "harvestBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/EnchantmentHelper;"
                            + "getEnchantmentLevel("
                            + "Lnet/minecraft/enchantment/Enchantment;"
                            + "Lnet/minecraft/item/ItemStack;"
                            + ")I",
                    ordinal = 1
            ),
            require = 0
    )
    private int getFortuneLevel(Enchantment ench, ItemStack stack, Operation<Integer> original, World world, EntityPlayer player) {
        int originalLevel = original.call(ench, stack);
        return Math.max(originalLevel + newMod$getPlayerFortune(player), 0);
    }

    @Unique
    private int newMod$getPlayerFortune(EntityPlayer player) {
        if(player != null) {
            double level = player.getAttributeMap().getAttributeInstance(Attributes.FORTUNELEVEL).getAttributeValue();
            return Attributes.getAmount(level);
        }
        return 0;
    }
}
