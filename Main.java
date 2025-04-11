import diegoutil.DiegoUtil;

public class Main {

    private static final DiegoUtil du = new DiegoUtil();

    public static void main(String[] args) {

        du.print(du.toString(du.getRandom(10, 100)));

    }
}