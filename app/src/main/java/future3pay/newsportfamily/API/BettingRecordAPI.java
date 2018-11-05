package future3pay.newsportfamily.API;

import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Activity.BettingRecordActivity;
import future3pay.newsportfamily.Activity.WithdrawalRecordActivity;
import future3pay.newsportfamily.Bean.WithdrawalRecordBean;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BettingRecordAPI {

    public static void BettingRecord(final String token) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(DoMainUrl.BettingRecord)
                .header("Accept", "application/json")
                .header("authorization", "bearer " + token)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                BettingRecordActivity.WeakBettingRecord.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Loading.diss();
                        //告知使用者連線失敗
                        ToastShow.start( BettingRecordActivity.WeakBettingRecord.get(), "無網路狀態，請檢查您的行動網路是否開啟");

                    }

                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                BettingRecordActivity.WeakBettingRecord.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String json = response.body().string();
                            JSONObject content = new JSONObject(json);

                            if (response.isSuccessful()) {

                                if (content.getInt("result") == 0) {
                                    BettingRecordActivity.WeakBettingRecord.get().BettingRecordList.clear();
                                    for(int i =0 ; i<content.getJSONArray("records").length();i++){
                                        /*
                                        BettingRecordActivity.WeakBettingRecord.get().BettingRecordList.add(new WithdrawalRecordBean(
                                                    content.getJSONArray("records").getJSONObject(i).getString("amount"),
                                                    content.getJSONArray("records").getJSONObject(i).getString("status"),
                                                    content.getJSONArray("records").getJSONObject(i).getString("bank_info"),
                                                            content.getJSONArray("records").getJSONObject(i).getString("create_time"),
                                                content.getJSONArray("records").getJSONObject(i).getString("update_time")
                                                    ));
                                                    */
                                    }

                                } else {

                                    BettingRecordActivity.WeakBettingRecord.get().finish();
                                    ToastShow.start( BettingRecordActivity.WeakBettingRecord.get(), content.getString("message"));
                                    Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                    Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);

                                }

                            } else {

                                BettingRecordActivity.WeakBettingRecord.get().finish();
                                ToastShow.start( BettingRecordActivity.WeakBettingRecord.get(), content.getString("message"));
                                Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                            Loading.diss();
                            ToastShow.start( BettingRecordActivity.WeakBettingRecord.get(), "伺服器無回應");

                        } catch (IOException e) {
                            e.printStackTrace();
                            Loading.diss();
                        }
                        if( BettingRecordActivity.WeakBettingRecord.get().BettingRecordList.size() <=0){
                            BettingRecordActivity.WeakBettingRecord.get().BettingRecordRV.setVisibility(View.GONE);
                            BettingRecordActivity.WeakBettingRecord.get().NoBettingRecord.setVisibility(View.VISIBLE);
                        }else{
                            BettingRecordActivity.WeakBettingRecord.get().BettingRecordRV.setVisibility(View.VISIBLE);
                            BettingRecordActivity.WeakBettingRecord.get().NoBettingRecord.setVisibility(View.GONE);
                        }
                        Loading.diss();
                        BettingRecordActivity.WeakBettingRecord.get().BettingRecordRV.finishRefreshing();
                        BettingRecordActivity.WeakBettingRecord.get().BettingRecordAdapter.notifyDataSetChanged();
                    }
                });


            }
        });


    }


}
