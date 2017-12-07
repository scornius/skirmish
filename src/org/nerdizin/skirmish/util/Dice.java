package org.nerdizin.skirmish.util;

import java.util.Random;

public class Dice {

    private final static Random random = new Random();

    public static int roll(final int sides) {
        return random.nextInt(sides) + 1;
    }

}
