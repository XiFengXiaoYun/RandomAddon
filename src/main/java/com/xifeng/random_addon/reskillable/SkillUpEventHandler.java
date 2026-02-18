package com.xifeng.random_addon.reskillable;

import codersafterdark.reskillable.api.event.LevelUpEvent;
import codersafterdark.reskillable.api.event.LockUnlockableEvent;
import codersafterdark.reskillable.api.event.UnlockUnlockableEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public final class SkillUpEventHandler {
    @SubscribeEvent
    public static void onLevelUp(LevelUpEvent.Post evt) {
        if(evt.getEntityPlayer() == null) return;
        EntityPlayer player = evt.getEntityPlayer();
        int level = evt.getLevel();
        int oldLevel = evt.getOldLevel();
        if(SkillUtils.checkSkillMatch(evt.getSkill().getKey())) {
            SkillAttributeEntry skillAttributeEntry = SkillUtils.getSkillEntry(evt.getSkill().getKey());
            if(level > oldLevel) {
                SkillUtils.applyAllModifiers(player, skillAttributeEntry, level);
            } else if(oldLevel > level) {
                SkillUtils.removeModifier(player, skillAttributeEntry);
                SkillUtils.applyAllModifiers(player, skillAttributeEntry, level);
            }

        }
    }

    @SubscribeEvent
    public static void onUnlockUnlockable(UnlockUnlockableEvent.Post evt) {
        if(evt.getEntityPlayer() == null) return;
        EntityPlayer player = evt.getEntityPlayer();
        String traitName = evt.getUnlockable().getKey();
        if(SkillUtils.checkTraitMatch(traitName)) {
            TraitAttributeEntry traitAttributeEntry = SkillUtils.getTraitEntry(traitName);
            SkillUtils.applyTraitModifier(player, traitAttributeEntry);
        }
    }

    @SubscribeEvent
    public static void onLockUnlockable(LockUnlockableEvent.Post evt) {
        if(evt.getEntityPlayer() == null) return;
        EntityPlayer player = evt.getEntityPlayer();
        String traitName = evt.getUnlockable().getKey();
        if(SkillUtils.checkTraitMatch(traitName)) {
            TraitAttributeEntry traitAttributeEntry = SkillUtils.getTraitEntry(traitName);
            SkillUtils.removeModifier(player, traitAttributeEntry);
        }
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if(event.player == null) return;
        EntityPlayer player = event.player;
        SkillUtils.syncAttribute(player);
    }

    @SubscribeEvent
    public static void onLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.player == null) return;
        SkillUtils.syncAttribute(event.player);
    }
}
