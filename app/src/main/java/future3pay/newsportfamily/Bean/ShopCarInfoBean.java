package future3pay.newsportfamily.Bean;

import org.json.JSONObject;

public class ShopCarInfoBean {

    private JSONObject Item;



    public ShopCarInfoBean(JSONObject Item){
        this.Item= Item;


    }

    public JSONObject getItem() {
        return Item;
    }

    public void setItem(JSONObject item) {
        Item = item;
    }




}
