package TextAdventure;

public class Player {
    int con;
    int dex;
    int sta;
    int str;

    public Player(int con, int dex, int sta, int str) {
        this.con = con;
        this.dex = dex;
        this.sta = sta;
        this.str = str;
    }

    public void setStats(int con, int dex, int sta, int str) {
        this.con = con;
        this.dex = dex;
        this.sta = sta;
        this.str = str;
    }

}
//    public int[] getRougeStats(){
//        return new int[]{1,5,3,3};
//    }
//    public int[] getDebugStats(){
//        return new int[]{5,5,5,5};
//    }

