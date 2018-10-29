package future3pay.newsportfamily.Bean;

public class GameResultBean {

    private String BetsType;
    private String GameStartTime;
    private String Category;
    private String Ti;
    private String AwayTeam;
    private String HomeTeam;
    private String AwayScore;
    private  String HomeScore;
    private String AwayPeriod;
    private  String HomePeriod;
    private String FinialResult;
    private String Ni;
    private String Code;
    private String Type;
    private String Status;


    public String getBetsType() {
        return BetsType;
    }

    public void setBetsType(String betsType) {
        BetsType = betsType;
    }

    public String getGameStartTime() {
        return GameStartTime;
    }

    public void setGameStartTime(String gameStartTime) {
        GameStartTime = gameStartTime;
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

    public String getAwayScore() {
        return AwayScore;
    }

    public void setAwayScore(String awayScore) {
        AwayScore = awayScore;
    }

    public String getHomeScore() {
        return HomeScore;
    }

    public void setHomeScore(String homeScore) {
        HomeScore = homeScore;
    }

    public String getAwayPeriod() {
        return AwayPeriod;
    }

    public void setAwayPeriod(String awayPeriod) {
        AwayPeriod = awayPeriod;
    }

    public String getHomePeriod() {
        return HomePeriod;
    }

    public void setHomePeriod(String homePeriod) {
        HomePeriod = homePeriod;
    }

    public String getFinialResult() {
        return FinialResult;
    }

    public void setFinialResult(String finialResult) {
        FinialResult = finialResult;
    }

    public String getNi() {
        return Ni;
    }

    public void setNi(String ni) {
        Ni = ni;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }





    public GameResultBean(String BetsType,String GameStartTime,String Category,String Ti,String AwayTeam,String HomeTeam,String AwayScore,String HomeScore,String AwayPeriod,String HomePeriod,String FinialResult,String Ni,String Code,String Type,String Status){


        this.BetsType = BetsType;
        this.GameStartTime = GameStartTime;
        this.Category =Category;
        this.Ti = Ti;
        this.AwayTeam = AwayTeam;
        this.HomeTeam = HomeTeam;
        this.AwayScore = AwayScore;
        this.HomeScore =  HomeScore;
        this.AwayPeriod = AwayPeriod;
        this.HomePeriod =  HomePeriod;
        this.FinialResult =  FinialResult;
        this.Ni =  Ni;
        this.Code =  Code;
        this.Type =  Type;
        this.Status = Status;



    }


}
