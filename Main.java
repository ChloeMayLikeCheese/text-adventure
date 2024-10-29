
package TextAdventure;

import java.util.Arrays;

public class Main {

    public static void main(String[] args){
        Stats stats = new Stats();
        stats.character.hp+=stats.character.con*10;
        System.out.println(stats.character.hp);
        changeHealth("damage",10);
        System.out.println(stats.character.hp);
/*
        Scanner scanner = new Scanner(System.in);
        String input;
*/
        roomGenerator();
        System.out.println("DEBUG: "+" CON:"+ stats.character.con+" DEX:"+ stats.character.dex+" STA:"+ stats.character.sta+" STR:"+ stats.character.str);

    }
    public static void roomGenerator(){
        int randomRoom = (int) Math.round(Math.random() * 2);
        System.out.println("DEBUG Room: "+randomRoom);

        switch (randomRoom){
            case 1:
                Room coinAndAxe = new Room("You found a coin and an axe",new Item[]{new Item("Coin"),new Item("Axe")});
                System.out.println(coinAndAxe.description + " "+Arrays.toString(coinAndAxe.items));
                break;
            case 2:
                RoomNoItems trap = new RoomNoItems("Trap room! Watch out for the spikes!");
                System.out.println(trap.description);
                break;

        }


        }

        public static void changeHealth(String type, int change){
        Stats stats = new Stats();
        if (type.equalsIgnoreCase("heal")){
            stats.character.hp += change;
        } else if (type.equalsIgnoreCase("damage")){
            stats.character.hp -= change;
        }


        }
        public static void Dodge(){
            Stats stats = new Stats();
            int dodgeChance = (int) Math.ceil(Math.random()*10);
            System.out.println("Dodge chance: "+dodgeChance);
            if (dodgeChance <= stats.character.dex){
                System.out.println("Dodge success");
                return;
            }
            System.out.println("Dodge fail");
        }
    }