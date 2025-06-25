package com.CUTECAT.diegoutil;

import java.util.ArrayList;

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

    public static String ToString(Object x) {
        return String.valueOf(x);
    }

    public static String toCsv(ArrayList<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(',');
            }
        }
        return sb.toString();
    }
}