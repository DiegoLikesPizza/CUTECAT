package diegoutil;

public class DiegoUtil {

    // DiegoMathUtil

    public static boolean coinflip() {
        return DiegoMathUtils.coinflip();
    }

    public static int getRandom(int min, int max) {
        return DiegoMathUtils.getRandom(min, max);
    }

    // DiegoPhysicsUtil

    // DiegoStringUtil

    public static void print(String input) {
        DiegoStringUtils.print(input);
    }

    public static void println(String input) {
        DiegoStringUtils.println(input);
    }

    public static boolean compare (String input1, String input2) {
        return DiegoStringUtils.compare(input1, input2);
    }

    public static boolean comparekey(String input1, String input2, int key) {
        return DiegoStringUtils.comparekey(input1, key, input2, key);
    }

    public static boolean comparekey(String input1, int key1, String input2, int key2) {
        return DiegoStringUtils.comparekey(input1, key1, input2, key2);
    }

    public static String ToString(int x) {
        return DiegoStringUtils.toString(x);
    }
    public static String ToString(float x) {
        return DiegoStringUtils.toString(x);
    }
    public static String ToString(double x) {
        return DiegoStringUtils.toString(x);
    }
    public static String ToString(char x) {
        return DiegoStringUtils.toString(x);
    }
    public static String ToString(boolean x) {
        return DiegoStringUtils.toString(x);
    }
}