import diegoutil.DiegoMathUtils;
import diegoutil.DiegoStringUtils;

public class Main {

    private static DiegoStringUtils dsu = new DiegoStringUtils();
    private static DiegoMathUtils dmu = new DiegoMathUtils();

    public static void main(String[] args) {

        dsu.println(dsu.toString(dmu.getrandom(100, 102)));

        if (dmu.coinflip()) {
            dsu.print("true");
        } else {
            dsu.print("false");
        }
    }
}