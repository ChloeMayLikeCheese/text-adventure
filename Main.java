package TextAdventure;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

public class Main {
    static int health;
    static Player player;
    static int stamina;
    static int currentRoomIndex = 1;
    static String currentRoom;

    public static void main(String[] args) throws IOException {
        saveCreate();

        player = new Player(0, 0, 0, 0);
        startSequence();
        health = 100 + player.con * 10;
        stamina = 100 + player.sta * 10;
        System.out.println("DEBUG: CON:" + player.con + " DEX:" + player.dex + " STA:" + player.sta + " STR:" + player.str);
        System.out.println("Health: " + health);
        System.out.println("Stamina: "+stamina);
       roomGenerator();
        saveRead();
        //saveDelete();

    }

    public static void startSequence() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String startInput;
        boolean running = true;

        while (running) {
            System.out.print("Welcome to the Blatant Biohazard Research Facility!\nType 'help' for instructions on how to play.\nClass options: Tank, Rogue\nPlease choose a class (or type 'info' for more information on each class): ");
            startInput = scanner.next().toLowerCase();

            switch (startInput) {
                case "debug", "d":
                    player.setStats(5, 5, 5, 5);
                    running = false;
                    break;

                case "tank":
                    player.setStats(5, 1, 2, 4);
                    running = false;
                    break;

                case "rogue":
                    player.setStats(1, 5, 4, 2);
                    running = false;
                    break;

                case "info":
                    System.out.println("Tank: High constitution and strength, but low dexterity and stamina. Meant to hit hard and withstand attacks\n" +
                            "Rogue: High dexterity and stamina but low constitution and strength. Meant for quick, low damage attacks to chip away at an enemy's health.");
                    break;
                case "t":
                    player.setStats(0, 0, 0, 0);
                    running = false;
                    break;
                case "help":
                    System.out.println("""
                            How to play:
                            Stats determine things that happen in the game.
                            Con: More health per level.
                            Dex: Higher dodge chance.
                            Sta: More actions without resting.
                            Str: Higher damage per level.
                            Commands:
                            Forward or f, moves you forward one room
                            Back or b, moves you back one room
                            Quit or q, quits the program""");
                    break;
                case "r", "read":
                    saveRead();
                    break;
                case "q", "quit":
                    System.out.print("Save? y/n: ");
                    String yesno = scanner.next();
                    if (Objects.equals(yesno, "n")) {
                        System.out.println("Quitting without saving...");
                        saveDelete();
                        System.exit(0);
                    } else {
                        saveWrite();
                        saveRead();
                        System.out.println("Saved");
                        System.exit(0);

                    }

                    break;

                default:
                    System.out.println("Invalid input. Type 'tank' or 'rogue' to select a class.");
            }
        }
    }

    public static void changeHealth(String type, int change) {
        if (type.equalsIgnoreCase("heal")) {
            health += change;
            System.out.println("You healed " + change + " health!\nHealth: " + health);
            saveWrite();
        } else if (type.equalsIgnoreCase("damage")) {
            Scanner scannerIn = new Scanner(System.in);
            System.out.println("Do you want to attempt a dodge? This will take stamina. (y/n)");
            String in = scannerIn.next().toLowerCase();
            if (in == "y"){
                dodge();
                stamina -= 5;
                health -= change;
                System.out.println("You took " + change + " damage!\nHealth: " + health);
                saveWrite();
            }else{
                health -= change;
                System.out.println("You took " + change + " damage!\nHealth: " + health);
                saveWrite();
            }
        }
    }


    public static void dodge() {
        int dodgeChance = (int) Math.ceil(Math.random() * 10);
        if (dodgeChance <= player.dex) {
            System.out.println("You Dodged!");
        }
    }

    public static void roomGenerator() {
        int randomRoom = (int) Math.ceil(Math.random() * 2);
        Room newRoom;

        if (randomRoom == 1) {
            newRoom = new Room("You found a coin", new Item[]{new Item("Coin")});
            System.out.println(newRoom.description + " Items: " + Arrays.toString(newRoom.items));
            currentRoomIndex++;
            currentRoom = "coin";
            stamina -= 5;
            saveWrite();
        } else {
            newRoom = new Room("Trap room! Watch out for the spikes!", new Item[]{});
            System.out.println(newRoom.description);
            changeHealth("damage", 10);
            currentRoomIndex++;
            currentRoom = "trap";
            saveWrite();
        }
    }

    public static void saveDelete() {
        File saveDelete = new File("save.txt");
        if (saveDelete.delete()) {
            System.out.println("DEBUG: Deleted the save file: " + saveDelete.getName());
        } else {
            System.out.println("DEBUG: Failed to delete the save file.");
        }
    }

    public static void saveCreate() {
        try {
            File saveCreator = new File("save.txt");
            if (saveCreator.createNewFile()) {
                System.out.println("DEBUG: Save created: " + saveCreator.getName());
            } else {
                System.out.println("DEBUG: Save already exists.");
            }
        } catch (IOException e) {
            System.out.println("DEBUG: An error occurred.");
            e.printStackTrace();
        }
    }

    public static void saveWrite() {
        try {
            FileWriter saveWriter = new FileWriter("save.txt", true); // 'true' for appending
            saveWriter.append("health=").append(String.valueOf(health))
                    .append(" stamina=").append(String.valueOf(stamina))
                    .append(" currentRoomIndex=").append(String.valueOf(currentRoomIndex))
                    .append(" currentRoom=").append(currentRoom)
                    .append("\n");
            saveWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public static void saveRead() throws IOException {
        List<String> saveData = Files.readAllLines(new File("save.txt").toPath(), Charset.defaultCharset());
        System.out.println("DEBUG: save data: "+saveData);

    }
}


