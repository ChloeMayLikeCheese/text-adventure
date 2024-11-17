package TextAdventure;

import java.util.ArrayList;

import static TextAdventure.Main.enemy;

public class Player {
    //variables for player stats and inventory
    int con;
    int dex;
    int sta;
    int str;
    static int ammo = 0;  // Ammo count (static because shared by all instances)
    ArrayList<Item> inventory;  // Inventory to store items

    // Constructor to initialize the player's stats and inventory
    public Player(int con, int dex, int sta, int str) {
        this.con = con;
        this.dex = dex;
        this.sta = sta;
        this.str = str;
        this.inventory = new ArrayList<>();  // Initialize an empty inventory
    }

    // Method to set or update the player's stats
    public void setStats(int con, int dex, int sta, int str) {
        this.con = con;
        this.dex = dex;
        this.sta = sta;
        this.str = str;
    }

    // Method to edit the player's inventory (add an item)
    public void editInventory(Item item) {
        // If the item is stackable, check if it's already in the inventory
        if (item instanceof StackableItem) {
            for (Item i : inventory) {
                if (i instanceof StackableItem && i.name.equals(item.name)) {
                    // If found, increase the quantity of the stackable item
                    ((StackableItem) i).addQuantity(((StackableItem) item).quantity);
                    System.out.println("Added " + ((StackableItem) item).quantity + " " + item.name + "(s) to inventory.");
                    return;
                }
            }
        }
        // If the item is not stackable or not found, add it to the inventory
        inventory.add(item);
        System.out.println("Added " + item.name + " to inventory.");
    }

    // Method to display the items in the player's inventory
    public void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Items: ");
            // Print each item in the inventory
            for (Item item : inventory) {
                System.out.println(item);
            }
        }
    }

    // Method to consume an item
    public void consumeItem(String itemName, int quantity) {
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);

            if (item.name.equals(itemName.toLowerCase())) {
                if (item instanceof StackableItem) {
                    // If the item is stackable, check if there are enough to consume
                    StackableItem stackableItem = (StackableItem) item;
                    if (stackableItem.quantity >= quantity) {
                        // Reduce the quantity of the item
                        stackableItem.addQuantity(-quantity);
                        System.out.println("Removed " + quantity + " " + itemName + "(s) from inventory.");
                        // Handle specific item effects (e.g., coins, health potion)
                        if (itemName.equals("coin")) {
                            System.out.println("You spent " + quantity + " coin(s)!");
                        } else if (itemName.equalsIgnoreCase("health potion")) {
                            Main.changeHealth("heal", 5);  // Heal the player
                        } else if (itemName.equalsIgnoreCase("scrap metal")) {
                            ammo += 1;  // Increase ammo from scrap metal
                        }
                        return;
                    } else {
                        System.out.println("Not enough " + itemName + " in inventory to remove.");
                        return;
                    }
                } else {
                    // If the item is not stackable, remove it from inventory
                    inventory.remove(i);
                    System.out.println("Removed " + itemName + " from inventory.");
                    return;
                }
            }
        }
        System.out.println(itemName + " not found in inventory.");
    }

    // Method to use an item from the inventory
    public void useItem(String itemName) {
        System.out.println(ammo);
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            if (item.name.equalsIgnoreCase(itemName)) {
                if (item instanceof StackableItem) {
                    System.out.println("You can't use that item.");
                } else {
                    // Handle the use of different items
                    if (itemName.equalsIgnoreCase("sword")) {
                        System.out.println("You swing your sword!");
                        enemy.health -= 15 + str;  // Damage the enemy with sword+str for melee attacks
                    } else if (itemName.equalsIgnoreCase("pistol")) {
                        System.out.println("You shoot your pistol!");
                        enemy.health -= 10;  // Damage the enemy with pistol
                        ammo -= 1;  // Reduce ammo count
                        System.out.println("ammo:" + ammo);
                    } else if (itemName.equalsIgnoreCase("crowbar")) {
                        System.out.println("You strike with your crowbar");
                        enemy.health -= 8 + str;
                    } else if (itemName.equalsIgnoreCase("shotgun")) {
                        enemy.health -= 19;
                        ammo -= 4;  // Reduce ammo count for shotgun
                        System.out.println("ammo:" + ammo);
                    } else if (itemName.equalsIgnoreCase("debug")) {
                        // Case for debugging
                        enemy.health -= 1000000000;
                    } else {
                        System.out.println("You used the " + itemName + "!");
                    }
                    return;
                }
            }
        }
        System.out.println(itemName + " not found in inventory.");
    }
}
