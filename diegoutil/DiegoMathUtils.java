package diegoutil;

import java.util.Random;

public class DiegoMathUtils {

    public boolean coinflip() {
        Random rand  = new Random();
        int x = rand.nextInt(2);

        if (x == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getrandom(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}