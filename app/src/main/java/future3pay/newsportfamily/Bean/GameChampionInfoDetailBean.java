package future3pay.newsportfamily.Bean;

public class GameChampionInfoDetailBean {


    private String category;
    private String betsTitle;
    private String codes;
    private String mins;
    private String gameStartTime;
    private String code;


    public GameChampionInfoDetailBean(String category, String betsTitle, String codes, String mins,String gameStartTime, String code) {

        this.category = category;

        this.betsTitle = betsTitle;

        this.codes = codes;

        this.mins = mins;

        this.gameStartTime = gameStartTime;

        this.code = code;


    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBetsTitle() {
        return betsTitle;
    }

    public void setBetsTitle(String betsTitle) {
        this.betsTitle = betsTitle;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getMins() {
        return mins;
    }

    public void setMins(String mins) {
        this.mins = mins;
    }

    public String getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(String gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
