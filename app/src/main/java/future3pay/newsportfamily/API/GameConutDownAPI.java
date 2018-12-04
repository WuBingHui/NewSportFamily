package future3pay.newsportfamily.API;

import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Bean.GameCountDownBean;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Fragment.BettingCountDownFragment;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GameConutDownAPI {


    public static void GameConutDown() {


        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(DoMainUrl.GameCountDown)
                .header("Accept", "application/json")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                Index.WeakIndex.get().runOnUiThread(new Runnable() {
                    @Override
                    public synchronized void run() {

                        //告知使用者連線失敗
                        ToastShow.start(BettingCountDownFragment.WeakBettingCountDown.get().getActivity(), "無網路狀態，請檢查您的行動網路是否開啟");

                        Loading.diss();
                    }

                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {



                BettingCountDownFragment.WeakBettingCountDown.get().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public synchronized void run() {

                        if (response.isSuccessful()) {

                            try {

                                String json = response.body().string();
                                JSONObject content = new JSONObject(json);

                                if (content.getInt("result") == 0) {

                                    BettingCountDownFragment.WeakBettingCountDown.get().GameCountDownList.clear();


                                    BettingCountDownFragment.WeakBettingCountDown.get().GameCountDownList.add(new GameCountDownBean(
                                            content.getJSONObject("countdownGame").getString("id"),
                                            content.getJSONObject("countdownGame").getString("code"),
                                            content.getJSONObject("countdownGame").getString("awayTeam"),
                                            content.getJSONObject("countdownGame").getString("homeTeam"),
                                            content.getJSONObject("countdownGame").getString("gameStartTime"),
                                            String.valueOf(content.getJSONObject("countdownGame").getJSONArray("game"))
                                    ));
                                    BettingCountDownFragment.WeakBettingCountDown.get().CountDownAway.setText(content.getJSONObject("countdownGame").getString("awayTeam"));
                                    BettingCountDownFragment.WeakBettingCountDown.get().CountDownHome.setText(content.getJSONObject("countdownGame").getString("homeTeam"));
                                    BettingCountDownFragment.WeakBettingCountDown.get().GmaeNext();
                                } else {

                                    ToastShow.start(BettingCountDownFragment.WeakBettingCountDown.get().getActivity(), "場中賽事獲取失敗");
                                }
                            } catch (JSONException e) {

                                e.printStackTrace();
                                ToastShow.start(BettingCountDownFragment.WeakBettingCountDown.get().getActivity(), "伺服器斷線");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {

                            ToastShow.start(BettingCountDownFragment.WeakBettingCountDown.get().getActivity(), "場中賽事獲取失敗");

                        }
                        if (BettingCountDownFragment.WeakBettingCountDown.get().GameCountDownList.size() <= 0) {
                            BettingCountDownFragment.WeakBettingCountDown.get().NoCountDown.setVisibility(View.VISIBLE);
                            BettingCountDownFragment.WeakBettingCountDown.get().CountdownNext.setVisibility(View.GONE);
                        } else {
                            BettingCountDownFragment.WeakBettingCountDown.get().NoCountDown.setVisibility(View.GONE);
                            BettingCountDownFragment.WeakBettingCountDown.get().CountdownNext.setVisibility(View.VISIBLE);
                        }

                        Loading.diss();
                    }
                });


            }
        });

    }



}