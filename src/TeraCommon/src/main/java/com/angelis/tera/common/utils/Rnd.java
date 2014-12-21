package com.angelis.tera.common.utils;

public class Rnd {
    
    private static final MTRandom random = new MTRandom();
    private static final String alphaNumeric = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static float get() {
        return random.nextFloat();
    }

    public static int get(final int n) {
        return (int) Math.floor(random.nextDouble() * n);
    }

    public static int get(final int min, final int max) {
        return min + (int) Math.floor(random.nextDouble() * (max - min + 1));
    }
    
    public static int get(final int min, final int max, final boolean maxInclusive) {
        if (maxInclusive) {
            return get(min, max);
        } else {
            return (int) Math.floor(random.nextDouble() * max) + min;
        }
    }

    public static long get(final long min, final long max) {
        return min + (long) Math.floor(random.nextDouble() * (max - min + 1));
    }

    public static int nextInt() {
        return random.nextInt();
    }

    public static double nextDouble() {
        return random.nextDouble();
    }

    public static double nextGaussian() {
        return random.nextGaussian();
    }

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }
    
    public static String nextString(final int length) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < length ; i++) {
            sb.append(alphaNumeric.charAt(Rnd.get(0, alphaNumeric.length()-1)));
        }
        return sb.toString();
    }

    public static boolean chance(final int chance) {
        return chance < 1 ? false : chance > 99 ? true : random.nextInt(99) + 1 <= chance;
    }

    public static boolean chance(final double chance) {
        return random.nextDouble() <= chance / 100.;
    }
}