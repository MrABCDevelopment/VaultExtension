package me.dreamdevs.github.vaultextension;

import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.extensions.Extension;
import me.dreamdevs.github.randomlootchest.utils.ColourUtil;
import me.dreamdevs.github.randomlootchest.utils.Util;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class VaultExtension extends Extension {

    public static Economy ECONOMY;
    public static VaultExtension instance;
    private VaultManager vaultManager;

    @Override
    public void onExtensionEnable() {
        instance = this;
        saveDefaultConfig();
        vaultManager = new VaultManager(this);
        if(!setupEconomy()) {
            Util.sendPluginMessage("&cVault Extension could not hook with Vault, so there might be a problem...");
            return;
        }
        RandomLootChestMain.getInstance().getMessagesManager().getMessages().put("found-money", ColourUtil.colorize(getConfig().getString("Messages.Money-Found")));
        registerListener(new VaultListener());
        Util.sendPluginMessage("&aLoaded all variables and setup VaultExtension!");
    }

    @Override
    public void onExtensionDisable() {}

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        ECONOMY = rsp.getProvider();
        return ECONOMY != null;
    }

    public VaultManager getVaultManager() {
        return vaultManager;
    }
}