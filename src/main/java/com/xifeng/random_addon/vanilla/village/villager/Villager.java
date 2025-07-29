package com.xifeng.random_addon.vanilla.village.villager;

import com.xifeng.random_addon.vanilla.village.merchant.IMerchantRecipe;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.mc1120.entity.MCEntityAgeable;
import net.minecraft.entity.passive.EntityVillager;

import java.util.List;
import java.util.stream.Collectors;

public class Villager extends MCEntityAgeable implements IVillager {
    private final EntityVillager villager;

    public Villager(EntityVillager villager) {
        super(villager);
        this.villager = villager;
    }


    @Override
    public void setProfession(int profession) {
        villager.setProfession(profession);
    }

    @Override
    public boolean isMating() {
        return villager.isMating();
    }

    @Override
    public void setMating(boolean mate) {
        villager.setMating(mate);
    }

    @Override
    public boolean isPlaying() {
        return villager.isPlaying();
    }

    @Override
    public void setPlaying(boolean playing) {
        villager.setPlaying(playing);
    }

    @Override
    public IPlayer getCustomer() {
        return CraftTweakerMC.getIPlayer(villager.getCustomer());
    }

    @Override
    public boolean isTrading() {
        return villager.isTrading();
    }

    @Override
    public List<IMerchantRecipe> getTradeList() {
        if (villager.getCustomer() != null && villager.getRecipes(villager.getCustomer()) != null) {
            return villager.getRecipes(villager.getCustomer()).stream().map(IMerchantRecipe::new).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public IBlockPos getPos() {
        return CraftTweakerMC.getIBlockPos(villager.getPos());
    }

    @Override
    public int getProf() {
        return villager.getEntityData().getInteger("Profession");
    }

    @Override
    public String getProfId() {
        return villager.getEntityData().getString("ProfessionName");
    }

    @Override
    public int getRiches() {
        return villager.getEntityData().getInteger("Riches");
    }

    @Override
    public int getCareer() {
        return villager.getEntityData().getInteger("Career");
    }

    @Override
    public int getLevel() {
        return villager.getEntityData().getInteger("CareerLevel");
    }


}
