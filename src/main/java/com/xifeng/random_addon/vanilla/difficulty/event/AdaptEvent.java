package com.xifeng.random_addon.vanilla.difficulty.event;

import com.xifeng.random_addon.Tags;
import com.xifeng.random_addon.config.ModConfig;
import com.xifeng.random_addon.vanilla.difficulty.capability.AdaptingCapability;
import com.xifeng.random_addon.vanilla.difficulty.capability.CapabilityProvider;
import com.xifeng.random_addon.vanilla.difficulty.capability.IAdaptingCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class AdaptEvent {
    @SubscribeEvent
    public static void attach(AttachCapabilitiesEvent<Entity> evt) {
        if (EventUtil.shouldAdapt(evt.getObject())) {
            evt.addCapability(
                    new ResourceLocation(Tags.MOD_ID, "adapt"),
                    new CapabilityProvider(new AdaptingCapability(), CapabilityProvider.CAP, CapabilityProvider.DEFAULT_FACING)
            );
        }
    }

    @SubscribeEvent
    public static void onJoinWorld(EntityJoinWorldEvent evt) {
        World world = evt.getWorld();
        if(world.isRemote) return;
        if(EventUtil.shouldAdapt(evt.getEntity())) {
            IAdaptingCapability cap = CapabilityProvider.getAdapt(evt.getEntity());
            if(cap == null) return;
            if(cap.adaptLevel() == 0 && cap.adaptAbility() == 0.0f) {
                EventUtil.calculateAdapt(cap, world);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHurt(LivingHurtEvent evt) {
        if(evt.getSource().isDamageAbsolute()) return;

        if(!EventUtil.shouldAdapt(evt.getEntity())) return;

        IAdaptingCapability adaptingCapability = CapabilityProvider.getAdapt(evt.getEntityLiving());
        if(adaptingCapability == null) return;

        Entity entity = evt.getSource().getTrueSource();
        if(ModConfig.Difficulty.onlyPlayer && !(entity instanceof EntityPlayer)) return;

        String type = evt.getSource().damageType;
        if(EventUtil.canAdaptDmg(type)) {
            adaptingCapability.recordHit(type);
            float reduce = adaptingCapability.getDmgModifier(type);
            evt.setAmount(evt.getAmount() * reduce);
        }
    }
}
