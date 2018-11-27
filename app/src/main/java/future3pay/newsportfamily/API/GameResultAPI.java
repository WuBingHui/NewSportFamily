package future3pay.newsportfamily.API;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.ArrayRemoveDuplicate;
import future3pay.newsportfamily.Bean.GameResultBean;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Fragment.GameResultFragment;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GameResultAPI {




    public static void GameResul(String type,String time){



        OkHttpClient client=new OkHttpClient();


        //構建FormBody，傳入要提交的參數
        FormBody formBody = new FormBody
                .Builder()
                .add("type",type)
                .add("time", time)
                .build();

        Request request=new Request.Builder()
                .url(DoMainUrl.GameResult)
                .header("Accept","application/json")
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

                                    GameResultFragment.WeakGameResult.get().GameResulList.clear();
                                    GameResultFragment.WeakGameResult.get().GameResulCount.clear();
                                    for(int i=0; i < content.getJSONArray("gamesResult").length();i++){

                                            GameResultFragment.WeakGameResult.get().GameResulList.add(new GameResultBean(
                                                    content.getJSONArray("gamesResult").getJSONObject(i).getString("bets_type"),
                                                    content.getJSONArray("gamesResult").getJSONObject(i).getString("game_start_time"),
                                                    content.getJSONArray("gamesResult").getJSONObject(i).getString("category"),
                                                    content.getJSONArray("gamesResult").getJSONObject(i).getString("ti"),
                                                    content.getJSONArray("gamesResult").getJSONObject(i).getString("away_team"),
                                                    content.getJSONArray("gamesResult").getJSONObject(i).getString("home_team"),
                                                    content.getJSONArray("gamesResult").getJSONObject(i).getString("away_score"),
                                                    content.getJSONArray("gamesResult").getJSONObject(i).getString("home_score"),
                                                    content.getJSONArray("gamesResult").getJSONObject(i).getString("away_period"),
                                                    content.getJSONArray("gamesResult").getJSONObject(i).getString("home_period"),
                                                    content.getJSONArray("gamesResult").getJSONObject(i).getString("finial_result"),
                                                    content.getJSONArray("gamesResult").getJSONObject(i).getString("ni"),
                                                    content.getJSONArray("gamesResult").getJSONObject(i).getString("code"),
                                                    content.getJSONArray("gamesResult").getJSONObject(i).getString("type"),
                                                    content.getJSONArray("gamesResult").getJSONObject(i).getString("status")
                                            ));

                                    }

                                        if( GameResultFragment.WeakGameResult.get().GameResulList.size() <= 0){
                                            GameResultFragment.WeakGameResult.get().GameResultRV.setVisibility(View.GONE);
                                            GameResultFragment.WeakGameResult.get().NoGame.setVisibility(View.VISIBLE);
                                        }else{
                                            GameResultFragment.WeakGameResult.get().GameResultRV.setVisibility(View.VISIBLE);
                                            GameResultFragment.WeakGameResult.get().NoGame.setVisibility(View.GONE);
                                        }


                                }else{

                                    ToastShow.start(Index.WeakIndex.get(),"賽事獲取失敗，請在試一次");

                                }

                        }else{

                            ToastShow.start(Index.WeakIndex.get(),"賽事獲取失敗，請在試一次");

                        }

                        } catch (JSONException e) {

                            e.printStackTrace();
                            ToastShow.start(Index.WeakIndex.get(),"伺服器斷線");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        GameResultFragment.WeakGameResult.get().GameResultRV.finishRefreshing();

                        ArrayAdapter<String> List = new ArrayAdapter<>(GameResultFragment.WeakGameResult.get().getContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                ArrayRemoveDuplicate.RemoveDuplicate(GameResultFragment.WeakGameResult.get().GameResulList));
                        GameResultFragment.WeakGameResult.get().GameResultCategory.setAdapter(List);

                        GameResultFragment.WeakGameResult.get().GetGameResultInfo();



                        GameResultFragment.WeakGameResult.get() .GameResultType.setOnItemSelectedListener(GameResultFragment.WeakGameResult.get().TypeSelect);

                        GameResultFragment.WeakGameResult.get() .GameResultCategory.setOnItemSelectedListener(GameResultFragment.WeakGameResult.get().CategorySelect);

                        GameResultFragment.WeakGameResult.get().GameResultDate.setOnItemSelectedListener(GameResultFragment.WeakGameResult.get().DateSelect);

                        GameResultFragment.WeakGameResult.get().GameResultRV.setVisibility(View.VISIBLE);

                    }
                });


            }
        });




    }








}
