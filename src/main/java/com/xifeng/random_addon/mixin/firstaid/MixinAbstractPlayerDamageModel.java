package com.xifeng.random_addon.mixin.firstaid;

import ichttt.mods.firstaid.FirstAid;
import ichttt.mods.firstaid.FirstAidConfig;
import ichttt.mods.firstaid.api.damagesystem.AbstractDamageablePart;
import ichttt.mods.firstaid.common.damagesystem.PlayerDamageModel;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Mixin(value = PlayerDamageModel.class, remap = false)
public abstract class MixinAbstractPlayerDamageModel {
    @Shadow
    private float prevScaleFactor;

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lichttt/mods/firstaid/common/damagesystem/PlayerDamageModel;runScaleLogic(Lnet/minecraft/entity/player/EntityPlayer;)V"), remap = false)
    private void runNewScaleLogic(PlayerDamageModel instance, EntityPlayer player) {
        if (FirstAidConfig.scaleMaxHealth) {
            player.world.profiler.startSection("healthscaling");
            float globalFactor = player.getMaxHealth() / 20F;
            if (prevScaleFactor != globalFactor) {
                if (FirstAidConfig.debug) {
                    FirstAid.LOGGER.info("Starting health scaling factor {} -> {} (max health {})", prevScaleFactor, globalFactor, player.getMaxHealth());
                }
                player.world.profiler.startSection("distribution");
                int reduced = 0;
                int added = 0;
                float expectedNewMaxHealth = 0F;
                int newMaxHealth = 0;

                Object2FloatOpenHashMap<String> partMapToHealth = new Object2FloatOpenHashMap<>(8);

                for (AbstractDamageablePart part : instance) {
                    float floatResult = ((float) part.initialMaxHealth) * globalFactor;
                    //Try to save the original health distribution
                    partMapToHealth.put(part.part.name(), part.currentHealth);

                    expectedNewMaxHealth += floatResult;
                    int result = (int) floatResult;
                    if (result % 2 == 1) {
                        int partMaxHealth = part.getMaxHealth();
                        if (part.currentHealth < partMaxHealth && reduced < 4) {
                            result--;
                            reduced++;
                        } else if (part.currentHealth > partMaxHealth && added < 4) {
                            result++;
                            added++;
                        } else if (reduced > added) {
                            result++;
                            added++;
                        } else {
                            result--;
                            reduced++;
                        }
                    }
                    newMaxHealth += result;
                    if (FirstAidConfig.debug) {
                        FirstAid.LOGGER.info("Part {} max health: {} current health: {} initial; {} old; {} new", part.part.name(), part.initialMaxHealth, part.currentHealth, part.getMaxHealth(), result);
                    }
                    part.setMaxHealth(result);
                }
                player.world.profiler.endStartSection("correcting");
                if (Math.abs(expectedNewMaxHealth - newMaxHealth) >= 2F) {
                    if (FirstAidConfig.debug) {
                        FirstAid.LOGGER.info("Entering second stage - diff {}", Math.abs(expectedNewMaxHealth - newMaxHealth));
                    }
                    List<AbstractDamageablePart> prioList = new ArrayList<>();
                    for (AbstractDamageablePart part : instance) {
                        prioList.add(part);
                    }
                    prioList.sort(Comparator.comparingInt(AbstractDamageablePart::getMaxHealth));
                    for (AbstractDamageablePart part : prioList) {
                        int maxHealth = part.getMaxHealth();
                        if (FirstAidConfig.debug) {
                            FirstAid.LOGGER.info("Part {}: Second stage with total diff {}", part.part.name(), Math.abs(expectedNewMaxHealth - newMaxHealth));
                        }
                        if (expectedNewMaxHealth > newMaxHealth) {
                            part.setMaxHealth(maxHealth + 2);
                            //Ensure that all the part's health would not lose;
                            part.currentHealth = partMapToHealth.getFloat(part.part.name());

                            newMaxHealth += (part.getMaxHealth() - maxHealth);
                        } else if (expectedNewMaxHealth < newMaxHealth) {
                            //Here we don't need to modify the current health of the part
                            part.setMaxHealth(maxHealth - 2);
                            newMaxHealth -= (maxHealth - part.getMaxHealth());
                        }
                        if (Math.abs(expectedNewMaxHealth - newMaxHealth) < 2F) {
                            break;
                        }
                    }
                }
                player.world.profiler.endSection();
            }
            prevScaleFactor = globalFactor;
            player.world.profiler.endSection();
        }
    }
}
