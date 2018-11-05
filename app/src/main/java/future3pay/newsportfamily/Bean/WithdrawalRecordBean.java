package future3pay.newsportfamily.Bean;

public class WithdrawalRecordBean {


    private String amount;
    private String status;
    private String bank_info;
    private String create_time;
    private String update_time;

    public WithdrawalRecordBean(String amount, String status, String bank_info, String create_time, String update_time) {

        this.amount = amount;
        this.status = status;
        this.bank_info = bank_info;
        this.create_time = create_time;
        this.update_time = update_time;

    }

    public String getAmount() {

        return amount;

    }

    public void setAmount(String amount) {

        this.amount = amount;

    }

    public String getStatus() {

        return status;

    }

    public void setStatus(String status) {

        this.status = status;

    }

    public String getBank_info() {

        return bank_info;

    }

    public void setBank_info(String bank_info) {
        this.bank_info = bank_info;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }


}
