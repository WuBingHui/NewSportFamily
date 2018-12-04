package future3pay.newsportfamily.Bean;

public class GameCountDownBean {
    private String Id;
    private String Code;
    private String AwayTeam;
    private String HomeTeam;
    private String GameStartTime;
    private String Game;



    public GameCountDownBean(String Id, String Code, String AwayTeam, String HomeTeam, String GameStartTime, String Game){

        this.Id = Id;
        this.Code = Code;
        this.AwayTeam = AwayTeam;
        this.HomeTeam = HomeTeam;
        this.GameStartTime = GameStartTime;
        this.Game = Game;
    }

    public String getGame() {
        return Game;
    }

    public void setGame(String game) {
        Game = game;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getAwayTeam() {
        return AwayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        AwayTeam = awayTeam;
    }

    public String getHomeTeam() {
        return HomeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        HomeTeam = homeTeam;
    }

    public String getGameStartTime() {
        return GameStartTime;
    }

    public void setGameStartTime(String gameStartTime) {
        GameStartTime = gameStartTime;
    }

}
