package future3pay.newsportfamily.API;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Activity.VerifyEmailActivity;
import future3pay.newsportfamily.Activity.VerifyPhoneActivity;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.StatmentDialog;
import future3pay.newsportfamily.UIkit.ToastShow;
import future3pay.newsportfamily.UserInfo;
import future3pay.newsportfamily.VerifyData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CheckBettingFromShopCarAPI {


    public static void CheckBettingFromShopCar(final String token, final String amount, final String payout, final String combo, final String combination, final String column, final String item){

        OkHttpClient client=new OkHttpClient();



        //構建FormBody，傳入要提交的參數
        FormBody formBody = new FormBody
                .Builder()
                .add("amount",amount)
                .add("payout",payout)
                .add("combo",combo)
                .add("combination",combination)
                .add("column",column)
                .add("item","["+item+"]")
                .build();
        Request request=new Request.Builder()
                .url(DoMainUrl.CheckBettingFromShopCar)
                .header("Accept","application/json")
                .header("authorization", "bearer " + token)
                .post(formBody)
                .build();
        Call call =client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


               Index.WeakIndex.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Loading.diss();
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

                                    StatmentDialog.Statment();

                                }else{


                                    //信箱未驗證
                                    if(content.getInt("result") == 4) {
                                        ToastShow.start(Index.WeakIndex.get(),"信箱尚未通過驗證");
                                        UserInfo.add(content,token);
                                        Intent intent =new Intent();
                                        intent.setClass(Index.WeakIndex.get(),VerifyEmailActivity.class);
                                        Index.WeakIndex.get().startActivity(intent);
                                    }else{
                                        //手機未驗證
                                        if(content.getInt("result") == 5){
                                            ToastShow.start(Index.WeakIndex.get(),"手機尚未通過驗證");
                                            UserInfo.add(content,token);
                                            Intent intent =new Intent();
                                            intent.setClass(Index.WeakIndex.get(),VerifyPhoneActivity.class);
                                            Index.WeakIndex.get().startActivity(intent);
                                        }else{
                                            if(content.getInt("result") == 3){
                                                Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                                ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                            }else{
                                                String message = "";
                                                for(int i = 0 ; i <content.getString("message").length();i++){
                                                    if( VerifyData.VerifyChiness(content.getString("message").substring(i,i+1))){
                                                        message = message +content.getString("message").substring(i,i+1);
                                                    }
                                                }
                                                ToastShow.start(Index.WeakIndex.get(),message);
                                            }
                                        }
                                    }
                                    ////
                                   // Index.WeakIndex.get().ShopCarInfoList.clear();
                                }


                            }else{

                                //信箱未驗證
                                if(content.getInt("result") == 4) {
                                    ToastShow.start(Index.WeakIndex.get(),"信箱尚未通過驗證");
                                    UserInfo.add(content,token);
                                    Intent intent =new Intent();
                                    intent.setClass(Index.WeakIndex.get(),VerifyEmailActivity.class);
                                    Index.WeakIndex.get().startActivity(intent);
                                }else{
                                    //手機未驗證
                                    if(content.getInt("result") == 5){
                                        ToastShow.start(Index.WeakIndex.get(),"手機尚未通過驗證");
                                        UserInfo.add(content,token);
                                        Intent intent =new Intent();
                                        intent.setClass(Index.WeakIndex.get(),VerifyPhoneActivity.class);
                                        Index.WeakIndex.get().startActivity(intent);
                                    }else{
                                        if(content.getInt("result") == 3){
                                            Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                            ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                        }else{
                                            String message = "";
                                            for(int i = 0 ; i <content.getString("message").length();i++){
                                                if( VerifyData.VerifyChiness(content.getString("message").substring(i,i+1))){
                                                    message = message +content.getString("message").substring(i,i+1);
                                                }
                                            }
                                            ToastShow.start(Index.WeakIndex.get(),message);
                                        }
                                    }
                                }
                                ////
                                //Index.WeakIndex.get().ShopCarInfoList.clear();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                            ToastShow.start(Index.WeakIndex.get(),"伺服器無回應");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Loading.diss();
                        Index.WeakIndex.get().ShopCarAdapter.notifyDataSetChanged();
                    }
                });


            }
        });




    }


}
