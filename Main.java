
package TextAdventure;


import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static int health;
    public static void main(String[] args){
        Stats stats = new Stats();
        System.out.println("Health: "+health);
        StartSequence startSequence = new StartSequence();
        health = 100 + stats.character.con*10;
//        Scanner scanner = new Scanner(System.in);
//        String input;
//        roomGenerator();
//        System.out.println("DEBUG: "+" CON:"+ stats.character.con+" DEX:"+ stats.character.dex+" STA:"+ stats.character.sta+" STR:"+ stats.character.str);
    }
    public static void roomGenerator(){
        int randomRoom = (int) Math.ceil(Math.random() * 2);
        System.out.println("DEBUG Room: "+randomRoom);

        switch (randomRoom){
            case 1:
                Room coin = new Room("You found a coin",new Item[]{new Item("Coin")});
                System.out.println(coin.description + " "+Arrays.toString(coin.items));
                break;
            case 2:
                RoomNoItems trap = new RoomNoItems("Trap room! Watch out for the spikes!");
                System.out.println(trap.description);
                changeHealth("damage",10);
                break;

        }


        }

        public static void changeHealth(String type, int change){
        Stats stats = new Stats();
        if (type.equalsIgnoreCase("heal")){
            health += change;
        } else if (type.equalsIgnoreCase("damage")){
            if (!Dodge()) {
                health -= change;
                System.out.println("You took damage!\n Health: "+ health);
            }
        }

        }
        public static boolean Dodge(){
            Stats stats = new Stats();
            int dodgeChance = (int) Math.ceil(Math.random()*10);
            System.out.println("Dodge chance: "+dodgeChance);
            if (dodgeChance <= stats.character.dex){
                System.out.println("Dodge success");
                return true;
            }
            System.out.println("Dodge fail");
            return false;
        }

    }