import static diegoutil.DiegoMathUtils.*;
import static diegoutil.DiegoPhysicsUtils.*;
import static diegoutil.DiegoStringUtils.*;

public class Main {

    public static void main(String[] args) {
        KeyInputHandler keys = new KeyInputHandler();
        CommandReader commandReader = new CommandReader(keys);

        Thread readerThread = new Thread(() -> {
            while (true) {
                System.out.println(commandReader.getCurrentCommands());
                try {
                    Thread.sleep(1); // pausiert 1 Sekunde
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        readerThread.start();
    }
}
