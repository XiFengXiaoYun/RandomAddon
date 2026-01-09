package com.xifeng.random_addon.mixin.vanilla;


import com.xifeng.random_addon.vanilla.Attributes;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Block.class, priority = 100)
public abstract class MixinBlock {

    @ModifyVariable(
            method = "harvestBlock",
            at = @At(value = "STORE", ordinal = 0, id = "i"),
            require = 0,
            remap = false,
            argsOnly = true)
    private int get(int original, World world, EntityPlayer player) {
        return Math.max((original + newMod$getPlayerFortune(player)), 0);
    }


    @Redirect(
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
            require = 0,
            remap = false
    )
    private int get(Enchantment ench, ItemStack stack, World world, EntityPlayer player) {
        int level =  EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack) + newMod$getPlayerFortune(player);
        return Math.max(level, 0);
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
