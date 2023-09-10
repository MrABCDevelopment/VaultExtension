package me.dreamdevs.vaultextension;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.events.ChestOpenEvent;
import me.dreamdevs.randomlootchest.utils.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class VaultListener implements Listener {

    @EventHandler
    public void openChestEvent(ChestOpenEvent event) {
        if(!VaultExtension.instance.getVaultManager().getChestsValues().containsKey(event.getChestGame().getId()))
            return;
        RandomMoney randomMoney = VaultExtension.instance.getVaultManager().getChestsValues().get(event.getChestGame().getId());
        if(Util.chance(randomMoney.getChance())) {
            double money = randomMoney.getRandomMoney();
            VaultExtension.ECONOMY.depositPlayer(event.getPlayer(), round(money));
            if (money == 0) {
                event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("nothing-found"));
            } else if (money > 0) {
                event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("found-money").replaceAll("%MONEY%", String.valueOf(money)));
            } else {
                event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("lost-money").replaceAll("%MONEY%", String.valueOf(money)));
            }
        }
    }

    private double round(double money) {
        BigDecimal bd = BigDecimal.valueOf(money);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}