package TextAdventure;

import java.io.*;
import java.util.Scanner;

public class Main {
    static int health;
    static Player player;
    static final String START_ROOM_DESCRIPTION = "Welcome to the Blatant Biohazard Research Facility! Type 'help' for instructions.";

    public static void main(String[] args) {
        player = new Player(0, 0, 0, 0);


        startSequence();


        health = 100 + player.con * 10;
        System.out.println("DEBUG: CON:" + player.con + " DEX:" + player.dex + " STA:" + player.sta + " STR:" + player.str);
        System.out.println("Health: " + health);


    }

    public static void startSequence() {
        Scanner scanner = new Scanner(System.in);
        String startInput;
        boolean running = true;

        while (running) {
            System.out.print("Welcome to the Blatant Biohazard Research Facility!\nType 'help' for instructions on how to play.\nClass options: Tank, Rogue\nPlease choose a class (or type 'info' for more information on each class): " );
            startInput = scanner.next().toLowerCase();

            switch (startInput) {
                case "debug", "d" :
                    player.setStats(5, 5, 5, 5);
                    running = false;
                    break;

                case "tank" :
                    player.setStats(5, 1, 2, 4);
                    running = false;
                    break;

                case "rogue" :
                    player.setStats(1, 5, 4, 2);
                    running = false;
                    break;

                case "info" :
                    System.out.println("Tank: High constitution and strength, but low dexterity and stamina. Meant to hit hard and withstand attacks\n" +
                            "Rogue: High dexterity and stamina but low constitution and strength. Meant for quick, low damage attacks to chip away at an enemy's health.");
                    break;
                case "help" :
                    System.out.println("""
                        How to play:
                        Stats determine things that happen in the game.
                        Con: More health per level.
                        Dex: Higher dodge chance.
                        Sta: More actions without resting.
                        Str: Higher damage per level.""");
                    break;

                default : System.out.println("Invalid input. Type 'tank' or 'rogue' to select a class.");
            }
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
    }
}
