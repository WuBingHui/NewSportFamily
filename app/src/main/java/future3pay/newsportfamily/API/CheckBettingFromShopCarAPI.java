package future3pay.newsportfamily.API;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.StatmentDialog;
import future3pay.newsportfamily.UIkit.ToastShow;
import future3pay.newsportfamily.VerifyData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CheckBettingFromShopCarAPI {


    public static void CheckBettingFromShopCar(String token, final String amount, final String payout, final String combo, final String combination, final String column, final String item){

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

                        Log.d("aaaaaaaaaaaaaaaaaa", amount);
                        Log.d("aaaaaaaaaaaaaaaaaa", payout);
                        Log.d("aaaaaaaaaaaaaaaaaa", combo);
                        Log.d("aaaaaaaaaaaaaaaaaa", combination);
                        Log.d("aaaaaaaaaaaaaaaaaa", column);
                        Log.d("aaaaaaaaaaaaaaaaaa", item);

                        try {

                            String json =response.body().string();
                            JSONObject content = new JSONObject(json);
                            Log.d("aaaaaaaaaaaaaaaaaa", String.valueOf(content));
                            if(response.isSuccessful()){


                                if(content.getInt("result") == 0){

                                    StatmentDialog.Statment();

                                }else{


                                    if(content.getInt("result") == 3){
                                        Index.WeakIndex.get().UserInfo.edit().clear().apply();

                                    }

                                    if(content.getInt("result") == 4){


                                    }

                                    if(content.getInt("result") == 5){


                                    }
                                    String message = "";
                                    for(int i = 0 ; i <content.getString("message").length();i++){
                                        if( VerifyData.VerifyChiness(content.getString("message").substring(i,i+1))){
                                            message = message +content.getString("message").substring(i,i+1);
                                        }

                                    }
                                    ToastShow.start(Index.WeakIndex.get(),message);
                                    Index.WeakIndex.get().ShopCarInfoList.clear();
                                }


                            }else{

                                if(content.getInt("result") == 3){
                                    Index.WeakIndex.get().UserInfo.edit().clear().apply();

                                }

                                if(content.getInt("result") == 4){


                                }

                                if(content.getInt("result") == 5){


                                }

                                String message = "";
                                for(int i = 0 ; i <content.getString("message").length();i++){
                                    if( VerifyData.VerifyChiness(content.getString("message").substring(i,i+1))){
                                        message = message +content.getString("message").substring(i,i+1);
                                    }
                                }
                                ToastShow.start(Index.WeakIndex.get(),message);
                                Index.WeakIndex.get().ShopCarInfoList.clear();
                            }
                        } catch (JSONException e) {
                            Log.d("aaaaaaaaaaaaaaaaaa",e.toString());
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
