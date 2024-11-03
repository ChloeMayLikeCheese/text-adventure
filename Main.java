
package TextAdventure;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int health;
    static Player player;
    static ArrayList<Room> roomList = new ArrayList<>();
    static int currentRoomIndex = -1;

    public static void main(String[] args) {
        player = new Player(0, 0, 0, 0);
        startSequence(player);

        health = 100 + player.con * 10;
        System.out.println("DEBUG: " + " CON:" + player.con + " DEX:" + player.dex + " STA:" + player.sta + " STR:" + player.str);
        System.out.println("Health: " + health);

        gameLoop();
    }

    public static void startSequence(Player player) {
        Scanner scanner = new Scanner(System.in);
        String startInput;
        boolean running = true;

        while (running) {
            System.out.println("Type 'help' for instructions on how to play");
            System.out.print("Welcome to the Blatant Biohazard Research Facility!\nClass options: Tank, Rouge\nPlease choose a class (or type 'info' for more information on each class): ");
            startInput = scanner.next().toLowerCase();

            switch (startInput) {
                case "debug","d":
                    player.setStats(5, 5, 5, 5);
                    running = false;
                    break;
                case "tank":
                    player.setStats(5, 1, 2, 4);
                    running = false;
                    break;
                case "rouge":
                    player.setStats(1, 5, 4, 2);
                    running = false;
                    break;
                case "test","t":
                    player.setStats(0,0,0,0);
                    running = false;
                    break;
                case "info":
                    System.out.println("Tank: High constitution and strength, but low dexterity and stamina. meant to hit hard and withstand attacks\n" +
                            "Rouge: High dexterity and stamina but low constitution and strength. meant for quick, low damage attacks to chip away at an enemy's health");
                    break;
                case "help":
                    System.out.println("""
                            How to play:
                            Stats: Stats determine things that happen in the game, like your constitution, which determines how much health you have
                            Here is what they all mean:
                            Con: For every level of Con you have, you get ten more health
                            Dex: For every level of Dex you have, you have a higher chance of dodging certain attacks
                            Sta: For every level of Sta you have, you can attack more and move more without resting
                            Str: For every level of Str you have, you deal a bit more damage""");
                    break;

            }
        }

    }

    public static void gameLoop() {
        boolean playing = true;
        Scanner scanner = new Scanner(System.in);
        while (playing) {
            System.out.print("Enter command (forward,back, quit): ");
            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "move forward","forward","f":
                    moveForward();
                    break;
                case "move back","back","b":
                    moveBackward();
                    break;
                case "quit","q":
                    playing = false;
                    System.out.println("Thanks for playing!");
                    break;
                default:
                    System.out.println("Unknown command. Please type 'forward', 'back', or 'quit'.");
                    break;
            }
        }
    }


    public static void roomGenerator() {
        int randomRoom = (int) Math.ceil(Math.random() * 2);
        Room newRoom;

        if (randomRoom == 1) {

            newRoom = new Room(  "You found a coin", new Item[]{new Item("Coin")}, false);
        } else {
            newRoom = new Room(  "Trap room! Watch out for the spikes!", new Item[]{}, true);
           changeHealth("damage", 10);
        }


            roomList.add(newRoom);
            currentRoomIndex++;


            System.out.println(newRoom.description);

    }



    public static void changeHealth(String type, int change) {
        if (type.equalsIgnoreCase("heal")) {
            health += change;
        } else if (type.equalsIgnoreCase("damage")) {
            if (!Dodge()) {
                health -= change;
                System.out.println("You took " + change + " damage!\nHealth: " + health);
            }
        }
    }

    public static boolean Dodge() {
        int dodgeChance = (int) Math.ceil(Math.random() * 10);
        if (dodgeChance <= player.dex) {
            System.out.println("You Dodged!");
            return true;
        }
        return false;
    }

    public static void moveForward() {
        if (currentRoomIndex < roomList.size() - 1) {
            currentRoomIndex++;
            System.out.println(roomList.get(currentRoomIndex).description);
        } else {
            roomGenerator();
            System.out.println(roomList.get(currentRoomIndex).description);
        }
    }


    public static void moveBackward() {
        if (currentRoomIndex > 0) {
            currentRoomIndex--;
            Room room = roomList.get(currentRoomIndex);


            if (room.isTrapActive) {
                System.out.println("Trap room! Watch out for the spikes!");
                changeHealth("damage", 10);
                room.isTrapActive = false;
            } else {
                System.out.println("Returning to previous room: " + room.description + " (Trap is inactive)");
            }
        } else {
            System.out.println("You are at the start and cannot go back further.");
        }
    }
}

