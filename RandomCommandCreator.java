import java.util.ArrayList;
import java.util.Random;

public class RandomCommandCreator {

    public ArrayList<Integer> createrandomCommands(int direction) {
        /*ArrayList<Integer> commands = new ArrayList<>();
        Random rand = new Random();
        commands.add(rand.nextInt(1, 3));
        commands.add(rand.nextInt(1, 255));
        commands.add(rand.nextInt(1, 3));
        commands.add(rand.nextInt(1, 255));
        commands.add(rand.nextInt(1, 3));
        commands.add(rand.nextInt(1, 255));
        commands.add(rand.nextInt(1, 3));
        commands.add(rand.nextInt(1, 255));
        return commands;*/
        if (direction == 1) {
            ArrayList<Integer> commands = new ArrayList<>();
            commands.add(1);   //Richtung Motor1
            commands.add(100); //Geschwindigkeit Motor1
            commands.add(1);   //Richtung Motor2
            commands.add(100); //Geschwindigkeit Motor2
            commands.add(1);   //Richtung Motor3
            commands.add(100); //Geschwindigkeit Motor3
            commands.add(1);   //Richtung Motor4
            commands.add(100); //Geschwindigkeit Motor4
            commands.add(0);   //Stellung Lenkservo
            commands.add(90);  //Stellung Tumservo
            commands.add(180); //Stellung Laufservo
            commands.add(0);   //Stellung Sensorvert
            commands.add(0);   //Stellung Sensorhori
            commands.add(0);   //Stellung schuss

            return commands;
        } else if (direction == 2) {
            ArrayList<Integer> commands = new ArrayList<>();
            commands.add(2);
            commands.add(100);
            commands.add(2);
            commands.add(100);
            commands.add(2);
            commands.add(100);
            commands.add(2);
            commands.add(100);
            commands.add(90);
            commands.add(180);
            commands.add(0);

            return commands;
        } else if (direction == 4) {
            ArrayList<Integer> commands = new ArrayList<>();
            commands.add(1);
            commands.add(150);
            commands.add(1);
            commands.add(100);
            commands.add(1);
            commands.add(150);
            commands.add(1);
            commands.add(100);
            commands.add(180);
            commands.add(0);
            commands.add(90);
            return commands;
        } else {
            ArrayList<Integer> commands = new ArrayList<>();
            commands.add(0);
            commands.add(0);
            commands.add(0);
            commands.add(0);
            commands.add(0);
            commands.add(0);
            commands.add(0);
            commands.add(0);
            commands.add(0);
            commands.add(0);
            commands.add(0);
            return commands;
        }
    }
}
