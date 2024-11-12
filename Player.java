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
            for (Item item : inventory) {
                System.out.println(item);
            }
        }
    }
}



