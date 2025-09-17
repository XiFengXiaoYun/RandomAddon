package com.xifeng.random_addon.mixin.vanilla;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.inventory.SlotMerchantResult;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlotMerchantResult.class)
public abstract class MixinSlotMerchantResult {
    @Final
    @Shadow(remap = false)
    private InventoryMerchant merchantInventory;

    @Inject(method = "onTake", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private void mixinTrade(EntityPlayer thePlayer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if(merchantInventory.getCurrentRecipe().isRecipeDisabled()) cir.setReturnValue(ItemStack.EMPTY);
    }
}
