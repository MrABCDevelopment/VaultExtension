package me.dreamdevs.github.vaultextension;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.events.ChestOpenEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VaultListener implements Listener {

    @EventHandler
    public void openChestEvent(ChestOpenEvent event) {
        if(!VaultExtension.instance.getVaultManager().getChestsValues().containsKey(event.getChestGame().getId()))
            return;
        RandomMoney randomMoney = VaultExtension.instance.getVaultManager().getChestsValues().get(event.getChestGame().getId());
        int money = randomMoney.getRandomMoney();
        VaultExtension.ECONOMY.depositPlayer(event.getPlayer(), money);
        if(money == 0) {
            event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("nothing-found"));
        } else if (money > 0) {
            event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("found-money").replaceAll("%MONEY%", String.valueOf(money)));
        } else {
            event.getPlayer().sendMessage(RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("lost-money").replaceAll("%MONEY%", String.valueOf(money)));
        }
    }

}