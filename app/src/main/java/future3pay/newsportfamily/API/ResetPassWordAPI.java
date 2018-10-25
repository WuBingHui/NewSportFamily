package future3pay.newsportfamily.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.Activity.LoginActivity;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResetPassWordAPI {

    public static void ResetPassWord(String email){



        OkHttpClient client=new OkHttpClient();


        //構建FormBody，傳入要提交的參數
        FormBody formBody = new FormBody
                .Builder()
                .add("email", email)
                .build();
        Request request=new Request.Builder()
                .url(DoMainUrl.ResetPassWord)
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
                        ToastShow.start(LoginActivity.WeakLoginActivity.get().ResetPassWordDialog.getContext(),"無網路狀態，請檢查您的行動網路是否開啟");

                        Loading.diss();
                    }

                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                LoginActivity.WeakLoginActivity.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject content =null;
                        String message ="";
                        try {
                            String json =response.body().string();
                             content = new JSONObject(json);
                            message = content.getString("message");
                        if(response.isSuccessful()){
                                if(content.getInt("result") == 0){
                                    ToastShow.start(LoginActivity.WeakLoginActivity.get().ResetPassWordDialog.getContext(),content.getString("message"));
                                    LoginActivity.WeakLoginActivity.get().ResetPassWordDialog.dismiss();
                                }else{

                                    ToastShow.start(LoginActivity.WeakLoginActivity.get().ResetPassWordDialog.getContext(),content.getString("message"));

                                }

                        }else{

                            if(!content.getJSONObject("message").isNull("email")){

                                String str = "";

                                for(int i=0;i<content.getJSONObject("message").getJSONArray("email").length();i++){
                                    str = str+ content.getJSONObject("message").getJSONArray("email").getString(i) + "\n";
                                }

                                ToastShow.start(LoginActivity.WeakLoginActivity.get().ResetPassWordDialog.getContext(),str);
                            }else{
                                ToastShow.start(LoginActivity.WeakLoginActivity.get().ResetPassWordDialog.getContext(),"未知錯誤，請稍後在試");
                            }

                        }
                        } catch (JSONException e) {

                            e.printStackTrace();

                            ToastShow.start(LoginActivity.WeakLoginActivity.get().ResetPassWordDialog.getContext(),message);

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
