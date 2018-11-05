package future3pay.newsportfamily.Bean;

public class UseRecordBean {


    private String OrderNo;
    private String OriginPoints;
    private String WonPoints;
    private String RecordTime;

    public UseRecordBean(String OrderNo, String OriginPoints, String WonPoints, String RecordTime) {

        this.OrderNo = OrderNo;
        this.OriginPoints = OriginPoints;
        this.WonPoints = WonPoints;
        this.RecordTime = RecordTime;
    }


    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getOriginPoints() {
        return OriginPoints;
    }

    public void setOriginPoints(String originPoints) {
        OriginPoints = originPoints;
    }

    public String getWonPoints() {
        return WonPoints;
    }

    public void setWonPoints(String wonPoints) {
        WonPoints = wonPoints;
    }

    public String getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(String recordTime) {
        RecordTime = recordTime;
    }


}
