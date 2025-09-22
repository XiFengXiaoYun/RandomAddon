package com.xifeng.random_addon.nyx.event;

import com.xifeng.random_addon.Tags;
import com.xifeng.random_addon.config.ModConfig;
import com.xifeng.random_addon.nyx.lunarevents.*;
import com.xifeng.random_addon.nyx.utils.NyxUtil;
import com.xifeng.random_addon.vanilla.Attributes;
import de.ellpeck.nyx.capabilities.NyxWorld;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public final class NyxEvents {
    public static DamageSource dark = new DamageSource("dark").setDamageBypassesArmor().setDifficultyScaled();
    public static AttributeModifier modifier = new AttributeModifier(UUID.fromString("b359bf93-4155-894f-f849-e51f89ae45eb"),"blue_moon", 3.0, 0);
    public static AttributeModifier modifier1 = new AttributeModifier(UUID.fromString("b359bf93-1145-894f-f849-e51f89ae45eb"), "miner_night", 3.0, 0);

    @SubscribeEvent
    public static void onSpawn(LivingSpawnEvent.CheckSpawn evt) {
        EntityLivingBase entity = evt.getEntityLiving();
        World world = evt.getWorld();
        NyxWorld nyxWorld = NyxWorld.get(world);
        if(nyxWorld == null) return;
        if(evt.getSpawner() == null) {
            if(ModConfig.Nyxs.DarkMoon.enable  && nyxWorld.currentEvent instanceof DarkMoon && !world.isRemote) {
                if(entity instanceof EntityEnderman) {
                    evt.setResult(Event.Result.ALLOW);
                } else if(world.rand.nextDouble() <= ModConfig.Nyxs.DarkMoon.enderManChance) {
                    evt.setResult(Event.Result.DENY);
                    world.spawnEntity(new EntityEnderman(world));
                }
            }
            if(ModConfig.Nyxs.CrescentMoon.enable && nyxWorld.currentEvent instanceof CrescentMoon ) {
                if(Math.random() <= ModConfig.Nyxs.CrescentMoon.mobReduction) {
                    evt.setResult(Event.Result.DENY);
                }
            }
            //if(ModConfig.Nyxs.DesertedMoon.enable && nyxWorld.currentEvent instanceof DesertedMoon) {
                //if(entity instanceof EntityAnimal) evt.setResult(Event.Result.DENY);
            //}
        }
        if(ModConfig.Nyxs.PeacefulMoon.enable && nyxWorld.currentEvent instanceof PeacefulMoon) {
            if(entity instanceof IMob || entity instanceof EntitySlime) evt.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent evt) {
        if(evt.phase == TickEvent.Phase.START) {
            NyxWorld nyxWorld = NyxWorld.get(evt.player.world);
            if(nyxWorld == null) return;
            EntityPlayer player = evt.player;
            if(ModConfig.Nyxs.DarkMoon.enable && nyxWorld.currentEvent instanceof DarkMoon) {
                if (player.world.getWorldTime() % 60 == 0 && !player.world.isRemote && player.world.getLightFromNeighbors(player.getPosition()) <= 4) {
                    player.attackEntityFrom(dark, 0.5f);
                }
            }
            if (ModConfig.Nyxs.BlueMoon.enable) {
                if(nyxWorld.currentEvent instanceof BlueMoon) {
                    NyxUtil.setModifier(player.getEntityAttribute(SharedMonsterAttributes.LUCK), modifier);
                } else {
                    NyxUtil.removeModifier(player.getEntityAttribute(SharedMonsterAttributes.LUCK), modifier);
                }
            }
            if(ModConfig.Nyxs.MinerNight.enable) {
                if(nyxWorld.currentEvent instanceof MinerNight) {
                    NyxUtil.setModifier(player.getEntityAttribute(Attributes.FORTUNELEVEL), modifier1);
                } else {
                    NyxUtil.removeModifier(player.getEntityAttribute(Attributes.FORTUNELEVEL), modifier1);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEnchant(EnchantmentLevelSetEvent evt) {
        NyxWorld nyxWorld = NyxWorld.get(evt.getWorld());
        if(nyxWorld == null) return;
        int oldLevel = evt.getOriginalLevel();
        if(nyxWorld.currentEvent instanceof CrescentMoon) {
            evt.setLevel(oldLevel + 10);
        } else if (nyxWorld.currentEvent instanceof PeacefulMoon) {
            evt.setLevel(oldLevel + 20);
        }
    }

    @SubscribeEvent
    public static void onAttack(LivingHurtEvent evt) {
        NyxWorld nyxWorld = NyxWorld.get(evt.getEntityLiving().world);
        if(nyxWorld != null) {
            if(ModConfig.Nyxs.HunterNight.enable && nyxWorld.currentEvent instanceof HunterNight) {
                if(evt.getEntityLiving() instanceof EntityPlayer && evt.getSource().damageType.equals("mob")) {
                    evt.setAmount(evt.getAmount() * 0.75f);
                } else if (evt.getSource().getTrueSource() instanceof EntityPlayer) {
                    evt.setAmount(evt.getAmount() * 1.25f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLoot(LootingLevelEvent evt) {
        NyxWorld nyxWorld = NyxWorld.get(evt.getEntity().world);
        int level = evt.getLootingLevel();
        if(nyxWorld != null) {
            if(ModConfig.Nyxs.HunterNight.enable && nyxWorld.currentEvent instanceof HunterNight) {
                evt.setLootingLevel(level + 3);
            }
        }
    }

    @SubscribeEvent
    public static void onLootDrop(LivingDropsEvent evt) {
        NyxWorld nyxWorld = NyxWorld.get(evt.getEntityLiving().world);
        List<EntityItem> drops = evt.getDrops();
        if(nyxWorld != null) {
            if(ModConfig.Nyxs.HunterNight.enable && nyxWorld.currentEvent instanceof HunterNight) {
                int level = evt.getLootingLevel();
                Random random = new Random();
                if(random.nextDouble() <= (level + 1) * 0.01) {
                    drops.add(new EntityItem(evt.getEntityLiving().world, evt.getEntityLiving().posX, evt.getEntityLiving().posY, evt.getEntityLiving().posZ, new ItemStack(Items.EMERALD)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onExpDrop(LivingExperienceDropEvent evt) {
        NyxWorld nyxWorld = NyxWorld.get(evt.getAttackingPlayer().world);
        if(nyxWorld != null) {
            if(ModConfig.Nyxs.HunterNight.enable && nyxWorld.currentEvent instanceof HunterNight) {
                evt.setDroppedExperience((int) (evt.getOriginalExperience() * 2.0));
            }
        }
    }

    @SubscribeEvent
    public static void onBlockDrop(BlockEvent.HarvestDropsEvent evt) {
        NyxWorld nyxWorld = NyxWorld.get(evt.getWorld());
        if(nyxWorld != null) {
            Random random = new Random();
            if(ModConfig.Nyxs.MinerNight.enable && nyxWorld.currentEvent instanceof MinerNight && random.nextDouble() <= 0.01) {
                evt.getDrops().add(new ItemStack(Items.DIAMOND));
            }
        }
    }

    @SubscribeEvent
    public static void onHeal(LivingHealEvent evt) {
        NyxWorld nyxWorld = NyxWorld.get(evt.getEntityLiving().world);
        if(nyxWorld != null && evt.getEntityLiving() instanceof EntityPlayer) {
            if(ModConfig.Nyxs.PeacefulMoon.enable && nyxWorld.currentEvent instanceof PeacefulMoon) {
                evt.setAmount(evt.getAmount() * 2.0f);
            } else if (ModConfig.Nyxs.CrescentMoon.enable && nyxWorld.currentEvent instanceof CrescentMoon) {
                evt.setAmount(evt.getAmount() * 1.5f);
            }
        }
    }
/*
    @SubscribeEvent
    public static void onAnimalTame(PlayerInteractEvent.EntityInteract evt) {
        NyxWorld nyxWorld = NyxWorld.get(evt.getWorld());
        if(nyxWorld != null && evt.getTarget() instanceof EntityTameable) {
            EntityTameable entityTameable = (EntityTameable) evt.getTarget();
            if(ModConfig.Nyxs.DesertedMoon.enable && nyxWorld.currentEvent instanceof DesertedMoon && evt.getItemStack()) {
                evt.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onCropGrow(BlockEvent.CropGrowEvent.Pre evt) {
        if(evt.getWorld().isRemote || !ModConfig.Nyxs.DesertedMoon.enable) return;
        NyxWorld nyxWorld = NyxWorld.get(evt.getWorld());
        if(nyxWorld != null && nyxWorld.currentEvent instanceof DesertedMoon) evt.setResult(Event.Result.DENY);
    }

 */
}
