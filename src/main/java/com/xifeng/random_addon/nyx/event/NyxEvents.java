package com.xifeng.random_addon.nyx.event;

import com.xifeng.random_addon.config.ModConfig;
import com.xifeng.random_addon.nyx.lunarevents.*;
import com.xifeng.random_addon.nyx.utils.NyxUtil;
import com.xifeng.random_addon.vanilla.Attributes;
import de.ellpeck.nyx.capabilities.NyxWorld;
import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public final class NyxEvents {
    public static DamageSource dark = new DamageSource("dark").setDamageBypassesArmor().setDifficultyScaled();
    public static AttributeModifier modifier = new AttributeModifier(UUID.fromString("b359bf93-4155-894f-f849-e51f89ae45eb"),"blue_moon", ModConfig.Nyxs.BlueMoon.bonusLuck, 0);
    public static AttributeModifier modifier1 = new AttributeModifier(UUID.fromString("b359bf93-1145-894f-f849-e51f89ae45eb"), "miner_night", ModConfig.Nyxs.MinerNight.bonusFortuneLevel, 0);
    //Add cache for performance
    private static final Object2ByteOpenHashMap<World> CACHE = new Object2ByteOpenHashMap<>();
    private static byte calculateCache(World world) {
        if(world.getWorldTime() % 20 == 0 || !CACHE.containsKey(world)) {
            NyxWorld nw = NyxWorld.get(world);
            byte flag = (byte) (nw != null && nw.currentEvent instanceof DesertedMoon ? 1 : 2);
            CACHE.put(world, flag);
            return flag;
        }
        return CACHE.getByte(world);
    }


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
            if(ModConfig.Nyxs.DesertedMoon.enable && nyxWorld.currentEvent instanceof DesertedMoon) {
                if(entity instanceof EntityAnimal) evt.setResult(Event.Result.DENY);
            }
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
                if (player.world.getWorldTime() % ModConfig.Nyxs.DarkMoon.damageInterval == 0 && !player.world.isRemote && player.world.getLightFromNeighbors(player.getPosition()) <= ModConfig.Nyxs.DarkMoon.lightLevel) {
                    player.attackEntityFrom(dark, (float) ModConfig.Nyxs.DarkMoon.darkDamage);
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
            if(ModConfig.Nyxs.CrescentMoon.enable) {
                if(nyxWorld.currentEvent instanceof CrescentMoon) {
                    Collection<PotionEffect> potionEffects = player.getActivePotionEffects();
                    if(potionEffects.isEmpty()) return;
                    potionEffects.removeIf(effect -> effect.getPotion().isBadEffect());
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
            evt.setLevel(oldLevel + ModConfig.Nyxs.CrescentMoon.bonusEnchantLevel);
        } else if (nyxWorld.currentEvent instanceof PeacefulMoon) {
            evt.setLevel(oldLevel + ModConfig.Nyxs.PeacefulMoon.bonusEnchantLevel);
        }
    }

    @SubscribeEvent
    public static void onAttack(LivingHurtEvent evt) {
        NyxWorld nyxWorld = NyxWorld.get(evt.getEntityLiving().world);
        if(nyxWorld != null) {
            if(ModConfig.Nyxs.HunterNight.enable && nyxWorld.currentEvent instanceof HunterNight) {
                if(evt.getEntityLiving() instanceof EntityPlayer && evt.getSource().damageType.equals("mob")) {
                    evt.setAmount((float) (evt.getAmount() * (1.0 - ModConfig.Nyxs.HunterNight.damageReduction)));
                } else if (evt.getSource().getTrueSource() instanceof EntityPlayer) {
                    evt.setAmount((float) (evt.getAmount() * (1.0 + ModConfig.Nyxs.HunterNight.damageBonus)));
                }
            }
            if(ModConfig.Nyxs.CrescentMoon.enable && nyxWorld.currentEvent instanceof CrescentMoon && evt.getEntityLiving() instanceof EntityPlayer) {
                EntityLivingBase living = evt.getEntityLiving();
                living.hurtTime += 20;
            }
        }
    }

    @SubscribeEvent
    public static void onLoot(LootingLevelEvent evt) {
        NyxWorld nyxWorld = NyxWorld.get(evt.getEntity().world);
        int level = evt.getLootingLevel();
        if(nyxWorld != null) {
            if(ModConfig.Nyxs.HunterNight.enable && nyxWorld.currentEvent instanceof HunterNight) {
                evt.setLootingLevel(level + ModConfig.Nyxs.HunterNight.lootingLevelBonus);
            }
        }
    }

    @SubscribeEvent
    public static void onLootDrop(LivingDropsEvent evt) {
        World world = evt.getEntityLiving().world;
        NyxWorld nyxWorld = NyxWorld.get(world);
        List<EntityItem> drops = evt.getDrops();
        if(nyxWorld != null) {
            if(ModConfig.Nyxs.HunterNight.enable && nyxWorld.currentEvent instanceof HunterNight) {
                int level = evt.getLootingLevel();
                Random random = new Random();
                if(random.nextDouble() <= (level + 1) * 0.01) {
                    double x = evt.getEntityLiving().posX;
                    double y = evt.getEntityLiving().posY;
                    double z = evt.getEntityLiving().posZ;
                    Item dropItem = Item.getByNameOrId(ModConfig.Nyxs.HunterNight.dropItem);
                    if (dropItem != null) {
                        drops.add(new EntityItem(world, x, y, z, new ItemStack(dropItem)));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onExpDrop(LivingExperienceDropEvent evt) {
        if(evt.getAttackingPlayer() == null) return;
        NyxWorld nyxWorld = NyxWorld.get(evt.getAttackingPlayer().world);
        if(nyxWorld != null) {
            if(ModConfig.Nyxs.HunterNight.enable && nyxWorld.currentEvent instanceof HunterNight) {
                double bonus = 1.0 + ModConfig.Nyxs.HunterNight.expBonus;
                evt.setDroppedExperience((int) (evt.getOriginalExperience() * bonus));
            }
        }
    }

    @SubscribeEvent
    public static void onBlockDrop(BlockEvent.HarvestDropsEvent evt) {
        NyxWorld nyxWorld = NyxWorld.get(evt.getWorld());
        if(nyxWorld != null) {
            Random random = new Random();
            if(ModConfig.Nyxs.MinerNight.enable && nyxWorld.currentEvent instanceof MinerNight && random.nextDouble() <= 0.01) {
                if(ModConfig.Nyxs.MinerNight.dropItem != null) {
                    Item item = Item.getByNameOrId(ModConfig.Nyxs.MinerNight.dropItem);
                    if (item != null) {
                        evt.getDrops().add(new ItemStack(item));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onHeal(LivingHealEvent evt) {
        if(evt.getEntityLiving() == null) return;
        NyxWorld nyxWorld = NyxWorld.get(evt.getEntityLiving().world);
        if(nyxWorld != null && evt.getEntityLiving() instanceof EntityPlayer) {
            if(ModConfig.Nyxs.PeacefulMoon.enable && nyxWorld.currentEvent instanceof PeacefulMoon) {
                float heal = (float) (ModConfig.Nyxs.PeacefulMoon.bonusHealSpeed + 1.0);
                evt.setAmount(evt.getAmount() * heal);
            } else if (ModConfig.Nyxs.CrescentMoon.enable && nyxWorld.currentEvent instanceof CrescentMoon) {
                float heal1 = (float) (ModConfig.Nyxs.CrescentMoon.bonusHealSpeed + 1.0);
                evt.setAmount(evt.getAmount() * heal1);
            }
        }
    }

    @SubscribeEvent
    public static void babySpawn(BabyEntitySpawnEvent evt) {
        if(evt.getCausedByPlayer() != null) {
            NyxWorld nyxWorld = NyxWorld.get(evt.getCausedByPlayer().world);
            if(nyxWorld != null && nyxWorld.currentEvent instanceof DesertedMoon) {
                EntityLiving entity1 = evt.getParentA();
                EntityLiving entity2 = evt.getParentB();
                entity1.setHealth(entity1.getMaxHealth() * 0.5f);
                entity2.setHealth(entity2.getHealth() * 0.5f);
                evt.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onCropGrow(BlockEvent.CropGrowEvent.Pre evt) {
        if(evt.getWorld().isRemote || evt.getWorld().isDaytime() || !ModConfig.Nyxs.DesertedMoon.enable) return;
        if(calculateCache(evt.getWorld()) == 1) evt.setResult(Event.Result.DENY);
    }
}
