package me.dreamdevs.vaultextension;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.extensions.Extension;
import me.dreamdevs.randomlootchest.api.utils.Util;
import me.dreamdevs.vaultextension.listeners.VaultListener;
import me.dreamdevs.vaultextension.managers.VaultManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class VaultExtension extends Extension {

    public static Economy ECONOMY;
    public static VaultExtension instance;
    private @Getter VaultManager vaultManager;

    @Override
    public void onExtensionEnable() {
        instance = this;
        if(!setupEconomy()) {
            Util.sendPluginMessage("&cVaultExtension could not hook with Vault, so there might be a problem...");
            setState(State.DISABLED);
            this.onExtensionDisable();
            return;
        }
        saveDefaultConfig();
        setup();
    }

    @Override
    public void onExtensionDisable() {
        Util.sendPluginMessage("&cDisabling VaultExtension...");
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();

        setup();
    }

    private void setup() {
        vaultManager = new VaultManager(this);
        registerListener(new VaultListener());
        Util.sendPluginMessage("&aLoaded all variables and setup VaultExtension!");
    }

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

}