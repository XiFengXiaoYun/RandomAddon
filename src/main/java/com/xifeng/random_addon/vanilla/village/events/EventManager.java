package com.xifeng.random_addon.vanilla.village.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventHandle;
import crafttweaker.api.event.IEventManager;
import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import net.minecraftforge.event.village.MerchantTradeOffersEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.events.IEventManager")
@ZenRegister
public class EventManager {
    private static final EventList<TradeOfferEvent> eventList = new EventList<>();

    @ZenMethod
    public static IEventHandle onTradeOffer(IEventManager manager, IEventHandler<TradeOfferEvent> event) {
        return eventList.add(event);
    }

    public static final class EventHandler {
        @SubscribeEvent
        public static void villagerEvent(MerchantTradeOffersEvent evt ) {
            if(eventList.hasHandlers()) {
                eventList.publish(new TradeOfferEvent(evt));
            }
        }
    }

}
