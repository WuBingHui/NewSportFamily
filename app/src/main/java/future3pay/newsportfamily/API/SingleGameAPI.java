package future3pay.newsportfamily.API;

import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Bean.GameNormalInfoBean;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Fragment.BettingFragment;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SingleGameAPI {


    public static void SingleGame(){

        OkHttpClient client=new OkHttpClient();

        Request request=new Request.Builder()
                .url(DoMainUrl.SingleGame)
                .header("Accept","application/json")
                .build();
        Call call =client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                Index.WeakIndex.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //告知使用者連線失敗
                        ToastShow.start(  Index.WeakIndex.get(),"無網路狀態，請檢查您的行動網路是否開啟");
                        Loading.diss();
                        BettingFragment.WeakBettingFragment.get().BettingRV.finishRefreshing();
                        BettingFragment.WeakBettingFragment.get().GameNormalAdapter.notifyDataSetChanged();
                        BettingFragment.WeakBettingFragment.get().BettingDetailRV.setVisibility(View.GONE);
                        BettingFragment.WeakBettingFragment.get().BettingRV.setVisibility(View.GONE);
                        BettingFragment.WeakBettingFragment.get().NoGame.setVisibility(View.VISIBLE);
                    }

                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {



                        if(response.isSuccessful()){

                            try {

                                String json =response.body().string();
                                final JSONObject content = new JSONObject(json);

                                if(content.getInt("result") == 0){



                                    Index.WeakIndex.get().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                BettingFragment.WeakBettingFragment.get().GetNormalBettingDetail(content.getJSONArray("singleGames").toString());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });



                                }else{

                                    ToastShow.start(  Index.WeakIndex.get(),"賽事獲取失敗");

                                }

                            } catch (JSONException e) {

                                e.printStackTrace();
                                ToastShow.start(  Index.WeakIndex.get(),"伺服器斷線");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }else{

                            ToastShow.start(  Index.WeakIndex.get(),"賽事獲取失敗");

                        }


                Index.WeakIndex.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Loading.diss();
                        BettingFragment.WeakBettingFragment.get().BettingRV.finishRefreshing();
                        BettingFragment.WeakBettingFragment.get().GameNormalAdapter.notifyDataSetChanged();
                        BettingFragment.WeakBettingFragment.get().BettingDetailRV.setVisibility(View.VISIBLE);
                        BettingFragment.WeakBettingFragment.get().BettingRV.setVisibility(View.GONE);
                        BettingFragment.WeakBettingFragment.get().NoGame.setVisibility(View.GONE);
                    }
                });




            }
        });




    }






}
