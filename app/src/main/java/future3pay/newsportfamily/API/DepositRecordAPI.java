package future3pay.newsportfamily.API;

import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Activity.MakeUpPointRecordActivity;
import future3pay.newsportfamily.Activity.UseRecordActivity;
import future3pay.newsportfamily.Bean.DepositRecordBean;
import future3pay.newsportfamily.Bean.UseRecordBean;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DepositRecordAPI {

    public static void UseRecord(final String token) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(DoMainUrl.DepositRecord)
                .header("Accept", "application/json")
                .header("authorization", "bearer " + token)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                MakeUpPointRecordActivity.WeakMakeUpPointRecord.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Loading.diss();
                        //告知使用者連線失敗
                        ToastShow.start(MakeUpPointRecordActivity.WeakMakeUpPointRecord.get(), "無網路狀態，請檢查您的行動網路是否開啟");

                    }

                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                MakeUpPointRecordActivity.WeakMakeUpPointRecord.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String json = response.body().string();
                            JSONObject content = new JSONObject(json);

                            if (response.isSuccessful()) {

                                if (content.getInt("result") == 0) {
                                    MakeUpPointRecordActivity.WeakMakeUpPointRecord.get().DepositRecordList.clear();
                                    for(int i =0 ; i<content.getJSONArray("records").length();i++){

                                        MakeUpPointRecordActivity.WeakMakeUpPointRecord.get().DepositRecordList.add(new DepositRecordBean(
                                                    content.getJSONArray("records").getJSONObject(i).getString("reason"),
                                                    content.getJSONArray("records").getJSONObject(i).getString("origin_points"),
                                                    content.getJSONArray("records").getJSONObject(i).getString("won_points"),
                                                            content.getJSONArray("records").getJSONObject(i).getString("record_time")
                                                    ));
                                    }

                                } else {

                                    MakeUpPointRecordActivity.WeakMakeUpPointRecord.get().finish();
                                    ToastShow.start(Index.WeakIndex.get(), content.getString("message"));
                                    Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                    Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);

                                }

                            } else {

                                MakeUpPointRecordActivity.WeakMakeUpPointRecord.get().finish();
                                ToastShow.start(Index.WeakIndex.get(), content.getString("message"));
                                Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                            Loading.diss();
                            ToastShow.start(MakeUpPointRecordActivity.WeakMakeUpPointRecord.get(), "伺服器無回應");

                        } catch (IOException e) {
                            e.printStackTrace();
                            Loading.diss();
                        }
                        if(MakeUpPointRecordActivity.WeakMakeUpPointRecord.get().DepositRecordList.size() <=0){
                            MakeUpPointRecordActivity.WeakMakeUpPointRecord.get().DepositRecordRV.setVisibility(View.GONE);
                            MakeUpPointRecordActivity.WeakMakeUpPointRecord.get().NoDepositRecord.setVisibility(View.VISIBLE);
                        }else{
                            MakeUpPointRecordActivity.WeakMakeUpPointRecord.get().DepositRecordRV.setVisibility(View.VISIBLE);
                            MakeUpPointRecordActivity.WeakMakeUpPointRecord.get().NoDepositRecord.setVisibility(View.GONE);
                        }
                        Loading.diss();
                        MakeUpPointRecordActivity.WeakMakeUpPointRecord.get().DepositRecordRV.finishRefreshing();
                        MakeUpPointRecordActivity.WeakMakeUpPointRecord.get().DepositRecordAdapter.notifyDataSetChanged();
                    }
                });


            }
        });


    }


}
