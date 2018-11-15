package future3pay.newsportfamily.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Activity.VerifyEmailActivity;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Fragment.MemberFragment;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReSendEmailAPI {

    public static void ReSendEmail( String token){

        OkHttpClient client=new OkHttpClient();

        Request request=new Request.Builder()
                .url(DoMainUrl.ReSendEmail)
                .header("Accept","application/json")
                .header("authorization","bearer "+token)

                .build();
        Call call =client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                VerifyEmailActivity.WeakVerifyEmail.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //告知使用者連線失敗
                        ToastShow.start(VerifyEmailActivity.WeakVerifyEmail.get(),"無網路狀態，請檢查您的行動網路是否開啟");
                        Loading.diss();

                    }

                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                VerifyEmailActivity.WeakVerifyEmail.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String json =response.body().string();
                            JSONObject content = new JSONObject(json);

                            if(response.isSuccessful()){

                                if(content.getInt("result") == 0){
                                    ToastShow.start(VerifyEmailActivity.WeakVerifyEmail.get(),"發送成功");
                                    VerifyEmailActivity.WeakVerifyEmail.get().finish();
                                }else{
                                ToastShow.start(VerifyEmailActivity.WeakVerifyEmail.get(),content.getString("message"));
                                }

                            }else{
                                ToastShow.start(VerifyEmailActivity.WeakVerifyEmail.get(),content.getString("message"));
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                            ToastShow.start(VerifyEmailActivity.WeakVerifyEmail.get(),"伺服器無回應");

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
