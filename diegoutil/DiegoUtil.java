package diegoutil;

public class DiegoUtil {

    // DiegoMathUtil

    public boolean coinflip() {
        DiegoMathUtils d = new DiegoMathUtils();
        return d.coinflip();
    }

    public int getRandom(int min, int max) {
        DiegoMathUtils d = new DiegoMathUtils();
        return d.getrandom(min, max);
    }

    // DiegoPhysicsUtil

    // DiegoStringUtil

    public void print(String input) {
        DiegoStringUtils d = new DiegoStringUtils();
        d.print(input);
    }

    public void println(String input) {
        DiegoStringUtils d = new DiegoStringUtils();
        d.println(input);
    }

    public boolean compare (String input1, String input2) {
        DiegoStringUtils d = new DiegoStringUtils();
        return d.compare(input1, input2);
    }

    public boolean comparekey(String input1, String input2, int key) {
        DiegoStringUtils d = new DiegoStringUtils();
        return d.comparekey(input1, key, input2, key);
    }

    public boolean comparekey(String input1, int key1, String input2, int key2) {
        DiegoStringUtils d = new DiegoStringUtils();
        return d.comparekey(input1, key1, input2, key2);
    }

    public String toString(int x) {
        DiegoStringUtils d = new DiegoStringUtils();
        return d.toString(x);
    }
    public String toString(float x) {
        DiegoStringUtils d = new DiegoStringUtils();
        return d.toString(x);
    }
    public String toString(double x) {
        DiegoStringUtils d = new DiegoStringUtils();
        return d.toString(x);
    }
    public String toString(char x) {
        DiegoStringUtils d = new DiegoStringUtils();
        return d.toString(x);
    }
    public String toString(boolean x) {
        DiegoStringUtils d = new DiegoStringUtils();
        return d.toString(x);
    }

}