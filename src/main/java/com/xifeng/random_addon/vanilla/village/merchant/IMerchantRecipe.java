package com.xifeng.random_addon.vanilla.village.merchant;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.ra.vanilla.IMerchantRecipe")
@ZenRegister
public class IMerchantRecipe {
    private final MerchantRecipe recipe;

    public IMerchantRecipe(MerchantRecipe recipe) {
        this.recipe = recipe;
    }
/*
    @ZenMethod
    public static IMerchantRecipe create(IItemStack buy1, IItemStack buy2, IItemStack sell, int toolUse, int maxTradeUse) {
        ItemStack ibuy1 = CraftTweakerMC.getItemStack(buy1);
        ItemStack ibuy2 = CraftTweakerMC.getItemStack(buy2);
        ItemStack isell = CraftTweakerMC.getItemStack(sell);
        MerchantRecipe newRecipe = new MerchantRecipe(ibuy1, ibuy2, isell, toolUse, maxTradeUse);
        return new IMerchantRecipe(newRecipe);
    }

 */

    @ZenGetter("itemToBuy")
    @ZenMethod
    public IItemStack getItemToBuy(){
        return CraftTweakerMC.getIItemStack(recipe.getItemToBuy());
    }

    @ZenGetter("secondItemToBuy")
    @ZenMethod
    public IItemStack getSecondItemToBuy(){
        return CraftTweakerMC.getIItemStack(recipe.getSecondItemToBuy());
    }

    @ZenGetter("hasSecondItemToBuy")
    @ZenMethod
    public boolean hasSecondItemToBuy(){
        return recipe.hasSecondItemToBuy();
    }

    @ZenGetter("itemToSell")
    @ZenMethod
    public IItemStack getItemToSell(){
        return CraftTweakerMC.getIItemStack(recipe.getItemToSell());
    }

    @ZenGetter("toolUses")
    @ZenMethod
    public int getToolUses(){
        return recipe.getToolUses();
    }

    @ZenGetter("maxTradeUses")
    @ZenMethod
    public int getMaxTradeUses(){
        return recipe.getMaxTradeUses();
    }

    @ZenMethod
    public void addToolUses() {
        recipe.incrementToolUses();
    }

    @ZenMethod
    public void addTradeUses(int amount) {
        recipe.increaseMaxTradeUses(amount);
    }

    @ZenMethod
    public boolean rewardXp() {
        return recipe.getRewardsExp();
    }

    @ZenMethod
    public IData toData() {
        return CraftTweakerMC.getIData(recipe.writeToTags());
    }
}


