package future3pay.newsportfamily.Bean;

public class GameCountDownActiveBean {



    private String Result;
    private String Game;





    public GameCountDownActiveBean(String Result, String Game){

        this.Result = Result;
        this.Game = Game;


    }



    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }



    public String getGame() {
        return Game;
    }

    public void setGame(String game) {
        Game = game;
    }


}
