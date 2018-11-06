package future3pay.newsportfamily.API;

import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Activity.BettingRecordActivity;
import future3pay.newsportfamily.Activity.BettingRecordDetailActivity;
import future3pay.newsportfamily.Bean.BettingRecordBean;
import future3pay.newsportfamily.Bean.BettingRecordDetailBean;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BettingRecordDetailAPI {

    public static void BettingRecordDetail(final String token,String order) {

        OkHttpClient client = new OkHttpClient();



        Request request = new Request.Builder()
                .url(DoMainUrl.BettingRecordDetail+order)
                .header("Accept", "application/json")
                .header("authorization", "bearer " + token)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                BettingRecordDetailActivity.WeakBettingRecordDetail.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Loading.diss();
                        //告知使用者連線失敗
                        ToastShow.start(BettingRecordDetailActivity.WeakBettingRecordDetail.get(), "無網路狀態，請檢查您的行動網路是否開啟");

                    }

                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                BettingRecordDetailActivity.WeakBettingRecordDetail.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            String json = response.body().string();
                            JSONObject content = new JSONObject(json);

                            if (response.isSuccessful()) {

                                if (content.getInt("result") == 0) {
                                    BettingRecordDetailActivity.WeakBettingRecordDetail.get().BettingRecordDetailList.clear();
                                    for(int i = 0 ;i< content.getJSONObject("orders").getJSONArray("infos").length();i++ ){

                                        BettingRecordDetailActivity.WeakBettingRecordDetail.get().BettingRecordDetailList.add(new BettingRecordDetailBean(
                                                content.getJSONObject("orders").getString("order_no"),
                                                content.getJSONObject("orders").getString("bar_code"),
                                                content.getJSONObject("orders").getString("bets_type"),
                                                content.getJSONObject("orders").getString("combination"),
                                                content.getJSONObject("orders").getString("single_amount"),
                                                content.getJSONObject("orders").getString("amount"),
                                                content.getJSONObject("orders").getString("payout"),
                                                content.getJSONObject("orders").getString("bets_time"),
                                                content.getJSONObject("orders").getString("error_reason"),
                                                content.getJSONObject("orders").getJSONArray("infos").getJSONObject(i).toString()
                                        ));

                                    }



                                } else {

                                    BettingRecordDetailActivity.WeakBettingRecordDetail.get().finish();
                                    ToastShow.start(BettingRecordDetailActivity.WeakBettingRecordDetail.get(), content.getString("message"));
                                    Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                    Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);

                                }

                            } else {

                                BettingRecordDetailActivity.WeakBettingRecordDetail.get().finish();
                                ToastShow.start(BettingRecordDetailActivity.WeakBettingRecordDetail.get(), content.getString("message"));
                                Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                            Loading.diss();
                            ToastShow.start(BettingRecordDetailActivity.WeakBettingRecordDetail.get(), "伺服器無回應");

                        } catch (IOException e) {
                            e.printStackTrace();
                            Loading.diss();
                        }

                        Loading.diss();
                        BettingRecordDetailActivity.WeakBettingRecordDetail.get().BettingRecordDetailRV.finishRefreshing();
                        BettingRecordDetailActivity.WeakBettingRecordDetail.get().BettingRecordDetailAdapter.notifyDataSetChanged();
                    }
                });


            }
        });


    }


}
