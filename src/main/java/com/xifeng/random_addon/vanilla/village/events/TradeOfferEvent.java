package com.xifeng.random_addon.vanilla.village.events;

import com.xifeng.random_addon.vanilla.village.merchant.IMerchantRecipe;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
//import net.minecraft.item.ItemStack;
//import net.minecraft.village.MerchantRecipe;
//import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.village.MerchantTradeOffersEvent;
import stanhebben.zenscript.annotations.ZenClass;

import java.util.List;
import java.util.stream.Collectors;

@ZenClass("mods.ra.vanilla.TradeOfferEvent")
@ZenRegister
public class TradeOfferEvent implements ITradeOfferEvent {
    private final MerchantTradeOffersEvent evt;

    public TradeOfferEvent(MerchantTradeOffersEvent event) {
        this.evt = event;
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(evt.getPlayer());
    }

    @Override
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(evt.getMerchant().getWorld());
    }

    @Override
    public IBlockPos getPos() {
        return CraftTweakerMC.getIBlockPos(evt.getMerchant().getPos());
    }

    @Override
    public IData getTradeListData() {
        if (evt.getList() != null) {
            return CraftTweakerMC.getIData(evt.getList().getRecipiesAsTags());
        } else {
            return null;
        }
    }

    @Override
    public List<IMerchantRecipe> getTradeList() {
        if (evt.getList() != null) {
            return evt.getList().stream().map(IMerchantRecipe::new).collect(Collectors.toList());
        } else {
            return null;
        }
    }
/*
    @Override
    public void setTradeList(IData list) {
        evt.setList(new MerchantRecipeList(CraftTweakerMC.getNBTCompound(list)));
    }

    @Override
    public void addTrade(IMerchantRecipe recipe) {
        if (evt.getList() != null) {
            ItemStack ibuy1 = CraftTweakerMC.getItemStack(recipe.getItemToBuy());
            ItemStack ibuy2 = CraftTweakerMC.getItemStack(recipe.getSecondItemToBuy());
            ItemStack isell = CraftTweakerMC.getItemStack(recipe.getItemToSell());
            evt.getList().add(new MerchantRecipe(ibuy1, ibuy2, isell, recipe.getToolUses(), recipe.getMaxTradeUses()));
        }
    }

    @Override
    public void setTrade(int index, IMerchantRecipe recipe) {
        if(evt.getList() != null) {
            ItemStack ibuy1 = CraftTweakerMC.getItemStack(recipe.getItemToBuy());
            ItemStack ibuy2 = CraftTweakerMC.getItemStack(recipe.getSecondItemToBuy());
            ItemStack isell = CraftTweakerMC.getItemStack(recipe.getItemToSell());
            evt.getList().set(index, new MerchantRecipe(ibuy1, ibuy2, isell, recipe.getToolUses(), recipe.getMaxTradeUses()));
        }
    }

 */
}
