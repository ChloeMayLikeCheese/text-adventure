package TextAdventure;
public class Room {
    Item[] items;
    String description;
    boolean isTrapActive;

    public Room(String description, Item[] items, boolean isTrapActive) {
        this.description = description;
        this.items = items;
        this.isTrapActive = isTrapActive;
    }

    @Override
    public String toString() {
        return description + (isTrapActive ? " (Trap is active!)" : "");
    }
}


