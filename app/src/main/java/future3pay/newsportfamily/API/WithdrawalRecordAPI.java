package future3pay.newsportfamily.API;

import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Activity.MakeUpPointRecordActivity;
import future3pay.newsportfamily.Activity.UseRecordActivity;
import future3pay.newsportfamily.Activity.WithdrawalRecordActivity;
import future3pay.newsportfamily.Bean.DepositRecordBean;
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

public class WithdrawalRecordAPI {

    public static void UseRecord(final String token) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(DoMainUrl.DrawRecord)
                .header("Accept", "application/json")
                .header("authorization", "bearer " + token)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                WithdrawalRecordActivity.WeakWithdrawalRecord.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Loading.diss();
                        //告知使用者連線失敗
                        ToastShow.start(WithdrawalRecordActivity.WeakWithdrawalRecord.get(), "無網路狀態，請檢查您的行動網路是否開啟");

                    }

                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                WithdrawalRecordActivity.WeakWithdrawalRecord.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String json = response.body().string();
                            JSONObject content = new JSONObject(json);

                            if (response.isSuccessful()) {

                                if (content.getInt("result") == 0) {
                                    WithdrawalRecordActivity.WeakWithdrawalRecord.get().WithdrawalRecordList.clear();
                                    for(int i =0 ; i<content.getJSONArray("records").length();i++){

                                        WithdrawalRecordActivity.WeakWithdrawalRecord.get().WithdrawalRecordList.add(new WithdrawalRecordBean(
                                                    content.getJSONArray("records").getJSONObject(i).getString("amount"),
                                                    content.getJSONArray("records").getJSONObject(i).getString("status"),
                                                    content.getJSONArray("records").getJSONObject(i).getString("bank_info"),
                                                            content.getJSONArray("records").getJSONObject(i).getString("create_time"),
                                                content.getJSONArray("records").getJSONObject(i).getString("update_time")
                                                    ));
                                    }

                                } else {

                                    WithdrawalRecordActivity.WeakWithdrawalRecord.get().finish();
                                    ToastShow.start(WithdrawalRecordActivity.WeakWithdrawalRecord.get(), content.getString("message"));
                                    Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                    Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);

                                }

                            } else {

                                WithdrawalRecordActivity.WeakWithdrawalRecord.get().finish();
                                ToastShow.start(WithdrawalRecordActivity.WeakWithdrawalRecord.get(), content.getString("message"));
                                Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                            Loading.diss();
                            ToastShow.start(WithdrawalRecordActivity.WeakWithdrawalRecord.get(), "伺服器無回應");

                        } catch (IOException e) {
                            e.printStackTrace();
                            Loading.diss();
                        }
                        if(WithdrawalRecordActivity.WeakWithdrawalRecord.get().WithdrawalRecordList.size() <=0){
                            WithdrawalRecordActivity.WeakWithdrawalRecord.get().WithdrawalRecordRV.setVisibility(View.GONE);
                            WithdrawalRecordActivity.WeakWithdrawalRecord.get().NoWithdrawalRecord.setVisibility(View.VISIBLE);
                        }else{
                            WithdrawalRecordActivity.WeakWithdrawalRecord.get().WithdrawalRecordRV.setVisibility(View.VISIBLE);
                            WithdrawalRecordActivity.WeakWithdrawalRecord.get().NoWithdrawalRecord.setVisibility(View.GONE);
                        }
                        Loading.diss();
                        WithdrawalRecordActivity.WeakWithdrawalRecord.get().WithdrawalRecordRV.finishRefreshing();
                        WithdrawalRecordActivity.WeakWithdrawalRecord.get().WithdrawalRecordAdapter.notifyDataSetChanged();
                    }
                });


            }
        });


    }


}
