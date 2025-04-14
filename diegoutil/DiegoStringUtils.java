package diegoutil;

public class DiegoStringUtils {

    public static void print(String input) {
        System.out.print(input);
    }

    public static void println(String input) {
        System.out.println(input);
    }

    public static boolean compare(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        return a.equalsIgnoreCase(b);
    }

    public static boolean comparekey(String a, int ax, String b, int bx) {
        if (a == null || b == null) {
            return false;
        }
        if (ax >= a.length() || bx >= b.length() || ax < 0 || bx < 0) {
            return false;
        }
        char CharA = Character.toLowerCase(a.charAt(ax));
        char CharB = Character.toLowerCase(b.charAt(bx));

        return CharA == CharB;
    }

    public static String ToString(int x) {
        return String.valueOf(x);
    }
    public static String ToString(double x) {
        return String.valueOf(x);
    }
    public static String ToString(boolean x) {
        return String.valueOf(x);
    }
    public static String ToString(char x) {
        return String.valueOf(x);
    }
    public static String ToString(float x) {
        return String.valueOf(x);
    }
}