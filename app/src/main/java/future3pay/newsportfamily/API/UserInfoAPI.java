package future3pay.newsportfamily.API;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Fragment.GameResultFragment;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserInfoAPI {

    public static void UserInfo(String token){

        OkHttpClient client=new OkHttpClient();

        Request request=new Request.Builder()
                .url(DoMainUrl.Info)
                .header("Accept","application/json")
                .header("authorization","bearer "+token)

                .build();
        Call call =client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                Index.WeakIndex.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //告知使用者連線失敗
                        ToastShow.start(Index.WeakIndex.get(),"無網路狀態，請檢查您的行動網路是否開啟");

                    }

                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                Index.WeakIndex.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String json =response.body().string();
                            JSONObject content = new JSONObject(json);
                        if(response.isSuccessful()){

                                if(content.getInt("result") == 0){

                                    Index.WeakIndex.get().UserInfo.edit()
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
                                            .apply();
                                }else{

                                    ToastShow.start(Index.WeakIndex.get(),content.getString("message"));

                                }

                        }else{

                            ToastShow.start(Index.WeakIndex.get(),content.getString("message"));

                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                        ToastShow.start(Index.WeakIndex.get(),"伺服器無回應");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    }
                });


            }
        });




    }




}
