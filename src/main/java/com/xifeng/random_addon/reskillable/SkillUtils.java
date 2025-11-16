package com.xifeng.random_addon.reskillable;

import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import com.xifeng.random_addon.RandomAddon;
import com.xifeng.random_addon.config.ModConfig;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class SkillUtils {
    //TODO: 将这四个映射删除，用一个新的封装的类代替其功能
    private static final Map<String, Double> skillToValue = new HashMap<>();
    private static final Map<String, Integer> skillToOperation = new HashMap<>();
    private static final Map<String, String> skillToAttribute = new HashMap<>();
    private static final Map<String, UUID> skillToUUID = new HashMap<>();
    //看起来有点愚蠢（
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
            UUID.fromString("2d5f4a31-2981-c642-ae9c-cc18ba7118ea"),
            UUID.fromString("188b3967-25a8-33f6-0e16-33166f9cec97"),
            UUID.fromString("f0a80fa4-c657-c89f-1f7d-c2df805f40b3"),
            UUID.fromString("8da51251-a08b-2715-5b2e-801f42a48747"),
            UUID.fromString("1205dd85-8f64-78fd-ba68-0147b1633312"),
            UUID.fromString("3026dbfd-7f9a-0fe7-d520-ec9603f6d4fb"),
            UUID.fromString("9b2eb28e-e9f4-9f44-4516-3af6efb2d051"),
            UUID.fromString("8d1dd4ff-1583-38ad-7110-128db1cd2f7c"),
            UUID.fromString("dec2a6bc-0cd8-5aeb-c23f-a65ec2fb30cd"),
            UUID.fromString("a2dac672-5afb-c463-d513-70961954fb16"),
            UUID.fromString("c69035d2-48cb-e49c-4c63-b9f50c26466a"),
            UUID.fromString("4818c2ce-ab68-abfc-50ed-092c85a24580"),
            UUID.fromString("8f609b94-4e92-a9ae-655a-9daffbe8c1c0")
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
                    RandomAddon.LOGGER.warn("The format of the skill config is wrong! It should be skill:attribute:amount:operation!", exception);
                }
                skillToValue.put(cache[0], amount);
                skillToOperation.put(cache[0], operation);
                skillToAttribute.put(cache[0], cache[1]);
                skillToUUID.put(cache[0], uuids[i]);
                i++;
                if(i >= 24) {
                    RandomAddon.LOGGER.warn("The valid skill entry amount in the config is more than 24, the rest entry will be ignored, you're bad!");
                }
            }
        }
    }

    public static boolean checkSkillExists(String skill) {
        ResourceLocation rl = new ResourceLocation(skill.replace('.', ':'));
        return ReskillableRegistries.SKILLS.containsKey(rl);
    }

    public static boolean checkTraitExists(String trait) {
        ResourceLocation rl = new ResourceLocation(trait.replace('.', ':'));
        return ReskillableRegistries.UNLOCKABLES.containsKey(rl);
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

    public static void putModifierForTrait(EntityPlayer player, TraitAttributeEntry entry) {
        IAttributeInstance instance = player.getAttributeMap().getAttributeInstanceByName(entry.getAttributeName());
        if(instance != null) {
            AttributeModifier modifier = new AttributeModifier(entry.getUuid(), entry.getTraitName(), entry.getAmount(), entry.getOperation());
            modifier.setSaved(true);
            if(!instance.hasModifier(modifier)) {
                instance.applyModifier(modifier);
            } else {
                RandomAddon.LOGGER.warn("WARN! The attribute instance already has the modifier with name{}!", modifier.getName());
            }
        }
    }

    public static TraitAttributeEntry getFromList(List<TraitAttributeEntry> list, String traitName) {
        for(TraitAttributeEntry entry : list) {
            if(entry.getTraitName().equals(traitName)) {
                return entry;
            }
        }
        return null;
    }

    private static void setModifier(String skillName, IAttributeInstance instance, int level) {
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
            for(Unlockable unlockable : info.skill.getUnlockables()) {
                TraitAttributeEntry entry = getFromList(RandomAddon.listTraitAttributeEntry, unlockable.getKey());
                if (entry != null) {
                    putModifierForTrait(player, entry);
                }
            }
        }
    }
}
