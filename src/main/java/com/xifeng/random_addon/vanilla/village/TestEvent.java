package com.xifeng.random_addon.vanilla.village;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.village.MerchantTradeOffersEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class TestEvent {
    @SubscribeEvent
    public static void tradeEvent(MerchantTradeOffersEvent event) {
        if(event.getPlayer() != null) {
            EntityPlayer player = event.getPlayer();
            IMerchant merchant = event.getMerchant();
            player.sendMessage(new TextComponentString("merchant name:" + merchant.getDisplayName()));
        }
    }
}
