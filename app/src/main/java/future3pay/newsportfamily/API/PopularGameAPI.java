package future3pay.newsportfamily.API;

import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

public class PopularGameAPI {


    public static void PopularGame(){

        OkHttpClient client=new OkHttpClient();

        Request request=new Request.Builder()
                .url(DoMainUrl.PopularGame)
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

                Index.WeakIndex.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(response.isSuccessful()){

                            try {

                                String json =response.body().string();
                                JSONObject content = new JSONObject(json);

                                if(content.getInt("result") == 0){

                                    BettingFragment.WeakBettingFragment.get().GetNormalBettingDetail(content.getJSONArray("popularGames").toString());

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
