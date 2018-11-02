package future3pay.newsportfamily.Bean;

public class GameNormalInfoDetailBean {

    private String gameStartTime;
    private String category;
    private String awayTeam;
    private String homeTeam;
    private String code;
    private String ni;
    private String si;
    private String ti;
    private String id;
    private String lv;
    private String mins;
    private String status;
    private String diffTime;
    private String bets;

    public GameNormalInfoDetailBean(String gameStartTime,String category,String awayTeam,String homeTeam,String code,String ni,String si,String ti,String id,String lv,String mins,String status,String diffTime,String bets) {

        this.gameStartTime  =gameStartTime ;
        this.category  =category ;
        this.awayTeam  =awayTeam ;
        this.homeTeam  =homeTeam ;
        this.code  =code ;
        this.ni  =ni ;
        this.si  =si ;
        this.ti  =ti ;
        this.id  =id ;
        this.lv  =lv ;
        this.mins  =mins ;
        this.status  =status ;
        this.diffTime  =diffTime ;
        this.bets  =bets ;

    }


    public String getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(String gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNi() {
        return ni;
    }

    public void setNi(String ni) {
        this.ni = ni;
    }

    public String getSi() {
        return si;
    }

    public void setSi(String si) {
        this.si = si;
    }

    public String getTi() {
        return ti;
    }

    public void setTi(String ti) {
        this.ti = ti;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public String getMins() {
        return mins;
    }

    public void setMins(String mins) {
        this.mins = mins;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiffTime() {
        return diffTime;
    }

    public void setDiffTime(String diffTime) {
        this.diffTime = diffTime;
    }

    public String getBets() {
        return bets;
    }

    public void setBets(String bets) {
        this.bets = bets;
    }


}
