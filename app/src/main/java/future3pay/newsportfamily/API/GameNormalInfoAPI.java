package future3pay.newsportfamily.API;

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

public class GameNormalInfoAPI {


    public static void GameInfo(){



        OkHttpClient client=new OkHttpClient();



        Request request=new Request.Builder()
                .url(DoMainUrl.GameInfo+Index.WeakIndex.get().SportType)
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

                                    BettingFragment.WeakBettingFragment.get().GameNormalInfoList.clear();
                                    for(int i = 0; i<content.getJSONArray("game").length();i++ ){

                                        BettingFragment.WeakBettingFragment.get().GameNormalInfoList.add(new GameNormalInfoBean(

                                                content.getJSONArray("game").getJSONObject(i).getString("category"),
                                                content.getJSONArray("game").getJSONObject(i).getString("ti"),
                                                content.getJSONArray("game").getJSONObject(i).getJSONArray("game").length(),
                                              content.getJSONArray("game").getJSONObject(i).getJSONArray("game").toString()

                                        ));

                                    }

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

                        if( BettingFragment.WeakBettingFragment.get().GameNormalInfoList.size() <=0){
                            BettingFragment.WeakBettingFragment.get().NoGame.setVisibility(View.VISIBLE);
                        }else{
                            BettingFragment.WeakBettingFragment.get().NoGame.setVisibility(View.GONE);
                        }

                    }
                });


            }
        });




    }






}
