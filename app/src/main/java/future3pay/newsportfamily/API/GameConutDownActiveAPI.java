package future3pay.newsportfamily.API;

import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Activity.BettingRecordDetailActivity;
import future3pay.newsportfamily.Bean.GameCountDownActiveBean;
import future3pay.newsportfamily.Bean.GameCountDownBean;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Fragment.BettingCountDownFragment;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GameConutDownActiveAPI {


    public static void GameConutDownActive() {


        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(DoMainUrl.GameActive)
                .header("Accept", "application/json")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                Index.WeakIndex.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //告知使用者連線失敗
                        ToastShow.start(BettingCountDownFragment.WeakBettingCountDown.get().getActivity(), "無網路狀態，請檢查您的行動網路是否開啟");


                    }

                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                BettingCountDownFragment.WeakBettingCountDown.get().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (response.isSuccessful()) {

                            try {

                                String json = response.body().string();
                                JSONObject content = new JSONObject(json);


                                if (content.getInt("result") == 0) {

                                    if (content.getJSONArray("liveGames").length() > 0) {

                                        BettingCountDownFragment.WeakBettingCountDown.get().CountdownNext.setVisibility(View.GONE);
                                        BettingCountDownFragment.WeakBettingCountDown.get().CountDownRV.setVisibility(View.VISIBLE);
                                        BettingCountDownFragment.WeakBettingCountDown.get().GameCountDownActiveList.clear();
                                        for (int i = 0; i < content.getJSONArray("liveGames").length(); i++) {

                                            BettingCountDownFragment.WeakBettingCountDown.get().GameCountDownActiveList.add(new GameCountDownActiveBean(
                                                    content.getJSONArray("liveGames").getJSONObject(i).getJSONArray("result").toString(),
                                                    content.getJSONArray("liveGames").getJSONObject(i).getJSONArray("game").toString()
                                            ));

                                        }

                                    } else {
                                        BettingCountDownFragment.WeakBettingCountDown.get().CountdownNext.setVisibility(View.VISIBLE);
                                        BettingCountDownFragment.WeakBettingCountDown.get().CountDownRV.setVisibility(View.GONE);
                                        GameConutDownAPI.GameConutDown();
                                    }


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
                        if (BettingCountDownFragment.WeakBettingCountDown.get().GameCountDownActiveList.size() <= 0) {
                            BettingCountDownFragment.WeakBettingCountDown.get().CountdownNext.setVisibility(View.GONE);
                            BettingCountDownFragment.WeakBettingCountDown.get().CountDownRV.setVisibility(View.VISIBLE);
                        } else {
                            BettingCountDownFragment.WeakBettingCountDown.get().CountdownNext.setVisibility(View.VISIBLE);
                            BettingCountDownFragment.WeakBettingCountDown.get().CountDownRV.setVisibility(View.GONE);
                        }
                        BettingCountDownFragment.WeakBettingCountDown.get().GameCountDownActiveAdapter.notifyDataSetChanged();
                    }
                });


            }
        });

    }


}