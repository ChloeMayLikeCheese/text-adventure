package TextAdventure;
public class Room {
    Item[] items;
    String description;
    String type;

    public Room(String description, Item[] items,String type) {
        this.description = description;
        this.items = items;
        this.type = type;

    }

    @Override
    public String toString() {
        return description ;
    }
}


