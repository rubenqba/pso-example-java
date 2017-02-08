package org.gandhim.pso.util;

import lombok.Getter;

import java.util.Random;

public  class RandomGenerator {
    private static RandomGenerator instance;

    @Getter
    private Random random;

    private RandomGenerator() {
        random = new Random(4);
    }

    public static RandomGenerator getInstance() {
        if (instance == null)
            instance = new RandomGenerator();
        return instance;
    }
}