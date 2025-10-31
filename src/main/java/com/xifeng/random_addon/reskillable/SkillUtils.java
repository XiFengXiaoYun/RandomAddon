package com.xifeng.random_addon.reskillable;

import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import com.xifeng.random_addon.config.ModConfig;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillUtils {
    public static Map<String, Double> skillToValue = new HashMap<>();
    public static Map<String, Integer> skillToOperation = new HashMap<>();
    public static Map<String, String> skillToAttribute = new HashMap<>();
    private static final Map<String, UUID> skillToUUID = new HashMap<>();
    private static final UUID[] uuids = {
            UUID.fromString("cac76d00-08ee-f28a-89c1-f8f8d1629d03"),
            UUID.fromString("e60e0b03-ab5d-4c94-37fb-b2a839da362a"),
            UUID.fromString("960a67b2-7aac-983e-f369-b8ba6efada21"),
            UUID.fromString("8db0787e-6ea5-c1ae-0be3-6e55406e6653"),
            UUID.fromString("8aaaa129-8642-625d-8b88-713f9fdd9c02"),
            UUID.fromString("8c12002a-bbb7-eed4-9092-387cfb695ac9"),
            UUID.fromString("05111f1e-171e-a64d-ee44-578b5d06f1fd"),
            UUID.fromString("4a2efac7-fd23-043b-c748-cef17fc71199"),
            UUID.fromString("5ccb5a83-2818-c24f-b982-f03d5117311e"),
            UUID.fromString("8c02070e-dfe8-96cf-e211-82c7f50d9ba0"),
            UUID.fromString("ef0f3158-65bf-4b80-b5ff-941233d0a6c2"),
            UUID.fromString("2d5f4a31-2981-c642-ae9c-cc18ba7118ea")
    };

    public static void getAttributeFromConfig() {
        int i = 0;
        for(String e : ModConfig.ReskillableCompact.skillToAttribute) {
            double amount = 0.0;
            int operation = 0;
            String[] cache = e.split(":");
            if(checkSkillExists(cache[0])) {
                try {
                    amount = Double.parseDouble(cache[2]);
                    operation = Integer.parseInt(cache[3]);
                } catch (NumberFormatException exception) {
                    System.out.println("The string format in config is wrong!");
                }
                skillToValue.put(cache[0], amount);
                skillToOperation.put(cache[0], operation);
                skillToAttribute.put(cache[0], cache[1]);
                skillToUUID.put(cache[0], uuids[i]);
                i++;
            }
        }
    }

    public static boolean checkSkillExists(String skill) {
        ResourceLocation rl = new ResourceLocation(skill.replace('.', ':'));
        return ReskillableRegistries.SKILLS.containsKey(rl);
    }

    public static boolean checkSkillMatch(String otherSkill) {
        return skillToValue.containsKey(otherSkill);
    }

    public static void updateModifier(EntityPlayer player, String skillName, int level) {
        IAttributeInstance instance = player.getAttributeMap().getAttributeInstanceByName(skillToAttribute.get(skillName));
        if (instance != null) {
            setModifier(skillName, instance, level);
        }
    }

    public static void setModifier(String skillName, IAttributeInstance instance, int level) {
        double value = skillToValue.get(skillName) * level;
        AttributeModifier modifier = new AttributeModifier(skillToUUID.get(skillName), skillName, value, skillToOperation.get(skillName)).setSaved(true);
        if(!instance.hasModifier(modifier)) {
            instance.applyModifier(modifier);
        }
        instance.removeModifier(modifier);
        instance.applyModifier(modifier);
    }

    //玩家登录或重生时，更新玩家的属性修饰符
    public static void syncAttribute(EntityPlayer player) {
        if(player == null) return;
        PlayerData data = PlayerDataHandler.get(player);
        Collection<PlayerSkillInfo> skillsInfo = data.getAllSkillInfo();
        for(PlayerSkillInfo info : skillsInfo) {
            String skillName = info.skill.getKey();
            int skillLevel = info.getLevel();
            if(checkSkillMatch(skillName)) {
                updateModifier(player, skillName, skillLevel);
            }
        }
    }
}
