package future3pay.newsportfamily;

import org.json.JSONException;
import org.json.JSONObject;

import future3pay.newsportfamily.Fragment.MemberFragment;

public class UserInfo {


    public static void add(JSONObject content,String token){

        try {
            Index.WeakIndex.get().UserInfo.edit()
                    .putString("Token",token)
                    .putString("Permission",content.getJSONObject("userInfo").getString("permission"))
                    .putString("BelongStore",content.getJSONObject("userInfo").getString("belong_store"))
                    .putString("UserName",content.getJSONObject("userInfo").getString("username"))
                    .putString("Name",content.getJSONObject("userInfo").getString("name"))
                    .putString("Email",content.getJSONObject("userInfo").getString("email"))
                    .putString("Phone",content.getJSONObject("userInfo").getString("phone"))
                    .putString("BankName",content.getJSONObject("userInfo").getString("bank_name"))
                    .putString("BankAccount",content.getJSONObject("userInfo").getString("bank_account"))
                    .putString("OriginPoints",content.getJSONObject("userInfo").getString("origin_points"))
                    .putString("WonPoints",content.getJSONObject("userInfo").getString("won_points"))
                    .putString("InviteCode",content.getJSONObject("userInfo").getString("invite_code"))
                    .putString("OrderProcessing",content.getJSONObject("userInfo").getString("orderProcessing"))
                    .putString("OrderDone",content.getJSONObject("userInfo").getString("orderDone"))
                    .putString("OrderOfTheDay",content.getJSONObject("userInfo").getString("orderOfTheDay"))
                    .apply();
            MemberFragment.WeakMemberFragment.get().FamilyPoint.setText(content.getJSONObject("userInfo").getString("origin_points"));
            MemberFragment.WeakMemberFragment.get().CashPoint.setText(content.getJSONObject("userInfo").getString("won_points"));
            MemberFragment.WeakMemberFragment.get().InviteCode.setText(content.getJSONObject("userInfo").getString("invite_code"));
            MemberFragment.WeakMemberFragment.get().TodayOrderCount.setText(content.getJSONObject("userInfo").getString("orderOfTheDay"));
            MemberFragment.WeakMemberFragment.get().WaitBonus.setText(content.getJSONObject("userInfo").getString("orderDone"));
            MemberFragment.WeakMemberFragment.get().WaitOrder.setText(content.getJSONObject("userInfo").getString("orderProcessing"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public static void remove(){

        Index.WeakIndex.get().UserInfo.edit().clear().apply();

    }


}
