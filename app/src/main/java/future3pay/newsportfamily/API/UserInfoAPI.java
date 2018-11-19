package future3pay.newsportfamily.API;

import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.gms.auth.api.signin.GoogleSignIn;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Activity.VerifyEmailActivity;
import future3pay.newsportfamily.Activity.VerifyPhoneActivity;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.FacebookLogin;
import future3pay.newsportfamily.Fragment.GameResultFragment;
import future3pay.newsportfamily.Fragment.MemberFragment;
import future3pay.newsportfamily.GoogleLogin;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import future3pay.newsportfamily.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserInfoAPI {

    public static void UserInfo(final String token){

        OkHttpClient client=new OkHttpClient();

        Request request=new Request.Builder()
                .url(DoMainUrl.Info)
                .header("Accept","application/json")
                .header("authorization","bearer "+token)

                .build();
        Call call =client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                Index.WeakIndex.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

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

                                    UserInfo.add(content,token);

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
                                                ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                                Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                                Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);
                                                GoogleLogin.signOut();
                                                GoogleLogin.revokeAccess();
                                                FacebookLogin.signOut();
                                            }else{
                                                ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                            }
                                        }
                                    }
                                    ////

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
                                        ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                        Index.WeakIndex.get().UserInfo.edit().clear().apply();
                                        Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);
                                        GoogleLogin.signOut();
                                        GoogleLogin.revokeAccess();
                                        FacebookLogin.signOut();
                                    }else{
                                        ToastShow.start(Index.WeakIndex.get(),content.getString("message"));
                                    }
                                }
                            }
                            ////

                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                        ToastShow.start(Index.WeakIndex.get(),"伺服器無回應");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    }
                });


            }
        });




    }





}
