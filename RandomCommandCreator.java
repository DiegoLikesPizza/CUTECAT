import java.util.ArrayList;
import java.util.Random;

public class RandomCommandCreator {

    public ArrayList<Integer> createrandomCommands(int direction){
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
        if (direction == 1){
            ArrayList<Integer> commands = new ArrayList<>();
            commands.add(1);
            commands.add(100);
            commands.add(1);
            commands.add(100);
            commands.add(1);
            commands.add(100);
            commands.add(1);
            commands.add(100);
            return commands;
        }
        else if (direction == 2){
            ArrayList<Integer> commands = new ArrayList<>();
            commands.add(2);
            commands.add(100);
            commands.add(2);
            commands.add(100);
            commands.add(2);
            commands.add(100);
            commands.add(2);
            commands.add(100);
            return commands;
        }
        else if (direction == 4){
            ArrayList<Integer> commands = new ArrayList<>();
            commands.add(1);
            commands.add(150);
            commands.add(1);
            commands.add(100);
            commands.add(1);
            commands.add(150);
            commands.add(1);
            commands.add(100);
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
            return commands;
        }
    }
    /*public ArrayList<Integer> createCommands1(){
        ArrayList<Integer> commands = new ArrayList<>();
        commands.add(1);
        commands.add(100);
        commands.add(1);
        commands.add(100);
        commands.add(1);
        commands.add(100);
        commands.add(1);
        commands.add(100);
        return commands;
    }
    public ArrayList<Integer> createCommands2(){
        ArrayList<Integer> commands = new ArrayList<>();
        commands.add(2);
        commands.add(100);
        commands.add(2);
        commands.add(100);
        commands.add(2);
        commands.add(100);
        commands.add(2);
        commands.add(100);
        return commands;
    }
    public ArrayList<Integer> createCommands3(){
        ArrayList<Integer> commands = new ArrayList<>();
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
     */
}
