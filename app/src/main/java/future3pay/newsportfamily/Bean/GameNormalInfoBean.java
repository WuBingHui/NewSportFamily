package future3pay.newsportfamily.Bean;

/**
 * Created by cy on 2018/4/6.
 */

public class GameNormalInfoBean {

    private String Category;

    private String Ti;
    private String Status;
    private String Game;
    private int GameLeagth;




    public GameNormalInfoBean(String Category, String Ti, int GameLeagth, String Game) {

        this.Category = Category;
        this.Ti = Ti;
        this.GameLeagth = GameLeagth;
        this.Game = Game;

    }

    public int getGameLeagth() {
        return GameLeagth;
    }

    public void setGameLeagth(int gameLeagth) {
        GameLeagth = gameLeagth;
    }

    public String getGame() {
        return Game;
    }

    public void setGame(String game) {
        Game = game;
    }


    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }



    public String getTi() {
        return Ti;
    }

    public void setTi(String ti) {
        Ti = ti;
    }


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }



}
