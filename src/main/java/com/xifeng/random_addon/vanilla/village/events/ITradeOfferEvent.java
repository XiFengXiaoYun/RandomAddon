package com.xifeng.random_addon.vanilla.village.events;

import com.xifeng.random_addon.vanilla.village.merchant.IMerchantRecipe;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("mods.ra.vanilla.ITradeOfferEvent")
@ZenRegister
public interface ITradeOfferEvent extends IPlayerEvent {

    @ZenGetter("player")
    @ZenMethod
    IPlayer getPlayer();

    @ZenGetter("world")
    @ZenMethod
    IWorld getWorld();

    @ZenGetter("pos")
    @ZenMethod
    IBlockPos getPos();

    @ZenGetter("tradeListData")
    @ZenMethod
    IData getTradeListData();

    @ZenGetter("tradeList")
    @ZenMethod
    //@SideOnly(Side.SERVER)
    List<IMerchantRecipe> getTradeList();

    //@ZenMethod
    //void setTradeList(IData list);

    //@ZenMethod
    //void addTrade(IMerchantRecipe recipe);

    //@ZenMethod
    //void setTrade(int index, IMerchantRecipe recipe);
}
