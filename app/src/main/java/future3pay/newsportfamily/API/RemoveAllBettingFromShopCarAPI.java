package future3pay.newsportfamily.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Bean.ShopCarInfoBean;
import future3pay.newsportfamily.BettingRule;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Fragment.BettingFragment;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RemoveAllBettingFromShopCarAPI {


    public static void RemoveAllBettingFromShopCar(String token){

        OkHttpClient client=new OkHttpClient();


        //構建FormBody，傳入要提交的參數
        FormBody formBody = new FormBody
                .Builder()
                .build();
        Request request=new Request.Builder()
                .url(DoMainUrl.RemoveAllBettingFromShopCar)
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


                                            ToastShow.start(Index.WeakIndex.get(),"已清空");
                                            Index.WeakIndex.get().ShopCarInfoList.clear();
                                    Index.WeakIndex.get().BettingSum.setText("0");
                                    Index.WeakIndex.get().BettingWon.setText("0");
                                    Index.WeakIndex.get().ComboTypeSelect.removeAllViews();
                                            Index.WeakIndex.get().ShopCarAdapter.notifyDataSetChanged();



                                }else{

                                    ToastShow.start(Index.WeakIndex.get(),content.getString("message"));

                                }


                            }else{

                                if(content.getInt("result") == 3){
                                    Index.WeakIndex.get().UserInfo.edit().clear().apply();

                                }

                                ToastShow.start(Index.WeakIndex.get(),content.getString("message"));


                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                            ToastShow.start(Index.WeakIndex.get(),"伺服器無回應");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Loading.diss();
                    }
                });


            }
        });




    }
}