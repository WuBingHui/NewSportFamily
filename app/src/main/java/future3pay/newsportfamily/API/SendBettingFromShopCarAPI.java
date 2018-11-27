package future3pay.newsportfamily.API;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cy.dialog.BaseDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Activity.CheckShopCarActivity;
import future3pay.newsportfamily.Activity.VerifyEmailActivity;
import future3pay.newsportfamily.Activity.VerifyPhoneActivity;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import future3pay.newsportfamily.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SendBettingFromShopCarAPI {


    public static void SendBettingFromShopCar(final String token){

        OkHttpClient client=new OkHttpClient();



        //構建FormBody，傳入要提交的參數
        FormBody formBody = new FormBody
                .Builder()
                .build();
        Request request=new Request.Builder()
                .url(DoMainUrl.SendBettingFromShopCar)
                .header("Accept","application/json")
                .header("authorization", "bearer " + token)
                .post(formBody)
                .build();
        Call call =client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


               Index.WeakIndex.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Loading.diss();
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
                                    CheckShopCarActivity.WeakCheckShopCar.get().finish();
                                    Index.WeakIndex.get().ShopCarInfoList.clear();
                                    //ToastShow.start(Index.WeakIndex.get(),"投注訂單已送出");
                                    Index.WeakIndex.get().ShopDialog.dismiss();//購物車銷毀
                                    final BaseDialog  CompeletDialog = new BaseDialog(Index.WeakIndex.get());
                                    CompeletDialog.config(R.layout.compelet_order, true).show();
                                  TextView CompeletOrderNo = CompeletDialog.findViewById(R.id.CompeletOrderNo);
                                    CompeletOrderNo.setText("#"+content.getString("message"));
                                  Button CompeletOrderBack = CompeletDialog.findViewById(R.id.CompeletOrderBack);
                                    CompeletOrderBack.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            CompeletDialog.dismiss();
                                        }
                                    });



                                    UserInfoAPI.UserInfo(Index.WeakIndex.get().UserInfo.getString("Token",""));


                                }else{

                                    //信箱未驗證
                                    if(content.getInt("result") == 4) {
                                        ToastShow.start(Index.WeakIndex.get(),"信箱尚未通過驗證");
                                        UserInfo.add(content,token);
                                        Intent intent =new Intent();
                                        intent.setClass(Index.WeakIndex.get(),VerifyEmailActivity.class);
                                        Index.WeakIndex.get().startActivity(intent);
                                    }else{
                                        //手機未驗證
                                        if(content.getInt("result") == 5){
                                            ToastShow.start(Index.WeakIndex.get(),"手機尚未通過驗證");
                                            UserInfo.add(content,token);
                                            Intent intent =new Intent();
                                            intent.setClass(Index.WeakIndex.get(),VerifyPhoneActivity.class);
                                            Index.WeakIndex.get().startActivity(intent);
                                        }else{
                                            if(content.getInt("result") == 3){
                                                Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                                ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                            }else{
                                                ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                            }
                                        }
                                    }
                                    ////

                                    Index.WeakIndex.get().ShopCarInfoList.clear();
                                }


                            }else{

                                //信箱未驗證
                                if(content.getInt("result") == 4) {
                                    ToastShow.start(Index.WeakIndex.get(),"信箱尚未通過驗證");
                                    UserInfo.add(content,token);
                                    Intent intent =new Intent();
                                    intent.setClass(Index.WeakIndex.get(),VerifyEmailActivity.class);
                                    Index.WeakIndex.get().startActivity(intent);
                                }else{
                                    //手機未驗證
                                    if(content.getInt("result") == 5){
                                        ToastShow.start(Index.WeakIndex.get(),"手機尚未通過驗證");
                                        UserInfo.add(content,token);
                                        Intent intent =new Intent();
                                        intent.setClass(Index.WeakIndex.get(),VerifyPhoneActivity.class);
                                        Index.WeakIndex.get().startActivity(intent);
                                    }else{
                                        if(content.getInt("result") == 3){
                                            Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                            ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                        }else{
                                            ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                        }
                                    }
                                }
                                ////

                                Index.WeakIndex.get().ShopCarInfoList.clear();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                            ToastShow.start(Index.WeakIndex.get(),"伺服器無回應");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Loading.diss();
                        Index.WeakIndex.get().ShopCarAdapter.notifyDataSetChanged();
                    }
                });


            }
        });




    }


}
