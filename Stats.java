package TextAdventure;

public class Stats {
        // Maybe add char later
        Character character;

     public Stats(){
         int[] debugStats = character.getDebugStats();
         character = new Character(debugStats[0],debugStats[1],debugStats[2],debugStats[3]);

     }
    }
