package me.dreamdevs.vaultextension;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

@Getter @AllArgsConstructor
public class RandomMoney {

    private final double min;
    private final double max;
    private final double chance;

    public double getRandomMoney() {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

}