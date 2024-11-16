package TextAdventure;

public class Enemy {
    int health;
    Attacks attack;
    Attacks secondaryAttack;
    String enemyType;
    public Enemy(int health, Attacks attack, Attacks secondaryAttack, String enemyType){
        this.attack = attack;
        this.health = health;
        this.secondaryAttack = secondaryAttack;
        this.enemyType = enemyType;

        System.out.println("Enemy health: "+health);



    }
    public void enemyAction(){
        int actionRandom = (int) Math.ceil(Math.random() * 2);
        Attacks action = null;
        if (actionRandom == 1){
            action = attack;
        }else if (actionRandom == 2){
            action = secondaryAttack;
        }
            if (action == attack){
                switch (attack){
                    case SLASH:
                        System.out.println("The "+enemyType+" slashed at you!");
                        Main.changeHealth("damage",12);
                        break;

                    case PUNCH:
                        System.out.println("The "+enemyType+" attempted to punch you!");
                        Main.changeHealth("damage",7);
                        break;

                }
            }else if (action == secondaryAttack){
                switch (secondaryAttack){
                    case SLASH:
                        System.out.println("The "+enemyType+" slashed at you!");
                        Main.changeHealth("damage",12);
                        break;

                    case PUNCH:
                        System.out.println("The "+enemyType+" attempted to punch you!");
                        Main.changeHealth("damage",7);
                        break;

                }
            }
        }

    public enum Attacks{
        SLASH,
        PUNCH,
        CLAW,
        CLUB,
        PISTOL,
        BITE,
        SHOTGUN,
        EXPLOSION,
        THROW_ROCK
    }
}
