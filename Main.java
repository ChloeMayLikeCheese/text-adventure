package TextAdventure;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

public class Main {
    static int health;
    static Player player;
    static Enemy enemy;
    static int stamina;
    static int currentRoomIndex = 0;
    static ArrayList <Room> rooms;
    static int coins;
    public static void main(String[] args) throws IOException {
        loadSaveIfExists();
        saveCreate();
        rooms = new ArrayList<>();
        player = new Player(0, 0, 0, 0);
        startSequence();
        health = 100 + player.con * 10;
        stamina = 100 + player.sta * 10;
        displayStats();
        System.out.println("Type \"help\" for help");
        gameLoop();



       saveWrite();
       saveRead();

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
        System.out.println("DEBUG:"+dodgeChance);
        if (dodgeChance <= player.dex) {
            System.out.println("You dodged!");
            return true;
        }else {
            System.out.println("Your dodge failed!");
            return false;
        }
    }

    public static void roomGenerator() {
        int randomRoom = (int) Math.ceil(Math.random() * 5);
        Room newRoom = null;

        switch (randomRoom) {
            case 1:
                StackableItem coin = new StackableItem("coin", 1);
                newRoom = new Room("You found a coin", new Item[]{new Item("coin")}, "coin");
                System.out.println(newRoom.description + " Items: " + Arrays.toString(newRoom.items) + " x" + coin.quantity);
                newRoom.description = "It's an empty room";
                player.editInventory(coin);
                break;
            case 2:
                StackableItem scrapMetal = new StackableItem("scrap metal", 2);
                newRoom = new Room("Trap room! Watch out for the spikes!", new Item[]{new Item("scrap metal")}, "trap");
                System.out.println(newRoom.description + Arrays.toString(newRoom.items) + " x" + scrapMetal.quantity);
                newRoom.description = "It's a room with a broken trap";
                changeHealth("damage", 10);
                player.editInventory(scrapMetal);
                break;
            case 3:
                Item sword = new Item("sword");
                newRoom = new Room("It's a room with a sword", new Item[]{new Item("sword")}, "sword");
                System.out.println(newRoom.description + " Items: " + Arrays.toString(newRoom.items) + " x1");
                newRoom.description = "It's an empty room";
                player.editInventory(sword);
                break;
            case 4:
                StackableItem healthPotion = new StackableItem("health potion", 1);
                newRoom = new Room("It's a room with a health potion", new Item[]{new Item("health potion")}, "health potion");
                System.out.println(newRoom.description + " Items: " + Arrays.toString(newRoom.items) + " x" + healthPotion.quantity);
                newRoom.description = "It's an empty room";
                player.editInventory(healthPotion);
                break;
            case 5:
                newRoom = new Room("Watch out! Its a zombie!", new Item[]{}, "zombie");
                System.out.println(newRoom.description);
                newRoom.description = "It's a dead zombie";
                enemy = new Enemy(100, Enemy.Attacks.SLASH, Enemy.Attacks.PUNCH, "zombie");
                enemy.enemyAction();
                break;
            default:
                System.out.println("DEBUG: Error: Room doesnt exist");
        }
        rooms.add(newRoom);
        currentRoomIndex++;
        saveWrite();
    }

    public static void roomGeneratorDebug(int roomSpecific) {
        Room newRoom;

        switch (roomSpecific){
            case 1:
                StackableItem coin = new StackableItem("Coin", 1);
                newRoom = new Room("You found a coin", new Item[]{new Item("coin")},"coin");
                System.out.println(newRoom.description + " Items: " + Arrays.toString(newRoom.items));
                player.editInventory(coin);
                currentRoomIndex++;
                rooms.add(newRoom);
                saveWrite();
                break;
            case 2:
                StackableItem scrapMetal = new StackableItem("Scrap Metal", 2);
                newRoom = new Room("Trap room! Watch out for the spikes!", new Item[]{},"trap");
                System.out.println(newRoom.description);
                changeHealth("damage", 10);
                player.editInventory(scrapMetal);
                currentRoomIndex++;
                rooms.add(newRoom);
                saveWrite();
                break;
            case 3:
                newRoom = new Room("It's a room with a sword", new Item[]{new Item("sword")}, "sword");
                System.out.println(newRoom.description);
                player.editInventory(new Item("Sword"));
                currentRoomIndex++;
                rooms.add(newRoom);
                saveWrite();
                break;
            case 4:
                StackableItem healthPotion = new StackableItem("Health Potion", 1);
                newRoom = new Room("It's a room with a health potion",new Item[]{new Item("Health Potion")},"Health potion");
                System.out.println(newRoom.description);
                player.editInventory(healthPotion);
                currentRoomIndex++;
                rooms.add(newRoom);
                saveWrite();
                break;
            case 5:
                newRoom = new Room("Watch out! Its a zombie!", new Item[]{}, "zombie");
                System.out.println(newRoom.description);
                enemy = new Enemy(100, Enemy.Attacks.SLASH, Enemy.Attacks.PUNCH,"zombie");
                enemy.enemyAction();
                currentRoomIndex++;
                rooms.add(newRoom);
                saveWrite();
                break;
            default:
                System.out.println("DEBUG: Error: Room doesnt exist");
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
            for (Item item : player.inventory) {
                if (item instanceof StackableItem) {
                    StackableItem stackable = (StackableItem) item;
                    saveWriter.append("STACKABLE,").append(stackable.name).append(",").append(String.valueOf(stackable.quantity)).append("\n");
                } else {
                    saveWriter.append("ITEM,").append(item.name).append("\n");
                }
            }
            saveWriter.close();
        } catch (IOException e) {
            System.out.println("DEBUG: An error occurred.");
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
        player = new Player(con, dex, sta, str);
        currentRoomIndex = Integer.parseInt(playerStats[6]);


        for (int i = 1; i <= currentRoomIndex; i++) {
            String roomType = saveData.get(i);
            Room newRoom = null;

            switch (roomType) {
                case "coin":
                    newRoom = new Room("It's an empty room with a coin.", new Item[]{new Item("coin")}, "coin");
                    break;
                case "trap":
                    newRoom = new Room("It's an empty room with a broken trap.", new Item[]{}, "trap");
                    break;
                case "sword":
                    newRoom = new Room("It's a room with a sword.", new Item[]{new Item("sword")}, "sword");
                    break;
                case "health potion":
                    newRoom = new Room("It's a room with a health potion.", new Item[]{new Item("health potion")}, "health potion");
                    break;
                case "zombie":
                    newRoom = new Room("It's a dead zombie.", new Item[]{}, "zombie");
                    break;
                default:
                    newRoom = new Room("Unknown room type.", new Item[]{}, "unknown");
                    break;
            }
            rooms.add(newRoom);
        }


        if (currentRoomIndex > 0) {
            System.out.println("Current room: " + rooms.get(currentRoomIndex - 1).description);
        }
        if (currentRoomIndex > 1) {
            System.out.println("Previous room: " + rooms.get(currentRoomIndex - 2).description);
        }

        boolean isInventorySection = false;
        for (int i = 1; i < saveData.size(); i++) {
            String line = saveData.get(i);
            if (line.equals("INVENTORY:")) {
                isInventorySection = true;
                continue;
            }

            if (isInventorySection) {
                if (line.startsWith("STACKABLE")) {
                    String[] parts = line.split(",");
                    String name = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    StackableItem stackableItem = new StackableItem(name, quantity);
                    player.editInventory(stackableItem);
                } else if (line.startsWith("ITEM")) {
                    String[] parts = line.split(",");
                    String name = parts[1];
                    Item item = new Item(name);
                    player.editInventory(item);
                }
            }
        }

        System.out.println("DEBUG: save data: " + saveData);
    }


    public static void startSequence() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String startInput;
        boolean running = true;

        while (running) {
            System.out.print("Welcome to the Blatant Biohazard Research Facility!\nType \"help\" for instructions on how to play.\nClass options: Tank, Rogue\nPlease choose a class (or type 'info' for more information on each class): ");
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
                    System.out.println("Tank: High constitution and strength, but low dexterity and stamina. Meant to hit hard and withstand attack\n" +
                            "Rogue: High dexterity and stamina but low constitution and strength. Meant for quick, low damage attack to chip away at an enemy's health.");
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
                            help or h: displays help information
                            move forward, forward or f: moves you forward one room
                            move back, back or b: moves you back one room
                            display stats: displays health, stamina and all of your other stats""\");""");
                    break;
                case "r", "read":
                    Main.saveRead();
                    break;
                case "q", "quit":
                    System.out.print("Save? y/n: ");
                    String yesno = scanner.next();
                    if (Objects.equals(yesno, "n")) {
                        System.out.println("Quitting without saving...");
                        Main.saveDelete();
                        System.exit(0);
                    } else {
                        Main.saveWrite();
                        Main.saveRead();
                        System.out.println("Saved");
                        System.exit(0);

                    }

                    break;


                default:
                    System.out.println("Invalid input. Type \"tank\" or \"rogue\" to select a class.");
            }
        }

    }

    public static void gameLoop() throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
            while (running) {
                if (health <= 0){
                    System.out.println("Game over, You died.");
                    System.out.println("Score: "+currentRoomIndex);
                    saveDelete();
                    return;
                }

                System.out.print(">> ");
                String input = scanner.next().toLowerCase();
                switch (input) {
                    case "help":
                        System.out.println("""
                                help: displays help information
                                move forward, forward or f: moves you forward one room
                                move back, back or b: moves you back one room
                                display stats: displays health, stamina and all of your other stats""");
                        break;
                    case "quit", "q":
                        running = false;
                        System.out.print("Save? y/n: ");
                        String yesno = scanner.next();
                        if (Objects.equals(yesno, "n")) {
                            System.out.println("Quitting without saving...");
                            Main.saveDelete();
                            System.exit(0);
                        } else {
                            Main.saveWrite();
                            Main.saveRead();
                            System.out.println("Saved");
                            System.exit(0);

                        }
                        break;
                    case "forward", "f", "move forward":
                        moveForwards();

                        break;
                    case "back", "b", "move back":
                        moveBackwards();

                        break;
                    case "display stats", "stats":
                        displayStats();
                        break;
                    case "heal", "h":
                        player.consumeItem("health potion", 1);
                        break;
                    default:
                        System.out.println("That is not a command. Type \"help\" for help");
                }
            }
        }

    public static void moveForwards() {
        System.out.println("You move forward one room.");
        if (currentRoomIndex == rooms.size()){
            roomGenerator();
        }else {
            currentRoomIndex++;
        }
    }

    public static void moveBackwards() {
        if (currentRoomIndex > 0){
            System.out.println("You move back one room.(currently doesn't work)");
            currentRoomIndex --;
            System.out.println(rooms.get(currentRoomIndex).description);
        }else {
            System.out.println("You can't go back any further");
        }

    }
    public static void displayStats(){
            System.out.println("CON:" + player.con + " DEX:" + player.dex + " STA:" + player.sta + " STR:" + player.str);
            System.out.println("Health: " + health);
            System.out.println("Stamina: "+stamina);
        }
    public static void loadSaveIfExists() throws IOException {
        File saveFile = new File("save.txt");

        if (saveFile.exists()) {
            try {
                System.out.println("Save file found. Loading game...");
                saveRead();
            } catch (IOException e) {
                System.out.println("An error occurred while reading the save file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("No save file found. Starting a new game...");
            saveCreate();
            player = new Player(0, 0, 0, 0);
            startSequence();
        }
    }
    public static void checkCoins() {
        coins = 0;

        for (Item item : player.inventory) {
            if (item instanceof StackableItem) {
                StackableItem stackableItem = (StackableItem) item;
                if (stackableItem.name.equals("coin")) {
                    coins += stackableItem.quantity;
                }
            }
        }

        System.out.println("You have " + coins + " coins.");
    }
    }


