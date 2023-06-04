package me.dreamdevs.github.vaultextension;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

@Getter @AllArgsConstructor
public class RandomMoney {

    private final int min;
    private final int max;
    private final double chance;

    public int getRandomMoney() {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

}