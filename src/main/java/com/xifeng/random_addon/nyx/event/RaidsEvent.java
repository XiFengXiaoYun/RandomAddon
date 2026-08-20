package com.xifeng.random_addon.nyx.event;

import com.xifeng.random_addon.config.ModConfig;
import com.xifeng.random_addon.nyx.lunarevents.CrescentMoon;
import de.ellpeck.nyx.capabilities.NyxWorld;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.world.World;
import net.minecraftforge.event.village.MerchantTradeOffersEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.smileycorp.raids.common.interfaces.ITradeDiscount;

public final class RaidsEvent {
    @SubscribeEvent
    public static void tradeEvent(MerchantTradeOffersEvent event) {
        if(!ModConfig.Nyxs.CrescentMoon.enable || event.getPlayer() == null || event.getList() == null) return;
        World world = event.getPlayer().world;
        NyxWorld nyxWorld = NyxWorld.get(world);
        if(nyxWorld == null) return;
        if(nyxWorld.currentEvent instanceof CrescentMoon) {
            for (MerchantRecipe recipe : event.getList()) {
                ITradeDiscount trade = (ITradeDiscount) recipe;
                int count = recipe.getItemToBuy().getCount();
                int discount = (int) Math.floor(count * ModConfig.Nyxs.CrescentMoon.discount);
                trade.setDiscountedPrice(Math.max(count - discount, 1));
            }
        }
    }
}
