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
            commands.add(255); //Geschwindigkeit Motor1
            commands.add(1);   //Richtung Motor2
            commands.add(255); //Geschwindigkeit Motor2
            commands.add(1);   //Richtung Motor3
            commands.add(255); //Geschwindigkeit Motor3
            commands.add(1);   //Richtung Motor4
            commands.add(255); //Geschwindigkeit Motor4
            commands.add(0);   //Stellung Lenkservo
            commands.add(0);  //Stellung Tumservo
            commands.add(20); //Stellung Laufservo
            commands.add(0);   //Stellung Sensor hori
            commands.add(0);   //Stellung Sensor vert
            commands.add(0);   //Abschuss
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
            commands.add(40);
            commands.add(0);
            commands.add(0);
            commands.add(0);
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
            commands.add(0);
            commands.add(0);
            commands.add(0);
            return commands;
        }
        else if (direction == 7) {
            ArrayList<Integer> commands = new ArrayList<>();
            commands.add(2);   //Richtung Motor1
            commands.add(150); //Geschwindigkeit Motor1
            commands.add(2);   //Richtung Motor2
            commands.add(150); //Geschwindigkeit Motor2
            commands.add(2);   //Richtung Motor3
            commands.add(150); //Geschwindigkeit Motor3
            commands.add(2);   //Richtung Motor4
            commands.add(150); //Geschwindigkeit Motor4
            commands.add(0);   //Stellung Lenkservo
            commands.add(120);  //Stellung Tumservo
            commands.add(55); //Stellung Laufservo
            commands.add(180);   //Stellung Sensor hori
            commands.add(140);   //Stellung Sensor vert
            commands.add(0);   //Abschuss
            return commands;
        }
        else if (direction == 6) {
            ArrayList<Integer> commands = new ArrayList<>();
            commands.add(2);   //Richtung Motor1
            commands.add(150); //Geschwindigkeit Motor1
            commands.add(2);   //Richtung Motor2
            commands.add(150); //Geschwindigkeit Motor2
            commands.add(2);   //Richtung Motor3
            commands.add(150); //Geschwindigkeit Motor3
            commands.add(2);   //Richtung Motor4
            commands.add(150); //Geschwindigkeit Motor4
            commands.add(90);   //Stellung Lenkservo
            commands.add(90);  //Stellung Tumservo
            commands.add(90); //Stellung Laufservo
            commands.add(90);   //Stellung Sensor hori
            commands.add(90);   //Stellung Sensor vert
            commands.add(1);   //Abschuss
            return commands;
        }
        else if (direction == 5) {
            ArrayList<Integer> commands = new ArrayList<>();
            commands.add(2);   //Richtung Motor1
            commands.add(150); //Geschwindigkeit Motor1
            commands.add(2);   //Richtung Motor2
            commands.add(150); //Geschwindigkeit Motor2
            commands.add(2);   //Richtung Motor3
            commands.add(150); //Geschwindigkeit Motor3
            commands.add(2);   //Richtung Motor4
            commands.add(150); //Geschwindigkeit Motor4
            commands.add(180);   //Stellung Lenkservo
            commands.add(150);  //Stellung Tumservo
            commands.add(20); //Stellung Laufservo
            commands.add(0);   //Stellung Sensor hori
            commands.add(115);   //Stellung Sensor vert
            commands.add(0);   //Abschuss
            return commands;
        }
        else if (direction == 0) {
            ArrayList<Integer> commands = new ArrayList<>();
            commands.add(1);   //Richtung Motor1
            commands.add(150); //Geschwindigkeit Motor1
            commands.add(1);   //Richtung Motor2
            commands.add(150); //Geschwindigkeit Motor2
            commands.add(1);   //Richtung Motor3
            commands.add(150); //Geschwindigkeit Motor3
            commands.add(1);   //Richtung Motor4
            commands.add(150); //Geschwindigkeit Motor4
            commands.add(0);   //Stellung Lenkservo
            commands.add(0);  //Stellung Tumservo
            commands.add(0); //Stellung Laufservo
            commands.add(0);   //Stellung Sensor hori
            commands.add(0);   //Stellung Sensor vert
            commands.add(0);   //Abschuss
            return commands;
        }
        else if (direction == 9) {
            ArrayList<Integer> commands = new ArrayList<>();
            commands.add(1);   //Richtung Motor1
            commands.add(150); //Geschwindigkeit Motor1
            commands.add(1);   //Richtung Motor2
            commands.add(150); //Geschwindigkeit Motor2
            commands.add(1);   //Richtung Motor3
            commands.add(150); //Geschwindigkeit Motor3
            commands.add(1);   //Richtung Motor4
            commands.add(150); //Geschwindigkeit Motor4
            commands.add(90);   //Stellung Lenkservo
            commands.add(90);  //Stellung Tumservo
            commands.add(0); //Stellung Laufservo
            commands.add(0);   //Stellung Sensor hori
            commands.add(0);   //Stellung Sensor vert
            commands.add(0);   //Abschuss
            return commands;
        }
        else if (direction == 8) {
            ArrayList<Integer> commands = new ArrayList<>();
            commands.add(1);   //Richtung Motor1
            commands.add(150); //Geschwindigkeit Motor1
            commands.add(1);   //Richtung Motor2
            commands.add(150); //Geschwindigkeit Motor2
            commands.add(1);   //Richtung Motor3
            commands.add(150); //Geschwindigkeit Motor3
            commands.add(1);   //Richtung Motor4
            commands.add(150); //Geschwindigkeit Motor4
            commands.add(180);   //Stellung Lenkservo
            commands.add(90);  //Stellung Tumservo
            commands.add(0); //Stellung Laufservo
            commands.add(0);   //Stellung Sensor hori
            commands.add(65);   //Stellung Sensor vert
            commands.add(0);   //Abschuss
            return commands;
        }
        else {
            ArrayList<Integer> commands = new ArrayList<>();
            commands.add(0);
            commands.add(0);
            commands.add(0);
            commands.add(0);
            commands.add(0);
            commands.add(0);
            commands.add(0);
            commands.add(0);
            commands.add(90);
            commands.add(90);
            commands.add(90);
            commands.add(90);
            commands.add(90);
            commands.add(0);
            return commands;
        }

    }
}
