package TextAdventure;

public class Enemy {
    int health;
    Attacks attacks;
    public  Enemy(int health, Attacks attacks){
        this.attacks = attacks;
        this.health = health;
    }
    public enum Attacks{
        SLASH,
        PUNCH,
        CLAW,
        CLUB,
        FIREBALL
    }
}
