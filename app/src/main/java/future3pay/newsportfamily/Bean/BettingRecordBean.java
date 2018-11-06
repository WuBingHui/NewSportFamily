package future3pay.newsportfamily.Bean;

public class BettingRecordBean {




    private String PurseTotal;
    private String BetsTotal;
    private String SubTotal;
    private String Orders;


    public BettingRecordBean( String PurseTotal, String BetsTotal, String SubTotal, String Orders) {


        this.PurseTotal = PurseTotal;
        this.BetsTotal = BetsTotal;
        this.SubTotal = SubTotal;
        this.Orders = Orders;

    }



    public String getPurseTotal() {
        return PurseTotal;
    }

    public void setPurseTotal(String purseTotal) {
        PurseTotal = purseTotal;
    }

    public String getBetsTotal() {
        return BetsTotal;
    }

    public void setBetsTotal(String betsTotal) {
        BetsTotal = betsTotal;
    }

    public String getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(String subTotal) {
        SubTotal = subTotal;
    }
    public String getOrders() {
        return Orders;
    }

    public void setOrders(String orders) {
        Orders = orders;
    }


}
