package TextAdventure;

import java.io.*;
import java.util.*;

public class Main {
    // Variables for health, exp, power cores and stamina
    static int health;
    // Get the player and enemy classes
    static Player player;
    static Enemy enemy;
    static int stamina;
    // Variables for movement and making sure rooms don't spawn twice in row
    static int currentRoomIndex = -1;
    static int lastRoomIndex = -1;
    static ArrayList <Room> rooms;
    static int powerCores;
    static int exp;
    // Used for the final score count
    static int totalExp;
    public static void main(String[] args) throws IOException {
        // Creating arraylist for rooms
        rooms = new ArrayList<>();
        // Assigning player a default value
        player = new Player(0, 0, 0, 0);
        // Starting the game
        startSequence();
        // Setting health and displaying health and stamina
        health = 50 + player.con * 10;
        stamina = 100 + player.sta * 10;
        displayStats();
        // Starting the main game loop
        System.out.println("Type \"help\" for help");
        gameLoop();

    }


    // Method for changing health
    public static void changeHealth(String type, int change) {
        // Checking the type variable to see if it is heal or damage
        // Also checking the change variable to see how much
        if (type.equalsIgnoreCase("heal")) {
            health += change;
            System.out.println("You healed " + change + " health!\nHealth: " + health);
        } else if (type.equalsIgnoreCase("damage")) {
            Scanner scannerIn = new Scanner(System.in);
            // Dodge if damage
            System.out.println("Do you want to attempt a dodge? This will take stamina. (y/n)");
            String in = scannerIn.nextLine().toLowerCase();
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
    //Handling dodges
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
    // Generating rooms
    public static void roomGenerator() {
        Scanner roomScanner = new Scanner(System.in);
        int randomRoom;
        // Making sure two of the same rooms don't spawn in a row
        do {
            randomRoom = (int) Math.ceil(Math.random() * 8);
        } while (randomRoom == lastRoomIndex);

        Room newRoom = null;
        lastRoomIndex = randomRoom;

        // Handling room gen
        switch (randomRoom) {
            case 1:
                // New stackable item
                StackableItem powerCore = new StackableItem("power core", 1);
                newRoom = new Room("You found a power core", new Item[]{new Item("power core")}, "power core");
                // Printing item quantity and description of room
                System.out.println(newRoom.description + " Items: " + Arrays.toString(newRoom.items) + " x" + powerCore.quantity);
                // Changing description for when you go back
                newRoom.description = "It's an empty room";
                // Adding item to inventory
                player.editInventory(powerCore);
                break;
            case 2:
                StackableItem scrapMetal = new StackableItem("scrap metal", 2);
                newRoom = new Room("Trap room! Watch out for the spikes!", new Item[]{new Item("scrap metal")}, "trap");
                System.out.println(newRoom.description + Arrays.toString(newRoom.items) + " x" + scrapMetal.quantity);
                newRoom.description = "It's a room with a broken trap";
                // Example of taking damage
                changeHealth("damage", 10);
                player.editInventory(scrapMetal);
                break;
            case 3:
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
                // New enemy, set attacks, health and type
                enemy = new Enemy(50, Enemy.Attacks.PUNCH, Enemy.Attacks.THROW_ROCK, "zombie");
                // Enter combat loop
                combat();
                break;
            case 6:
                newRoom = new Room("Watch out! Its a giant rat!", new Item[]{}, "giant rat");
                System.out.println(newRoom.description);
                newRoom.description = "It's a dead rat";
                enemy = new Enemy(20, Enemy.Attacks.CLAW, Enemy.Attacks.BITE, "giant rat");
                combat();
                break;
            case 7:
                newRoom = new Room("Watch out! Its an armed zombie with a pistol!", new Item[]{}, "armed pistol zombie");
                System.out.println(newRoom.description);
                newRoom.description = "It's a dead zombie";
                enemy = new Enemy(60, Enemy.Attacks.PISTOL, Enemy.Attacks.THROW_ROCK, "armed pistol zombie");
                combat();
                player.editInventory(new Item("pistol"));
                break;
            case 8:
                newRoom = new Room("Watch out! Its an armed zombie with a shotgun!", new Item[]{}, "armed shotgun zombie");
                System.out.println(newRoom.description);
                newRoom.description = "It's a dead zombie";
                enemy = new Enemy(60, Enemy.Attacks.SHOTGUN, Enemy.Attacks.PUNCH, "armed shotgun zombie");
                combat();
                player.editInventory(new Item("shotgun"));
                break;
            case 9:
                newRoom = new Room("It's a glowing orb.",new Item[]{},"glowing orb");
                System.out.println(newRoom.description);
                newRoom.description = "It's a mildly singed room";
                // Just a little funny thing i wanted to put in
                System.out.print("Do you want to poke it with a stick?");
                String roomYesNo = roomScanner.nextLine();
                if (roomYesNo.equalsIgnoreCase("y")){
                    System.out.println("The orb blows up!");
                    changeHealth("damage",25);
                }else {
                    enemy = new Enemy(1,Enemy.Attacks.EXPLOSION,null,"orb creature");
                    combat();
                }
                break;
            default:
                // Error if room doesn't exist
                System.out.println("DEBUG: Error: Room doesnt exist");
        }
        // Adding new room to arraylist
        rooms.add(newRoom);
        // Increasing room index
        currentRoomIndex++;
    }



    // The start thingy
    public static void startSequence() throws IOException {
        // Create scanner
        Scanner scanner = new Scanner(System.in);
        String startInput;
        boolean running = true;
        // Intro text
        System.out.println("""
                Welcome to the Gray Mesa Research Facility!
                The Gray Mesa Research Facility is a bioengineering testing facility located of Desert road, Ruapehu, Aotearoa.
                It is your mission to enter the facility and collect as many power cores as possible.
                if you see any anomalies, purge immediately.
                You lose when either your health or stamina gets to zero""");
        // Handle exiting
        while (running) {
            System.out.print("Type \"help\" for instructions on how to play.\nClass options: Tank, Rogue\nPlease choose a class (or type 'info' for more information on each class): ");
            startInput = scanner.nextLine().toLowerCase();

            switch (startInput) {
                // Creating player with set stats
                case "tank":
                    player.setStats(5, 1, 2, 4);
                    running = false;
                    player.editInventory(new Item("crowbar"));
                    break;

                case "rogue":
                    player.setStats(1, 5, 4, 2);
                    running = false;
                    player.editInventory(new Item("crowbar"));
                    break;

                case "info":
                    System.out.println("Tank: High constitution and strength, but low dexterity and stamina. Meant to hit hard and withstand attack\n" +
                            "Rogue: High dexterity and stamina but low constitution and strength. Meant for quick, low damage attack to chip away at an enemy's health.");
                    break;
                // Help meeeeeeee
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
                            display stats: displays health, stamina and all of your other stats
                            level up: levels you up if you have enough exp""");
                    break;
                // Quitting
                case "q", "quit":
                        System.exit(0);
                    break;


                default:
                    // If invalid input
                    System.out.println("Invalid input. Please select a class");
            }
        }

    }
    // The main game loop
    public static void gameLoop() throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        // Game over :(
            while (running) {
                if (health <= 0 || stamina <= 0){
                    gameOver();
                    return;
                }
                // Input prompt
                System.out.print(">> ");
                String input = scanner.nextLine().toLowerCase();
                switch (input) {
                    // Help meeeeeee
                    case "help":
                        System.out.println("""
                                help: displays help information
                                move forward, forward or f: moves you forward one room
                                move back, back or b: moves you back one room
                                display stats: displays health, stamina and all of your other stats
                                inventory or i: displays inventory
                                heal or h: heals you, will consume a health potion
                                ammo: consumes one scrap metal for 5 ammo
                                level up: levels you up if you have enough exp""");
                        break;
                    // Quitting
                    case "quit", "q":
                        running = false;
                        System.exit(0);


                        break;
                    // Moving
                    case "forward", "f", "move forward":
                        moveForwards();

                        break;
                    case "back", "b", "move back":
                        moveBackwards();

                        break;
                    // Display stats
                    case "display stats", "stats":
                        displayStats();
                        break;
                    // Heal
                    case "heal", "h":
                        player.consumeItem("health potion", 1);
                        break;
                    // Inventory
                    case "inventory","i":
                        player.displayInventory();
                        break;
                    // kinda crafting
                    case "ammo":
                        player.consumeItem("scrap metal",1);
                        break;
                    // Leveling up
                    case "level up":
                        levelUp();
                        break;
                    default:
                        System.out.println("That is not a command. Type \"help\" for help");
                }
            }
        }
    // Moving
    public static void moveForwards() {
        if (currentRoomIndex == -1){
            System.out.println("You enter the facility");
        }else {
            System.out.println("You move forward one room.");
            stamina -= 2;
        }
        if (currentRoomIndex >= rooms.size() -1){
            roomGenerator();
        }else {
            currentRoomIndex++;
        }
    }
    // Moon walking
    public static void moveBackwards() {
        if (currentRoomIndex > 0){
            System.out.println("You move back one room.");
            // Smoooooth criminal
            stamina -= 2;
            currentRoomIndex --;
            System.out.println(rooms.get(currentRoomIndex).description);
        }else {
            System.out.println("You can't go back any further");
        }

    }
    // Displaying stats
    public static void displayStats(){
            System.out.println("CON:" + player.con + " DEX:" + player.dex + " STA:" + player.sta + " STR:" + player.str);
            System.out.println("Health: " + health);
            System.out.println("Stamina: "+stamina);
        }
        // Game over and displaying score and power cores
    public static void gameOver(){
        powerCores = 0;
        for (Item item : player.inventory) {
            if (item instanceof StackableItem) {
                StackableItem stackableItem = (StackableItem) item;
                if (stackableItem.name.equals("power core")) {
                    powerCores += stackableItem.quantity;
                }
            }
        }
        System.out.println("Game Over! You died.");
        System.out.println("Score:"+ powerCores + currentRoomIndex+totalExp);
        System.out.println("Power cores:"+powerCores);
    }
    // Main combat loop
    public static void combat(){
        // Handling enemy death
        while (enemy.health > 0) {
            // Handling enemy action, player action and if the player has no ammo
            enemy.enemyAction();
            if (player.ammo <= 0) {
                playerTurnNoAmmo();
            }else {
                playerTurn();
            }
            System.out.println("Enemy Health:"+enemy.health);
        }
        // Yipeee!!
        if (enemy.health <= 0){
            System.out.println("you win!");
            exp += 10;
            totalExp += 10;

        }
    }
    // Handling player turn
    // Pikachu i choose you!
    public static void playerTurn(){
        Scanner combatScanner = new Scanner(System.in);
            System.out.println("Ammo: " + player.ammo);
            System.out.print("What would you like to do? (Type \"help\" for help): ");
            String combatIn = combatScanner.nextLine().toLowerCase();
            // Switch statement for the players turn
            switch (combatIn) {
                case "attack", "a":
                    System.out.print("""
                        What would you like to attack with?
                        1 or crowbar: Attack with your crowbar, you get one by default.
                        2 or sword: Attack with sword if you have one.
                        3 or pistol: Attack with pistol if you have one, will take ammo.
                        4 or shotgun: Attack with shotgun if you have one, will take ammo.
                        5 or debug: For testing purposes.
                        >>\s""");
                    String attackObject = combatScanner.nextLine().toLowerCase();
                    stamina -= 5;
                    // Switch statement for how you want to attack
                    switch (attackObject) {
                        case "1", "crowbar":
                            player.useItem("crowbar");
                            break;
                        case "2", "sword":
                            player.useItem("sword");
                            break;
                        case "3", "pistol":
                            player.useItem("pistol");
                            break;
                        case "4", "shotgun":
                            player.useItem("shotgun");
                            break;
                        case "5", "debug":
                            player.useItem("debug");
                            break;
                        default:
                            // Couldnt figure out how to do non recursive things for returning
                            System.out.println("Invalid attack choice.");
                            playerTurn();
                            break;
                    }
                    break;

                case "help":
                    // Help
                    System.out.println("""
                        Commands in combat:
                        attack or a: Attack the enemy
                        heal or h: Consume a healing potion to heal 5 health
                        inventory or i: Check your inventory
                        """);
                    playerTurn();
                    break;
                // Heal
                case "heal", "h":
                    player.consumeItem("health potion", 1);
                    break;
                // Inventory
                case "inventory", "i":
                    player.displayInventory();
                    playerTurn();
                    break;


                default:
                    System.out.println("That is not a valid command.");
                    playerTurn();
                    break;
            }
        }
        // Its the same as player turn but with no gun attacks. couldn't figure out how to handle having no ammo
    public static void playerTurnNoAmmo(){
        Scanner combatScanner = new Scanner(System.in);


            System.out.println("Ammo: " + player.ammo);
            System.out.print("What would you like to do? (Type \"help\" for help): ");
            String combatIn = combatScanner.nextLine().toLowerCase();
            switch (combatIn) {
                case "attack", "a":
                    System.out.print("""
                        What would you like to attack with?
                        1 or crowbar: Attack with your crowbar, you get one by default.
                        2 or sword: Attack with sword if you have one.
                        3 or pistol: You cannot, no ammo.
                        4 or shotgun: You cannot, no ammo.
                        >>\s""");
                    String attackObject = combatScanner.nextLine().toLowerCase();
                    stamina -= 5;

                    switch (attackObject) {
                        case "1", "crowbar":
                            player.useItem("crowbar");
                            break;
                        case "2", "sword":
                            player.useItem("sword");
                            break;
                        case "5", "debug":
                            player.useItem("debug");
                            break;
                        default:
                            System.out.println("Invalid attack choice.");
                            playerTurnNoAmmo();
                            break;
                    }
                    break;

                case "help":
                    System.out.println("""
                        Commands in combat:
                        attack or a: Attack the enemy
                        heal or h: Consume a healing potion to heal 5 health
                        inventory or i: Check your inventory
                        """);
                    playerTurnNoAmmo();
                    break;

                case "heal", "h":
                    player.consumeItem("health potion", 1);
                    break;

                case "inventory", "i":
                    player.displayInventory();
                    playerTurnNoAmmo();
                    break;

                default:
                    System.out.println("That is not a valid command.");
                    playerTurnNoAmmo();
                    break;
            }
        }
    // Levelling up
    public static void levelUp() {
        Scanner scanner = new Scanner(System.in);
        // Experience check
        if (exp < 10) {
            System.out.println("You need at least 10 experience to level up.");
            return;
        }


        System.out.println("You have " + exp + " experience points. Which stat would you like to level up?");
        System.out.println("1. Constitution (CON) - Current: " + player.con);
        System.out.println("2. Dexterity (DEX) - Current: " + player.dex);
        System.out.println("3. Stamina (STA) - Current: " + player.sta);
        System.out.println("4. Strength (STR) - Current: " + player.str);

        int choice = scanner.nextInt();

        // Switch statement for choice
        switch (choice) {
            case 1:
                if (exp >= 10) {
                    exp -= 10;
                    player.con += 1;
                    System.out.println("Your Constitution (CON) has increased to: " + player.con);
                }
                break;
                // Dex is limited as to not become invincible
            case 2:
                if (player.dex < 5) {
                    if (exp >= 10) {
                        exp -= 10;
                        player.dex += 1;
                        System.out.println("Your Dexterity (DEX) has increased to: " + player.dex);
                    } else {
                        System.out.println("You need more experience to level up Dexterity.");
                    }
                } else {
                    System.out.println("Dexterity is already at its maximum value of 5.");
                }
                break;
            case 3:
                if (exp >= 10) {
                    exp -= 10;
                    player.sta += 1;
                    System.out.println("Your Stamina (STA) has increased to: " + player.sta);
                }
                break;
            case 4:
                if (exp >= 10) {
                    exp -= 10;
                    player.str += 1;
                    System.out.println("Your Strength (STR) has increased to: " + player.str);
                }
                break;
            default:
                System.out.println("Invalid choice. Please select a valid stat to level up.");
        }
        System.out.println("You have " + exp + " experience left.");
    }


}

