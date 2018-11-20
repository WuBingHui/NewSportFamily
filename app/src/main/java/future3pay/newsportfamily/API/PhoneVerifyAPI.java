package future3pay.newsportfamily.API;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Activity.VerifyEmailActivity;
import future3pay.newsportfamily.Activity.VerifyPhoneActivity;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PhoneVerifyAPI {

    public static void PhoneVerify(final String token,final String phone,final String validatorsCode){

        OkHttpClient client=new OkHttpClient();
        //構建FormBody，傳入要提交的參數
        FormBody formBody = new FormBody
                .Builder()
                .add("phone",phone)
                .add("validatorsCode",validatorsCode)
                .build();
        Request request=new Request.Builder()
                .url(DoMainUrl.PhoneVerify)
                .header("Accept","application/json")
                .header("authorization","bearer "+token)
                .post(formBody)
                .build();
        Call call =client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                VerifyPhoneActivity.WeakVerifyPhone.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //告知使用者連線失敗
                        ToastShow.start(VerifyPhoneActivity.WeakVerifyPhone.get(),"無網路狀態，請檢查您的行動網路是否開啟");
                        Loading.diss();

                    }

                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                VerifyPhoneActivity.WeakVerifyPhone.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            String json =response.body().string();
                            JSONObject content = new JSONObject(json);

                            if(response.isSuccessful()){

                                if(content.getInt("result") == 0){

                                    ToastShow.start(VerifyPhoneActivity.WeakVerifyPhone.get(),"驗證成功");
                                    VerifyPhoneActivity.WeakVerifyPhone.get().finish();

                                }else{

                                    if(content.getInt("result") == 3){
                                        VerifyPhoneActivity.WeakVerifyPhone.get().finish();
                                        ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                        Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                        Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);
                                    }else{
                                        ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                    }

                                }

                            }else{

                                if(content.getInt("result") == 3){
                                    VerifyPhoneActivity.WeakVerifyPhone.get().finish();
                                    ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                    Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                    Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);
                                }else{
                                    ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                }

                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                            ToastShow.start(VerifyPhoneActivity.WeakVerifyPhone.get(),"伺服器無回應");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Loading.diss();
                    }
                });


            }
        });




    }
}
