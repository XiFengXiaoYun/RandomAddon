package com.xifeng.random_addon.vanilla.difficulty.capability;

import com.xifeng.random_addon.Tags;
import com.xifeng.random_addon.config.ModConfig;
import com.xifeng.random_addon.nyx.lunarevents.CrescentMoon;
import de.ellpeck.nyx.capabilities.NyxWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.silentchaos512.scalinghealth.event.BlightHandler;
import net.silentchaos512.scalinghealth.world.ScalingHealthSavedData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class CapabilityProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {
    @CapabilityInject(IAdaptingCapability.class)
    public static final net.minecraftforge.common.capabilities.Capability<IAdaptingCapability> CAP = null;
    public static final EnumFacing DEFAULT_FACING = null;
    final Capability<IAdaptingCapability> cap;
    final IAdaptingCapability INSTANCE;
    final EnumFacing facing;

    CapabilityProvider(IAdaptingCapability instance, Capability<IAdaptingCapability> cap, @Nullable EnumFacing facing) {
        this.cap = cap;
        this.facing = facing;
        this.INSTANCE = instance;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        //return CAP == this.cap;
        return capability == this.cap;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == this.cap ? this.cap.cast(this.INSTANCE) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return this.cap != null ? (NBTTagCompound) this.cap.writeNBT(this.INSTANCE, this.facing) : new NBTTagCompound();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (this.cap != null) {
            this.cap.readNBT(this.INSTANCE, this.facing, nbt);
        }
    }

    public static IAdaptingCapability getAdapt(Entity entity) {
        return entity != null && entity.hasCapability(CAP, DEFAULT_FACING) ? entity.getCapability(CAP, DEFAULT_FACING) : null;
    }

    private static class ListHelper {
        private static final List<String> BLACK = Arrays.asList(ModConfig.Difficulty.blackList);
        private static final List<String> WHITE = Arrays.asList(ModConfig.Difficulty.whiteList);

        private static boolean whiteList(Entity entity) {
            return checkEntity(entity, WHITE, false);
        }

        private static boolean blackList(Entity entity) {
            return checkEntity(entity, BLACK, true);
        }
        private static boolean checkEntity(Entity entity, List<String> list, boolean result) {
            ResourceLocation rl = EntityList.getKey(entity);
            if (rl == null) return result;
            String name = rl.toString();
            for(String id : list) {
                if(id.equals(name)) return true;
            }
            return false;
        }
    }

    public static class EventHandler {

        private static boolean shouldAdapt(Entity entity) {
            if(ListHelper.blackList(entity)) return false;
            if(entity instanceof EntityMob || ListHelper.whiteList(entity)) {
                if(ModConfig.Difficulty.ScalingHealth.disableBlight) return !BlightHandler.isBlight((EntityLivingBase) entity);
                return true;
            }
            return false;
        }

        private static void calculateAdapt(IAdaptingCapability cap, World world) {
            if(ModConfig.Difficulty.enableScalingHealth) {
                double sh = ScalingHealthSavedData.get(world).difficulty;
                int difficulty = (int) (1 + sh / ModConfig.Difficulty.ScalingHealth.adaptLevelIncreaseRate);
                float modifier = (float) (sh / ModConfig.Difficulty.ScalingHealth.adaptAbilityIncreaseRate + 1.0);
                float ability = world.rand.nextFloat() * modifier;
                cap.setAdaptAbility(ability);
                cap.setAdaptLevel(Math.min(difficulty, ModConfig.Difficulty.ScalingHealth.maxAdaptLevel));
            } else {
                cap.setAdaptAbility(world.rand.nextFloat());
                cap.setAdaptLevel(world.getDifficulty().getId());
            }
            if(ModConfig.Difficulty.NyxIntegration.enable){
                NyxWorld nyxWorld = NyxWorld.get(world);
                if(nyxWorld.currentEvent instanceof CrescentMoon) {
                    cap.setAdaptAbility(0);
                }
            }
        }

        private static boolean canAdaptDmg(String damageType) {
            boolean canAdapt = false;
            for(String name : ModConfig.Difficulty.damageSources) {
                if(damageType.equals(name)) {
                    canAdapt = true;
                    break;
                }
            }
            return canAdapt;
        }

        @SubscribeEvent
        public static void attach(AttachCapabilitiesEvent<Entity> evt) {
            if (shouldAdapt(evt.getObject())) {
                evt.addCapability(
                        new ResourceLocation(Tags.MOD_ID, "adapt"),
                        new CapabilityProvider(new AdaptingCapability(), CAP, DEFAULT_FACING)
                );
            }
        }

        @SubscribeEvent
        public static void onJoinWorld(EntityJoinWorldEvent evt) {
            World world = evt.getWorld();
            if(world.isRemote) return;
            if(shouldAdapt(evt.getEntity())) {
                IAdaptingCapability cap = getAdapt(evt.getEntity());
                if(cap == null) return;
                if(cap.adaptLevel() == 0 && cap.adaptAbility() == 0.0f) {
                    calculateAdapt(cap, world);
                }
            }
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void onHurt(LivingHurtEvent evt) {
            if(evt.getSource().isDamageAbsolute()) return;

            if(!shouldAdapt(evt.getEntity())) return;

            IAdaptingCapability adaptingCapability = getAdapt(evt.getEntityLiving());
            if(adaptingCapability == null) return;

            Entity entity = evt.getSource().getTrueSource();
            if(ModConfig.Difficulty.onlyPlayer && !(entity instanceof EntityPlayer)) return;

            String type = evt.getSource().damageType;
            if(canAdaptDmg(type)) {
                adaptingCapability.recordHit(type);
                float reduce = adaptingCapability.getDmgModifier(type);
                evt.setAmount(evt.getAmount() * reduce);
            }
        }
    }
}
