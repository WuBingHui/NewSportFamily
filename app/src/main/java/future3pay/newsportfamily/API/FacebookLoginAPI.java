package future3pay.newsportfamily.API;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Activity.LoginActivity;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.FacebookLogin;
import future3pay.newsportfamily.GoogleLogin;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FacebookLoginAPI {


    public static void FacebookLogin(final String token){

        OkHttpClient client=new OkHttpClient();


        //構建FormBody，傳入要提交的參數
        FormBody formBody = new FormBody
                .Builder()
                .add("access_token",token)
                .build();
        Request request=new Request.Builder()
                .url(DoMainUrl.FacebookLogin)
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
                        FacebookLogin.signOut();
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
                        try {
                            JSONObject content = new JSONObject(json);

                        if(response.isSuccessful()){

                                if( content.getInt("result") == 0){

                                    UserInfoAPI.UserInfo(content.getString("message"));

                                    ToastShow.start(LoginActivity.WeakLoginActivity.get(),"登入成功");

                                    LoginActivity.WeakLoginActivity.get().finish();

                                }else{


                                    ToastShow.start(LoginActivity.WeakLoginActivity.get(),"登入驗證錯誤");

                                    FacebookLogin.signOut();
                                }



                        }else{

                            ToastShow.start(LoginActivity.WeakLoginActivity.get(),"登入驗證錯誤");
                            FacebookLogin.signOut();
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                        ToastShow.start(LoginActivity.WeakLoginActivity.get(),"伺服器忙碌中，請稍後在試");
                            FacebookLogin.signOut();
                    }

                        Loading.diss();
                    }
                });




            }

        });

    }
}
