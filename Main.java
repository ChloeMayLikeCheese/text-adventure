package TextAdventure;

import java.io.*;
import java.util.Scanner;

public class Main {
    static int health;
    static Player player;
    static Room[] rooms = new Room[10];
    static int currentRoomIndex = 0;
    static int roomCount = 0;
    static final String ROOM_FILE = "rooms.txt";
    static final String START_ROOM_DESCRIPTION = "Welcome to the Blatant Biohazard Research Facility! Type 'help' for instructions.";

    public static void main(String[] args) {
        player = new Player(0, 0, 0, 0);
        health = 100 + player.con * 10;
        System.out.println("DEBUG: " + " CON:" + player.con + " DEX:" + player.dex + " STA:" + player.sta + " STR:" + player.str);
        System.out.println("Health: " + health);


        rooms[0] = new Room(START_ROOM_DESCRIPTION, new Item[]{}, false);
        roomCount = 1;


        gameLoop();
    }

    public static void gameLoop() {
        boolean playing = true;
        Scanner scanner = new Scanner(System.in);
        while (playing) {

            Room currentRoom = rooms[currentRoomIndex];
            System.out.println(currentRoom.description);
            System.out.print("Enter command (forward, back, quit): ");
            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "move forward", "forward", "f" -> moveForward();
                case "move back", "back", "b" -> moveBackward();
                case "quit", "q" -> {
                    playing = false;
                    System.out.println("Thanks for playing!");
                    clearRoomsFile();
                }
                case "help" -> {
                    System.out.println("""
                            How to play:
                            Stats: Stats determine things that happen in the game, like your constitution, which determines how much health you have.
                            Con: For every level of Con, you get ten more health.
                            Dex: For every level of Dex, you have a higher chance of dodging attacks.
                            Sta: For every level of Sta, you can act more without resting.
                            Str: For every level of Str, you deal a bit more damage.""");
                }
                default -> System.out.println("Unknown command. Please type 'forward', 'back', or 'quit'.");
            }
        }
    }



    public static void moveForward() {
        if (roomCount == 0) {
            System.out.println("No rooms available to move forward.");
            return;
        }

        if (currentRoomIndex < roomCount - 1) {
            currentRoomIndex++;
        } else {
            roomGenerator();
        }

        Room currentRoom = rooms[currentRoomIndex % 10];
        System.out.println(currentRoom.description);
        if (currentRoom.isTrapActive) {
            handleTrap(currentRoom);
        }
    }

    public static void moveBackward() {
        if (roomCount == 0) {
            System.out.println("No rooms available to move back.");
            return;
        }

        if (currentRoomIndex > 0) {
            currentRoomIndex--;
            Room room = rooms[currentRoomIndex % 10];

            System.out.println("Returning to previous room: " + room.description);
            if (room.isTrapActive) {
                handleTrap(room);
            }
        } else {
            System.out.println("You are at the start and cannot go back further.");
        }
    }
    public static void generateNewRoomBatch() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ROOM_FILE, true))) {
            for (int i = 0; i < 10; i++) {
                Room newRoom = Math.random() < 0.5 ? new Room("You found a coin", new Item[]{new Item("Coin")}, false)
                        : new Room("", new Item[]{}, true);
                rooms[i] = newRoom;
                writer.write(newRoom.description + "," + newRoom.isTrapActive);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing rooms to file: " + e.getMessage());
        }
        roomCount += 10;
    }
    public static void handleTrap(Room room) {
        if (room.isTrapActive) {
            System.out.println("Trap room! Watch out for the spikes!");
            if (!dodge()) {
                changeHealth("damage", 10);
            }
            room.isTrapActive = false;
        }
    }

    public static void loadRoomsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ROOM_FILE))) {
            String line;
            roomCount = 0;
            while ((line = reader.readLine()) != null && roomCount < rooms.length) {
                String[] parts = line.split(",");
                String description = parts[0];
                boolean isTrapActive = Boolean.parseBoolean(parts[1]);
                rooms[roomCount] = new Room(description, new Item[]{}, isTrapActive);
                roomCount++;
            }
        } catch (IOException e) {
            System.out.println("No saved rooms found.");
        }
    }

    public static void clearRoomsFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ROOM_FILE))) {
            writer.print("");
        } catch (IOException e) {
            System.out.println("Error clearing rooms file: " + e.getMessage());
        }
    }

    public static void changeHealth(String type, int change) {
        if (type.equalsIgnoreCase("heal")) {
            health += change;
            System.out.println("You healed " + change + " health!\nHealth: " + health);
        } else if (type.equalsIgnoreCase("damage")) {
            health -= change;
            System.out.println("You took " + change + " damage!\nHealth: " + health);
        }
    }



    public static boolean dodge() {
        int dodgeChance = (int) Math.ceil(Math.random() * 10);
        if (dodgeChance <= player.dex) {
            System.out.println("You Dodged!");
            return true;
        }
        return false;
    }
    public static void roomGenerator() {
        int randomRoom = (int) Math.ceil(Math.random() * 2);
        Room newRoom;

        if (randomRoom == 1) {
            newRoom = new Room("You found a coin", new Item[]{new Item("Coin")}, false);
        } else {
            newRoom = new Room("Trap room! Watch out for the spikes!", new Item[]{}, true);
        }

        rooms[roomCount % 10] = newRoom;
        roomCount++;
        System.out.println(newRoom.description);
    }
}
