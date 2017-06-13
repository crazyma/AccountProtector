package com.beibeilab.accountprotector.util;

import java.util.Random;

/**
 * Created by david on 2017/6/13.
 */

public class PasswordGenerator {
    private final String uppercases = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String lowercases = "abcdefghijklmnopqrstuvwxyz";
    private final String numbers = "0123456789";
    private final String others = "!@#$%^&*()_";

    private String mSample;
    private int mLength;

    public PasswordGenerator(int length, boolean[] ruleArray) {
        mLength = length;
        createSample(ruleArray);
    }

    private void createSample(boolean[] ruleArray) {
        StringBuilder stringBuilder = new StringBuilder();
        if (ruleArray[0]) {
            stringBuilder.append(uppercases);
        }

        if (ruleArray[1]) {
            stringBuilder.append(lowercases);
        }

        if (ruleArray[2]) {
            stringBuilder.append(numbers);
        }

        if (ruleArray[3]) {
            stringBuilder.append(others);
        }

        mSample = stringBuilder.toString();
    }

    public String generate() {
        Random random = new Random(System.nanoTime());

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mLength; i++) {
            stringBuilder.append(mSample.charAt(random.nextInt(mSample.length())));
        }

        return stringBuilder.toString();
    }
}
