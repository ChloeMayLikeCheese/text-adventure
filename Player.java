package TextAdventure;

import java.util.ArrayList;

public class Player {
    int con;
    int dex;
    int sta;
    int str;
    ArrayList<Item> inventory;

    public Player(int con, int dex, int sta, int str) {
        this.con = con;
        this.dex = dex;
        this.sta = sta;
        this.str = str;
        this.inventory = new ArrayList<>();
    }

    public void setStats(int con, int dex, int sta, int str) {
        this.con = con;
        this.dex = dex;
        this.sta = sta;
        this.str = str;
    }

    public void editInventory(Item item) {
        if (item instanceof StackableItem) {
            for (Item i : inventory) {
                if (i instanceof StackableItem && i.name.equals(item.name)) {
                    ((StackableItem) i).addQuantity(((StackableItem) item).quantity);
                    System.out.println("Added " + ((StackableItem) item).quantity + " " + item.name + "(s) to inventory.");
                    return;
                }
            }
        }
        inventory.add(item);
        System.out.println("Added " + item.name + " to inventory.");
    }

    public void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Items: ");
            for (Item item : inventory) {
                System.out.println(item);
            }
        }
    }
    public void consumeItem(String itemName, int quantity) {
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);

            if (item.name.equals(itemName.toLowerCase())) {
                if (item instanceof StackableItem) {
                    StackableItem stackableItem = (StackableItem) item;
                    if (stackableItem.quantity >= quantity) {
                        stackableItem.addQuantity(-quantity);
                        System.out.println("Removed " + quantity + " " + itemName + "(s) from inventory.");
                        if (itemName.equals("coin")) {
                            System.out.println("You spent " + quantity + " coin(s)!");
                        } else if (itemName.equals("sword")) {
                            System.out.println("You no longer have a sword to fight with!");
                        } else if (itemName.equals("health potion")) {
                            Main.changeHealth("heal",10);
                        }
                        return;
                    } else {
                        System.out.println("Not enough " + itemName + " in inventory to remove.");
                        return;
                    }
                } else {
                    inventory.remove(i);
                    System.out.println("Removed " + itemName + " from inventory.");

                    if (itemName.equals("sword")) {
                        System.out.println("You no longer have a sword to fight with!");
                    }
                    return;
                }
            }
        }
        System.out.println(itemName + " not found in inventory.");
    }
    public void useItem(String itemName) {
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);

            if (item.name.equalsIgnoreCase(itemName)) {
                if (item instanceof StackableItem) {
                    System.out.println("You can't use that item.");
                } else {
                    if (itemName.equals("sword")) {
                        System.out.println("You swing your sword!");
                    } else if (itemName.equals("pistol")) {
                        System.out.println("You shoot your pistol!");
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



