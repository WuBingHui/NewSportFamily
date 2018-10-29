package future3pay.newsportfamily.Bean;

public class GameChampionInfoBean {



    private String Category;

    private String GameInfo;

    private int GameLegth;




    public  GameChampionInfoBean(String Category,String GameInfo,int GameLegth){

        this.Category = Category;
        this.GameInfo= GameInfo ;
        this.GameLegth = GameLegth;

    }

    public int getGameLegth() {
        return GameLegth;
    }

    public void setGameLegth(int gameLegth) {
        GameLegth = gameLegth;
    }


    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getGameInfo() {
        return GameInfo;
    }

    public void setGameInfo(String gameInfo) {
        GameInfo = gameInfo;
    }



}
