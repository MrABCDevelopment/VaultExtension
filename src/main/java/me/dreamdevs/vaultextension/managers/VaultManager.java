package me.dreamdevs.vaultextension.managers;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.utils.ColourUtil;
import me.dreamdevs.vaultextension.RandomMoney;
import me.dreamdevs.vaultextension.VaultExtension;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

@Getter
public class VaultManager {

    private final Map<String, RandomMoney> chestsValues;
    private final FileConfiguration config;

    public static String MESSAGE_NOTHING_FOUND;
    public static String MESSAGE_MONEY_FOUND;
    public static String MESSAGE_MONEY_LOST;
    public static String MENU_TITLE_MAIN;
    public static boolean BROADCAST_ON_FOUND;
    public static String BROADCAST_MESSAGE;
    public static double BROADCAST_AMOUNT;

    public VaultManager(VaultExtension extension) {
        chestsValues = new HashMap<>();
        config = extension.getConfig();
        load();
    }

    public void load() {
        chestsValues.clear();

        MESSAGE_NOTHING_FOUND = ColourUtil.colorize(config.getString("Messages.Nothing-Found"));
        MESSAGE_MONEY_FOUND = ColourUtil.colorize(config.getString("Messages.Money-Found"));
        MESSAGE_MONEY_LOST = ColourUtil.colorize(config.getString("Messages.Money-Lost"));
        BROADCAST_MESSAGE = ColourUtil.colorize(config.getString("Messages.Broadcast-Found"));

        BROADCAST_AMOUNT = config.getDouble("Broadcast.Min-Money-To-Display");
        BROADCAST_ON_FOUND = config.getBoolean("Broadcast.Use");

        ConfigurationSection section = config.getConfigurationSection("Chests");
        for (String key : section.getKeys(false)) {
            String[] strings = section.getString(key).split(";");
            RandomMoney randomMoney = new RandomMoney(Double.parseDouble(strings[0]), Double.parseDouble(strings[1]), Double.parseDouble(strings[2]));
            chestsValues.putIfAbsent(key, randomMoney);
        }
    }

}