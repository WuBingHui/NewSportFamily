package future3pay.newsportfamily.API;

import android.content.Intent;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Activity.BettingRecordActivity;
import future3pay.newsportfamily.Activity.VerifyEmailActivity;
import future3pay.newsportfamily.Activity.VerifyPhoneActivity;
import future3pay.newsportfamily.Bean.BettingRecordBean;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import future3pay.newsportfamily.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BettingRecordAPI {

    public static void BettingRecord(final String token) {


        OkHttpClient client = new OkHttpClient();

        //get 方法帶參數進入網址
        HttpUrl.Builder urlBuilder = HttpUrl.parse(DoMainUrl.BettingRecord).newBuilder();
        urlBuilder.addQueryParameter("startDate", BettingRecordActivity.WeakBettingRecord.get().StartDate.getText().toString());
        urlBuilder.addQueryParameter("endDate",  BettingRecordActivity.WeakBettingRecord.get().EndDate.getText().toString());
        urlBuilder.addQueryParameter("result", BettingRecordActivity.WeakBettingRecord.get().Status);
        //


        Request request = new Request.Builder()
                .url(urlBuilder.build())
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
                        ToastShow.start(BettingRecordActivity.WeakBettingRecord.get(), "無網路狀態，請檢查您的行動網路是否開啟");

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
                                    if (content.getJSONObject("ordersInfo").getJSONArray("orders").length() > 0) {

                                        for(int i = 0 ;i< content.getJSONObject("ordersInfo").getJSONArray("orders").length();i++ ){
                                            BettingRecordActivity.WeakBettingRecord.get().BettingRecordList.add(new BettingRecordBean(
                                                    content.getJSONObject("ordersInfo").getString("purseTotal"),
                                                    content.getJSONObject("ordersInfo").getString("betsTotal"),
                                                    content.getJSONObject("ordersInfo").getString("subTotal"),
                                                    content.getJSONObject("ordersInfo").getJSONArray("orders").getJSONObject(i).toString()
                                            ));
                                        }
                                    }else{

                                        BettingRecordActivity.WeakBettingRecord.get().PurseTotal.setText("0");
                                        BettingRecordActivity.WeakBettingRecord.get().BetsTotal.setText("0");
                                        BettingRecordActivity.WeakBettingRecord.get().SubTotal.setText("0");
                                        BettingRecordActivity.WeakBettingRecord.get().BettingRecordRV.setVisibility(View.GONE);
                                        BettingRecordActivity.WeakBettingRecord.get().NoBettingRecord.setVisibility(View.VISIBLE);

                                    }

                                } else {

                                    //信箱未驗證
                                    if(content.getInt("result") == 4) {
                                        ToastShow.start(Index.WeakIndex.get(),"信箱尚未通過驗證");
                                        BettingRecordActivity.WeakBettingRecord.get().finish();
                                        UserInfo.add(content,token);
                                        Intent intent =new Intent();
                                        intent.setClass(Index.WeakIndex.get(),VerifyEmailActivity.class);
                                        Index.WeakIndex.get().startActivity(intent);
                                    }else{
                                        //手機未驗證
                                        if(content.getInt("result") == 5){
                                            ToastShow.start(Index.WeakIndex.get(),"手機尚未通過驗證");
                                            BettingRecordActivity.WeakBettingRecord.get().finish();
                                            UserInfo.add(content,token);

                                            Intent intent =new Intent();
                                            intent.setClass(Index.WeakIndex.get(),VerifyPhoneActivity.class);
                                            Index.WeakIndex.get().startActivity(intent);
                                        }else{
                                            if(content.getInt("result") == 3){
                                                BettingRecordActivity.WeakBettingRecord.get().finish();
                                                ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                                Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                                Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);
                                            }else{
                                                ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                            }
                                        }
                                    }
                                    ////

                                }

                            } else {

                                //信箱未驗證
                                if(content.getInt("result") == 4) {
                                    ToastShow.start(Index.WeakIndex.get(),"信箱尚未通過驗證");
                                    BettingRecordActivity.WeakBettingRecord.get().finish();
                                    UserInfo.add(content,token);
                                    Intent intent =new Intent();
                                    intent.setClass(Index.WeakIndex.get(),VerifyEmailActivity.class);
                                    Index.WeakIndex.get().startActivity(intent);
                                }else{
                                    //手機未驗證
                                    if(content.getInt("result") == 5){
                                        ToastShow.start(Index.WeakIndex.get(),"手機尚未通過驗證");
                                        BettingRecordActivity.WeakBettingRecord.get().finish();
                                        UserInfo.add(content,token);

                                        Intent intent =new Intent();
                                        intent.setClass(Index.WeakIndex.get(),VerifyPhoneActivity.class);
                                        Index.WeakIndex.get().startActivity(intent);
                                    }else{
                                        if(content.getInt("result") == 3){
                                            BettingRecordActivity.WeakBettingRecord.get().finish();
                                            ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                            Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                            Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);
                                        }else{
                                            ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                        }
                                    }
                                }
                                ////

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                            Loading.diss();
                            ToastShow.start(BettingRecordActivity.WeakBettingRecord.get(), "伺服器無回應");

                        } catch (IOException e) {
                            e.printStackTrace();
                            Loading.diss();
                        }

                        if (BettingRecordActivity.WeakBettingRecord.get().BettingRecordList.size() <= 0) {
                            BettingRecordActivity.WeakBettingRecord.get().BettingRecordRV.setVisibility(View.GONE);
                            BettingRecordActivity.WeakBettingRecord.get().NoBettingRecord.setVisibility(View.VISIBLE);
                        } else {
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
