import diegoutil.DiegoStringUtils;

public class Main {

    private static DiegoStringUtils dsu = new DiegoStringUtils();

    public static void main(String[] args) {

        String as1 = "sigma";
        String bs1 = "sigma";

        if (dsu.compare(as1, bs1)) {
            dsu.print("true");
        } else {
            dsu.print("false");
        }
    }
}