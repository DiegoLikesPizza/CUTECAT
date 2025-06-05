import java.util.ArrayList;

public class CommandReader {

    private final KeyInputHandler keyInputHandler;

    public CommandReader(KeyInputHandler keyInputHandler) {
        this.keyInputHandler = keyInputHandler;
    }

    public ArrayList<Integer> getCurrentCommands() {
        ArrayList<Integer> keys = keyInputHandler.getKeyStates();
        ArrayList<Integer> command = new ArrayList<>();
        //Motoren
        if (keys.get(0) == 1){
            command.add(1);
            command.add(150);
            command.add(1);
            command.add(150);
            command.add(1);
            command.add(150);
            command.add(1);
            command.add(150);
        }
        else if (keys.get(2) == 1){
            command.add(2);
            command.add(150);
            command.add(2);
            command.add(150);
            command.add(2);
            command.add(150);
            command.add(2);
            command.add(150);
        }
        else {
            command.add(0);
            command.add(0);
            command.add(0);
            command.add(0);
            command.add(0);
            command.add(0);
            command.add(0);
            command.add(0);
        }
        //Lenkservo
        if (keys.get(1) == 1){
            command.add(0);
        }
        else if (keys.get(3) == 1){
            command.add(180);
        }
        else{
            command.add(90);
        }
        //Turmservo
        command.add(keys.get(6));
        //Laufservo
        command.add(keys.get(7));
        //Sensorvert
        command.add(keys.get(4));
        //Sensorhori
        command.add(keys.get(5));
        //Schuss
        command.add(keys.get(8));
        return command;

    }
}
