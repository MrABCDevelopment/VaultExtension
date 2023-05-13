package me.dreamdevs.github.vaultextension;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

@Getter
public class VaultManager
{

    private final Map<String, RandomMoney> chestsValues;

    public VaultManager(VaultExtension extension) {
        chestsValues = new HashMap<>();
        FileConfiguration config = extension.getConfig();
        ConfigurationSection section = config.getConfigurationSection("Chests");
        for(String key : section.getKeys(false)) {
            String[] strings = section.getString(key).split(";");
            RandomMoney randomMoney = new RandomMoney(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
            chestsValues.putIfAbsent(key, randomMoney);
        }
    }

}