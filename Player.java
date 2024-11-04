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


