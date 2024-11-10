package TextAdventure;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

public class Main {
    static int health;
    static Player player;
    static int stamina;
    static int currentRoomIndex = -1;
    static ArrayList <Room> rooms;

    public static void main(String[] args) throws IOException {
        saveCreate();
        rooms = new ArrayList<>();
        player = new Player(0, 0, 0, 0);
        startSequence();
        health = 100 + player.con * 10;
        stamina = 100 + player.sta * 10;
        System.out.println("DEBUG: CON:" + player.con + " DEX:" + player.dex + " STA:" + player.sta + " STR:" + player.str);
        System.out.println("Health: " + health);
        System.out.println("Stamina: "+stamina);
       roomGenerator();
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
            if (in.equals("y")){
                stamina -= 5;
                if (!dodge()) {
                    health -= change;
                    System.out.println("You took " + change + " damage!\nHealth: " + health);
                }
            }else{
                health -= change;
                System.out.println("You took " + change + " damage!\nHealth: " + health);

            }
        }
    }


    public static boolean dodge() {
        int dodgeChance = (int) Math.ceil(Math.random() * 10);
        if (dodgeChance <= player.dex) {
            System.out.println("You Dodged!");
            return true;
        }else {
            return false;
        }
    }

    public static void roomGenerator() {
        int randomRoom = (int) Math.ceil(Math.random() * 2);
        Room newRoom;

        if (randomRoom == 1) {
            newRoom = new Room("You found a coin", new Item[]{new Item("coin")},"coin");
            System.out.println(newRoom.description + " Items: " + Arrays.toString(newRoom.items));
            currentRoomIndex++;
            rooms.add(newRoom);
            saveWrite();
        } else {
            newRoom = new Room("Trap room! Watch out for the spikes!", new Item[]{},"trap");
            System.out.println(newRoom.description);
            changeHealth("damage", 10);
            currentRoomIndex++;
            rooms.add(newRoom);
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
            FileWriter saveWriter = new FileWriter("save.txt", false);
            saveWriter.append(String.valueOf(health))
                    .append(",").append(String.valueOf(stamina))
                    .append(",").append(String.valueOf(player.con))
                    .append(",").append(String.valueOf(player.dex))
                    .append(",").append(String.valueOf(player.sta))
                    .append(",").append(String.valueOf(player.str))
                    .append(",").append(String.valueOf(currentRoomIndex))
                    .append("\n");
            for (int i = 0; i < rooms.size(); i++){
                saveWriter.append(rooms.get(i).type).append("\n");
            }
            saveWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public static void saveRead() throws IOException {
        List<String> saveData = Files.readAllLines(new File("save.txt").toPath(), Charset.defaultCharset());
        rooms = new ArrayList<>();

        String[] playerStats = saveData.get(0).split(",");
        health = Integer.parseInt(playerStats[0]);
        stamina = Integer.parseInt(playerStats[1]);
        int con = Integer.parseInt(playerStats[2]);
        int dex = Integer.parseInt(playerStats[3]);
        int sta = Integer.parseInt(playerStats[4]);
        int str = Integer.parseInt(playerStats[5]);
        player = new Player(con,dex,sta,str);
        currentRoomIndex = Integer.parseInt(playerStats[6]);

        for (int i = 1; i < saveData.size(); i++){
            if (saveData.get(i).equals("coin")){
                rooms.add(new Room("It's an empty room",new Item[]{},"empty"));
            }else if (saveData.get(i).equals("trap")){
                rooms.add(new Room("It's an empty room with a broken trap",new Item[]{},"emptyTrap"));
            }
        }
        System.out.println("DEBUG: save data: "+saveData);

    }
}


