package TextAdventure;

import java.util.Scanner;

public class StartSequence {
    public StartSequence(){
        Stats stats = new Stats();
        int[] debugStats = stats.character.getDebugStats();
        Scanner scanner = new Scanner(System.in);
        String startInput;
        System.out.print("Welcome to the Blatant Biohazard Testing Facility!\nPlease choose a class: ");
        startInput = scanner.next();
        switch (startInput){
            case "test":
                stats.character = new Character(debugStats[0],debugStats[1],debugStats[2],debugStats[3]);
        }
    }
}
