package future3pay.newsportfamily.Bean;

public class BettingRecordBean {


    private String ordersInfo;
    private String purseTotal;
    private String betsTotal;
    private String subTotal;
    private String order_no;
    private String bets_type;
    private String amount;
    private String purseAmount;
    private String status;
    private String bets_time;

    public BettingRecordBean(String ordersInfo, String purseTotal, String betsTotal, String subTotal, String order_no, String bets_type, String amount, String purseAmount, String status, String bets_time) {

        this.ordersInfo = ordersInfo;
        this.purseTotal = purseTotal;
        this.betsTotal = betsTotal;
        this.subTotal = subTotal;
        this.order_no = order_no;
        this.bets_type = bets_type;
        this.amount = amount;
        this.purseAmount = purseAmount;
        this.status = status;
        this.bets_time = bets_time;

    }


    public String getOrdersInfo() {
        return ordersInfo;
    }

    public void setOrdersInfo(String ordersInfo) {
        this.ordersInfo = ordersInfo;
    }

    public String getPurseTotal() {
        return purseTotal;
    }

    public void setPurseTotal(String purseTotal) {
        this.purseTotal = purseTotal;
    }

    public String getBetsTotal() {
        return betsTotal;
    }

    public void setBetsTotal(String betsTotal) {
        this.betsTotal = betsTotal;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getBets_type() {
        return bets_type;
    }

    public void setBets_type(String bets_type) {
        this.bets_type = bets_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPurseAmount() {
        return purseAmount;
    }

    public void setPurseAmount(String purseAmount) {
        this.purseAmount = purseAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBets_time() {
        return bets_time;
    }

    public void setBets_time(String bets_time) {
        this.bets_time = bets_time;
    }

}
