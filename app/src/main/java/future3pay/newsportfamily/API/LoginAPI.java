package future3pay.newsportfamily.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.Activity.LoginActivity;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginAPI {

    public static void Login(final String username, String password){

    OkHttpClient client=new OkHttpClient();


    //構建FormBody，傳入要提交的參數
    FormBody formBody = new FormBody
            .Builder()
            .add("username",username )
            .add("password",password )
            .build();
    Request request=new Request.Builder()
            .url(DoMainUrl.Login)
            .header("Accept","application/json")
            .post(formBody)
            .build();
    Call call =client.newCall(request);
            call.enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            LoginActivity.WeakLoginActivity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //告知使用者連線失敗
                    ToastShow.start(LoginActivity.WeakLoginActivity.get(),"無網路狀態，請檢查您的行動網路是否開啟");
                    Loading.diss();
                }
            });


        }

        @Override
        public void onResponse(Call call, final Response response) throws IOException {
            final String json =response.body().string();

            LoginActivity.WeakLoginActivity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(response.isSuccessful()){
                        try {
                            JSONObject content = new JSONObject(json);

                            if(  content.getInt("result") == 0){

                                Index.WeakIndex.get().UserInfo.edit()
                                        .putString("Account",username)
                                        .putString("Token",content.getString("message"))
                                        .apply();

                                ToastShow.start(LoginActivity.WeakLoginActivity.get(),"登入成功");
                                    LoginActivity.WeakLoginActivity.get().finish();
                            }else{

                                ToastShow.start(LoginActivity.WeakLoginActivity.get(),"帳號或密碼錯誤");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastShow.start(LoginActivity.WeakLoginActivity.get(),"伺服器忙碌中，請稍後在試");
                        }

                    }else{

                        ToastShow.start(LoginActivity.WeakLoginActivity.get(),"帳號或密碼錯誤");
                    }
                    Loading.diss();
                }
            });




        }

    });

}




}
