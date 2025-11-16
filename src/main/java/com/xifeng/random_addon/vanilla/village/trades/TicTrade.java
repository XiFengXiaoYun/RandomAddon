package com.xifeng.random_addon.vanilla.village.trades;

import com.xifeng.random_addon.RandomAddon;
import com.xifeng.random_addon.config.ModConfig;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@Mod.EventBusSubscriber(modid = "random_addon")
public class TicTrade {
    @SubscribeEvent
    public static void onVillagerProfessionRegistration(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
        if(!RandomAddon.ticEnabled()) return;
        VillagerRegistry.VillagerProfession smith = event.getRegistry().getValue(new ResourceLocation("minecraft", "smith"));
        VillagerRegistry.VillagerProfession farmer = event.getRegistry().getValue(new ResourceLocation("minecraft", "farmer"));
        if (farmer != null) {
            farmer.getCareer(3).addTrade(3, new TicTradeList(3));
            farmer.getCareer(3).addTrade(3, new TicTradeList(4));
        }

        if (smith != null) {
            VillagerRegistry.VillagerCareer armor = smith.getCareer(0);
            VillagerRegistry.VillagerCareer weapon = smith.getCareer(1);
            VillagerRegistry.VillagerCareer tool = smith.getCareer(2);
            armor.addTrade(3, new TicTradeList(2));
            weapon.addTrade(3, new TicTradeList(0));
            tool.addTrade(3, new TicTradeList(1));
        }
    }
    public static final class TicTradeList implements EntityVillager.ITradeList {
        private final int type;
        public TicTradeList(int type) {
            this.type = type;
        }

        public void addMerchantRecipe(@ParametersAreNonnullByDefault IMerchant merchant, @ParametersAreNonnullByDefault MerchantRecipeList trades, Random random) {
            ItemStack sell = new ItemStack(Items.EMERALD, random.nextInt(ModConfig.VillagerTrade.price[1] - ModConfig.VillagerTrade.price[0] +1) +ModConfig.VillagerTrade.price[0]);
            Item sell2 = Item.getByNameOrId(ModConfig.VillagerTrade.extraItem);
            ItemStack buy = null;
            TradeUtil util = new TradeUtil();
            ItemStack secondItem = new ItemStack(sell2, random.nextInt(ModConfig.VillagerTrade.price2[1] - ModConfig.VillagerTrade.price2[0] +1) + ModConfig.VillagerTrade.price2[0]);

            switch (type) {
                case 0:
                    buy = util.randSword(random);
                    break;

                case 1:
                    buy = util.randTool(random);
                    break;

                case 2:
                    buy = util.randArmor(random);
                    break;

                case 3:
                    buy = util.randBow(random);
                    break;

                case 4:
                    buy = util.randArrow(random);
                    break;

                default:
                    break;
            }
            if (buy != null) {
                trades.add(new MerchantRecipe(sell, secondItem, buy, 0, 1));
            }
        }
    }
}
