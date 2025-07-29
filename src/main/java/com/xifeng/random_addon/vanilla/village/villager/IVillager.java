package com.xifeng.random_addon.vanilla.village.villager;

import com.xifeng.random_addon.vanilla.village.merchant.IMerchantRecipe;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityAgeable;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("mods.ra.vanilla.villager.IVillager")
@ZenRegister
public interface IVillager extends IEntityAgeable {
    //EntityVillager的方法
    @ZenMethod
    void setProfession(int profession);

    @ZenGetter("isMating")
    boolean isMating();

    @ZenMethod
    void setMating(boolean mate);

    @ZenGetter("isPlaying")
    boolean isPlaying();

    @ZenMethod
    void setPlaying(boolean playing);

    //IMerchant 的方法
    @ZenGetter("customer")
    IPlayer getCustomer();

    @ZenGetter("isTrading")
    boolean isTrading();

    @ZenGetter("tradeList")
    List<IMerchantRecipe> getTradeList();

    @ZenGetter("pos")
    IBlockPos getPos();

    //额外的方法
    @ZenGetter("profession")
    int getProf();

    @ZenGetter("professionName")
    String getProfId();

    @ZenGetter("riches")
    int getRiches();

    @ZenGetter("career")
    int getCareer();

    @ZenGetter("careerLevel")
    int getLevel();
}
