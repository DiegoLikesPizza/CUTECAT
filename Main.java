import diegoutil.DiegoStringUtils;

public class Main {

    private static DiegoStringUtils dsu = new DiegoStringUtils();

    public static void main(String[] args) {

        dsu.println(dsu.toString(dsu.getrandom(100, 102)));

        if (dsu.coinflip()) {
            dsu.print("true");
        } else {
            dsu.print("false");
        }
    }
}