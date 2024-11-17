package TextAdventure;

public class Item {
    //  Variable to store the name of the item
    String name;

    // Constructor to create the item with a name
    public Item(String name){
        this.name = name;
    }

    // Method to return the name of the item when the object is printed
    public String toString(){
        return this.name;
    }
}
