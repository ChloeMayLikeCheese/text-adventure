package TextAdventure;
public class Room {
    // Variables for items description and type
    Item[] items;
    String description;
    String type;
    // Method for initializing room object with descriptions, items and type
    public Room(String description, Item[] items,String type) {
        this.description = description;
        this.items = items;
        this.type = type;

    }
    // Override the toString method to return the room's description as the string representation
    @Override
    public String toString() {
        return description ;
    }
}


