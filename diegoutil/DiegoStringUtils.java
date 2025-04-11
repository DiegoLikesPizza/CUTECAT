package diegoutil;

import java.util.Random;

public class DiegoStringUtils {

    public void print(String input) {
        System.out.print(input);
    }

    public void println(String input) {
        System.out.println(input);
    }

    public boolean compare(String a, String b) {
        // Strings zu lower case
        a = a.toLowerCase();
        b = b.toLowerCase();
        // Vergleich
        return a.equals(b);
    }

    public boolean comparekey(String a, int ax, String b, int bx) {
        // Character Arrays für Vergleich initialisieren
        char[] aa;
        char[] ba;
        // Strings zu lower case
        a = a.toLowerCase();
        b = b.toLowerCase();
        // Character Arrays zuweisen
        aa = a.toCharArray();
        ba = b.toCharArray();
        // Vergleich
        return aa[ax] == ba[bx];
    }

    public String toString(int x) {
        return String.valueOf(x);
    }
    public String toString(double x) {
        return String.valueOf(x);
    }
    public String toString(boolean x) {
        return String.valueOf(x);
    }
    public String toString(char x) {
        return String.valueOf(x);
    }
    public String toString(float x) {
        return String.valueOf(x);
    }
}