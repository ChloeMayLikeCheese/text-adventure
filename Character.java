package TextAdventure;

public class Character {
    int con;
    int dex;
    int sta;
    int str;
    public Character(int con, int dex , int sta, int str){
        this.con = con;
        this.dex = dex;
        this.sta = sta;
        this.str = str;
    }
    public int[] getTankStats(){
        return new int[]{5,1,2,4};
    }
    public int[] getRougeStats(){
        return new int[]{1,5,3,3};
    }
    public int[] getDebugStats(){
        return new int[]{5,5,5,5};
    }
}
