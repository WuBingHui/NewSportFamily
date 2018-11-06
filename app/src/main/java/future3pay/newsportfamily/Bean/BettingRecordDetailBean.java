package future3pay.newsportfamily.Bean;


public class BettingRecordDetailBean {

    private String OrderNo;
    private String BarCode;
    private String BetsType;
    private String Combination;
    private String SingleAmount;
    private String Amount;
    private String Payout;
    private String BetsTime;
    private String ErrorReason;
    private String Infos;

    public BettingRecordDetailBean(String OrderNo, String BarCode, String BetsType, String Combination,String SingleAmount, String Amount, String Payout, String BetsTime, String ErrorReason, String Infos) {

        this.OrderNo = OrderNo;
        this.BarCode = BarCode;
        this.BetsType = BetsType;
        this.Combination = Combination;
        this.SingleAmount = SingleAmount;
        this.Amount = Amount;
        this.Payout = Payout;
        this.BetsTime = BetsTime;
        this.ErrorReason = ErrorReason;
        this.Infos = Infos;

    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getBetsType() {
        return BetsType;
    }

    public void setBetsType(String betsType) {
        BetsType = betsType;
    }

    public String getCombination() {
        return Combination;
    }

    public void setCombination(String combination) {
        Combination = combination;
    }

    public String getSingleAmount() {
        return SingleAmount;
    }

    public void setSingleAmount(String singleAmount) {
        SingleAmount = singleAmount;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getPayout() {
        return Payout;
    }

    public void setPayout(String payout) {
        Payout = payout;
    }

    public String getBetsTime() {
        return BetsTime;
    }

    public void setBetsTime(String betsTime) {
        BetsTime = betsTime;
    }

    public String getErrorReason() {
        return ErrorReason;
    }

    public void setErrorReason(String errorReason) {
        ErrorReason = errorReason;
    }

    public String getInfos() {
        return Infos;
    }

    public void setInfos(String infos) {
        Infos = infos;
    }





}
