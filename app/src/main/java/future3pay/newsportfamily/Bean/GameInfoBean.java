package future3pay.newsportfamily.Bean;

/**
 * Created by cy on 2018/4/6.
 */

public class GameInfoBean {

    private String gameStartTime;
    private String Category;
    private String AwayTeam;
    private String HomeTeam;
    private String Code;
    private String Ni;
    private String Si;
    private String Ti;
    private String Id;
    private String Lv;
    private String Mins;
    private String Status;
    private String DiffTime;
    private String Bets;
    private String Game;
    private int GameLeagth;




    public GameInfoBean(String Category, String Ti, int GameLeagth, String Game) {

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

    public String getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(String gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
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

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getNi() {
        return Ni;
    }

    public void setNi(String ni) {
        Ni = ni;
    }

    public String getSi() {
        return Si;
    }

    public void setSi(String si) {
        Si = si;
    }

    public String getTi() {
        return Ti;
    }

    public void setTi(String ti) {
        Ti = ti;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLv() {
        return Lv;
    }

    public void setLv(String lv) {
        Lv = lv;
    }

    public String getMins() {
        return Mins;
    }

    public void setMins(String mins) {
        Mins = mins;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDiffTime() {
        return DiffTime;
    }

    public void setDiffTime(String diffTime) {
        DiffTime = diffTime;
    }

    public String getBets() {
        return Bets;
    }

    public void setBets(String bets) {
        Bets = bets;
    }

}
