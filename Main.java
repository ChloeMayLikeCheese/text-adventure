
package TextAdventure;

import java.util.Scanner;

public class Main {
    static int health;
    static Player player;

    public static void main(String[] args) {
        player = new Player(0, 0, 0, 0);
        startSequence(player);

        // Calculate health based on the updated player stats
        health = 100 + player.con * 10;
        System.out.println("DEBUG: " + " CON:" + player.con + " DEX:" + player.dex + " STA:" + player.sta + " STR:" + player.str);
        System.out.println("Health: " + health);
    }

    public static void startSequence(Player player) {
        Scanner scanner = new Scanner(System.in);
        String startInput;

        System.out.print("Welcome to the Blatant Biohazard Testing Facility!\nClass options: Tank\nPlease choose a class: ");
        startInput = scanner.next();


        switch (startInput.toLowerCase()) {
            case "debug":
                player.setStats(5, 5, 5, 5);
                break;
            case "tank":
                player.setStats(5, 1, 2, 4);
                break;
            default:
                System.out.println("Invalid class chosen. Defaulting to tank stats.");
                player.setStats(5, 1, 2, 4);
                break;
        }
    }
}

//    public static void roomGenerator(){
//        int randomRoom = (int) Math.ceil(Math.random() * 2);
//        System.out.println("DEBUG Room: "+randomRoom);
//
//        switch (randomRoom){
//            case 1:
//                Room coin = new Room("You found a coin",new Item[]{new Item("Coin")});
//                System.out.println(coin.description + " "+Arrays.toString(coin.items));
//                break;
//            case 2:
//                RoomNoItems trap = new RoomNoItems("Trap room! Watch out for the spikes!");
//                System.out.println(trap.description);
//                changeHealth("damage",10);
//                break;
//
//        }
//
//
//        }
//
//        public static void changeHealth(String type, int change){
//        Stats stats = new Stats();
//        if (type.equalsIgnoreCase("heal")){
//            health += change;
//        } else if (type.equalsIgnoreCase("damage")){
//            if (!Dodge()) {
//                health -= change;
//                System.out.println("You took damage!\n Health: "+ health);
//            }
//        }
//
//        }
//        public static boolean Dodge(){
//            Stats stats = new Stats();
//            int dodgeChance = (int) Math.ceil(Math.random()*10);
//            System.out.println("Dodge chance: "+dodgeChance);
//            if (dodgeChance <= stats.player.dex){
//                System.out.println("Dodge success");
//                return true;
//            }
//            System.out.println("Dodge fail");
//            return false;
//        }
//    }
