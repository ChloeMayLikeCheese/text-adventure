package TextAdventure;

public class Item {
    String name;
    public Item(String name){
//        if (name == null) {
//
//        }
        this.name = name;
    }
    @Override
    public String toString(){
        return this.name;
    }
}
