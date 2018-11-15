package future3pay.newsportfamily.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Activity.VerifyEmailActivity;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Activity.RegisterActivity;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SmsAPI  {


    public static void SmsApi(String phone){



        OkHttpClient client=new OkHttpClient();


            //構建FormBody，傳入要提交的參數
            FormBody formBody = new FormBody
                    .Builder()
                    .add("phone", phone)
                    .build();
            Request request=new Request.Builder()
                    .url(DoMainUrl.PhoneSms)
                    .header("Accept","application/json")
                    .post(formBody)
                    .build();
            Call call =client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {


                    RegisterActivity.WeakRegisterActivity.get().runOnUiThread(new Runnable() {
                       @Override
                        public void run() {

                           //告知使用者連線失敗
                           ToastShow.start(RegisterActivity.WeakRegisterActivity.get(),"無網路狀態，請檢查您的行動網路是否開啟");

                                }

                    });


                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    RegisterActivity.WeakRegisterActivity.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(response.isSuccessful()){

                                try {
                                    String json =response.body().string();
                                    JSONObject content = new JSONObject(json);

                                    if(content.getInt("result") == 0){

                                        ToastShow.start(RegisterActivity.WeakRegisterActivity.get(),"發送成功");

                                    }else{

                                        ToastShow.start(RegisterActivity.WeakRegisterActivity.get(),"發送失敗");
                                    }
                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    ToastShow.start(RegisterActivity.WeakRegisterActivity.get(),"伺服器斷線");

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else{

                                ToastShow.start(RegisterActivity.WeakRegisterActivity.get(),"發送失敗");

                            }
                        }
                    });


                }
            });




    }




}
