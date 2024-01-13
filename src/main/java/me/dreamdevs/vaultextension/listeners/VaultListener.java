package me.dreamdevs.vaultextension.listeners;

import me.dreamdevs.randomlootchest.api.events.ChestOpenEvent;
import me.dreamdevs.randomlootchest.api.utils.Util;
import me.dreamdevs.vaultextension.RandomMoney;
import me.dreamdevs.vaultextension.VaultExtension;
import me.dreamdevs.vaultextension.managers.VaultManager;
import org.bukkit.Bukkit;
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
                event.getPlayer().sendMessage(VaultManager.MESSAGE_NOTHING_FOUND);
            } else if (money > 0) {
                event.getPlayer().sendMessage(VaultManager.MESSAGE_MONEY_FOUND.replace("%MONEY%", String.valueOf(round(money))));
            } else {
                event.getPlayer().sendMessage(VaultManager.MESSAGE_MONEY_LOST.replace("%MONEY%", String.valueOf(round(money))));
            }

            if (VaultManager.BROADCAST_ON_FOUND && money >= VaultManager.BROADCAST_AMOUNT) {
                Bukkit.broadcastMessage(VaultManager.BROADCAST_MESSAGE.replace("%PLAYER%", event.getPlayer().getName()).replace("%MONEY%", String.valueOf(round(money))));
            }
        }
    }

    private double round(double money) {
        BigDecimal bd = BigDecimal.valueOf(money);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}