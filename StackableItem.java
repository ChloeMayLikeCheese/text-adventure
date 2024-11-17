package TextAdventure;

// Extending from the main item class
public class StackableItem extends Item {
    // Item quantity
    int quantity;

    public StackableItem(String name, int quantity) {
        // Getting name from the item class
        super(name);
        this.quantity = quantity;
    }
    // Adding quantity
    public void addQuantity(int amount) {
        this.quantity += amount;
    }
    // Override the toString method to return the room's description as the string representation
    @Override
    public String toString() {
        return this.name + " (x" + quantity + ")";
    }
}
