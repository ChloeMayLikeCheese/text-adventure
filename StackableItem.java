package TextAdventure;

public class StackableItem extends Item {
    int quantity;

    public StackableItem(String name, int quantity) {
        super(name);
        this.quantity = quantity;
    }

    public void addQuantity(int amount) {
        this.quantity += amount;
    }

    @Override
    public String toString() {
        return this.name + " (x" + quantity + ")";
    }
}
