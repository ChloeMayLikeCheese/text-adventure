package TextAdventure;

public class Enemy {
    //  Variables for the enemy's health, attack options, and enemy type
    int health;
    Attacks attack;
    Attacks secondaryAttack;
    String enemyType;

    // Constructor to make the enemy
    public Enemy(int health, Attacks attack, Attacks secondaryAttack, String enemyType){
        this.attack = attack;
        this.health = health;
        this.secondaryAttack = secondaryAttack;
        this.enemyType = enemyType;


        System.out.println("Enemy health: "+health);
    }

    // Method to simulate the enemy performing an action
    public void enemyAction(){
        // Randomly select an action (1 for primary attack, 2 for secondary attack)
        int actionRandom = (int) Math.ceil(Math.random() * 2);
        Attacks action = null;

        // Choose the attack based on the random number
        if (actionRandom == 1){
            action = attack;
        } else if (actionRandom == 2){
            action = secondaryAttack;
        }

        // If the chosen action is the primary attack
        if (action == attack){
            // Switch statement for handling different attack types
            switch (attack){
                case SLASH:
                    System.out.println("The "+enemyType+" slashed at you!");
                    Main.changeHealth("damage",12);
                    break;

                case PUNCH:
                    System.out.println("The "+enemyType+" attempted to punch you!");
                    Main.changeHealth("damage",7);
                    break;

                case BITE:
                    System.out.println("The "+enemyType+" attempted to bite you!");
                    Main.changeHealth("damage",5);
                    break;

                case CLAW:
                    System.out.println("The "+enemyType+" clawed at you!");
                    Main.changeHealth("damage",9);
                    break;

                case CLUB:
                    System.out.println("The "+enemyType+" attempted to club you!");
                    Main.changeHealth("damage",11);
                    break;

                case PISTOL:
                    System.out.println("The "+enemyType+" attempted to shoot you with a pistol!");
                    Main.changeHealth("damage",10);
                    break;

                case EXPLOSION:
                    System.out.println("The "+enemyType+" exploded!");
                    Main.changeHealth("damage",25);
                    health -= 999; // Creeper aww maan
                    break;

                case SHOTGUN:
                    System.out.println("The "+enemyType+" attempted to shoot you with a shotgun!");
                    Main.changeHealth("damage",16);
                    break;

                case THROW_ROCK:
                    System.out.println("The "+enemyType+" attempted to throw a rock at you!");
                    Main.changeHealth("damage",6);
                    break;
            }
        }
        // If the chosen action is the secondary attack
        else if (action == secondaryAttack){
            // Switch statement for handling different secondary attack types
            switch (secondaryAttack){
                case SLASH:
                    System.out.println("The "+enemyType+" slashed at you!");
                    Main.changeHealth("damage",12);
                    break;

                case PUNCH:
                    System.out.println("The "+enemyType+" attempted to punch you!");
                    Main.changeHealth("damage",7);
                    break;

                case BITE:
                    System.out.println("The "+enemyType+" attempted to bite you!");
                    Main.changeHealth("damage",5);
                    break;

                case CLAW:
                    System.out.println("The "+enemyType+" clawed at you!");
                    Main.changeHealth("damage",9);
                    break;

                case CLUB:
                    System.out.println("The "+enemyType+" attempted to club you!");
                    Main.changeHealth("damage",11);
                    break;

                case PISTOL:
                    System.out.println("The "+enemyType+" attempted to shoot you with a pistol!");
                    Main.changeHealth("damage",10);
                    break;

                case EXPLOSION:
                    System.out.println("The "+enemyType+" exploded!");
                    Main.changeHealth("damage",25);
                    health -= 999;
                    break;

                case SHOTGUN:
                    System.out.println("The "+enemyType+" attempted to shoot you with a shotgun!");
                    Main.changeHealth("damage",16); // Apply damage to the player
                    break;

                case THROW_ROCK:
                    System.out.println("The "+enemyType+" attempted to throw a rock at you!");
                    Main.changeHealth("damage",6);
                    break;


                case null:
                    break;
            }
        }
    }

    // Enum for defining possible attack types
    public enum Attacks{
        SLASH,
        PUNCH,
        CLAW,
        CLUB,
        PISTOL,
        BITE,
        SHOTGUN,
        EXPLOSION,   // KABOOM
        THROW_ROCK
    }
}
