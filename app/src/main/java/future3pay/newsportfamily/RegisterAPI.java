package future3pay.newsportfamily;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterAPI {


    public static void RegisterApi(String username,String password,String password_confirmation,String name,String email,String phone,String bank_name,String bank_account,String validators_code,String invite_code){


        OkHttpClient client=new OkHttpClient();


        //構建FormBody，傳入要提交的參數
        FormBody formBody = new FormBody
                .Builder()
                .add("username", username)
                .add("password", password)
                .add("password_confirmation", password_confirmation)
                .add("name", name)
                .add("email", email)
                .add("phone", phone)
                .add("bank_name", bank_name)
                .add("bank_account", bank_account)
                .add("validators_code", validators_code)
                .add("invite_code", invite_code)
                .build();
        Request request=new Request.Builder()
                .url(DoMainUrl.Register)
                .header("Accept","application/json")
                .post(formBody)
                .build();
        Call call =client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                //告知使用者連線失敗
                RegisterActivity.WeakRegisterActivity.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ToastShow.start(RegisterActivity.WeakRegisterActivity.get(),"無網路狀態，請檢查您的行動網路是否開啟");
                        Loading.diss();
                    }

                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                RegisterActivity.WeakRegisterActivity.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                            try {
                                String json =response.body().string();
                                JSONObject content = new JSONObject(json);
                                if(content.getInt("result") == 0){

                                    Toast.makeText(RegisterActivity.WeakRegisterActivity.get(),content.getString("message"),Toast.LENGTH_SHORT).show();
                                    Loading.diss();
                                    RegisterActivity.WeakRegisterActivity.get().finish();
                                }else{


                                    String message ="";
                                    if(!content.getJSONObject("message").isNull("username")){
                                        for(int i=0;i< content.getJSONObject("message").getJSONArray("username").length();i++){

                                            message= message+content.getJSONObject("message").getJSONArray("username").getString(i)+"\n";
                                        }
                                        RegisterActivity.WeakRegisterActivity.get().Account.setText("");
                                    }

                                    if(!content.getJSONObject("message").isNull("password")){
                                        for(int i=0;i< content.getJSONObject("message").getJSONArray("password").length();i++){
                                            message= message+content.getJSONObject("message").getJSONArray("password").getString(i)+"\n";
                                        }
                                        RegisterActivity.WeakRegisterActivity.get().PassWord.setText("");
                                    }

                                    if(!content.getJSONObject("message").isNull("name")){
                                        for(int i=0;i< content.getJSONObject("message").getJSONArray("name").length();i++){
                                            message= message+content.getJSONObject("message").getJSONArray("name").getString(i)+"\n";
                                        }
                                        RegisterActivity.WeakRegisterActivity.get().UserName.setText("");
                                    }

                                    if(!content.getJSONObject("message").isNull("email")){
                                        for(int i=0;i< content.getJSONObject("message").getJSONArray("email").length();i++){
                                            message= message+content.getJSONObject("message").getJSONArray("email").getString(i)+"\n";
                                        }
                                        RegisterActivity.WeakRegisterActivity.get().Email.setText("");
                                    }

                                    if(!content.getJSONObject("message").isNull("bank_name")){
                                        for(int i=0;i< content.getJSONObject("message").getJSONArray("bank_name").length();i++){
                                            message= message+content.getJSONObject("message").getJSONArray("bank_name").getString(i)+"\n";
                                        }

                                    }

                                    if(!content.getJSONObject("message").isNull("bank_account")){
                                        for(int i=0;i< content.getJSONObject("message").getJSONArray("bank_account").length();i++){
                                            message= message+content.getJSONObject("message").getJSONArray("bank_account").getString(i)+"\n";
                                        }
                                        RegisterActivity.WeakRegisterActivity.get().BankAccount.setText("");
                                    }

                                    if(!content.getJSONObject("message").isNull("phone")){
                                        for(int i=0;i< content.getJSONObject("message").getJSONArray("phone").length();i++){
                                            message= message+content.getJSONObject("message").getJSONArray("phone").getString(i)+"\n";
                                        }
                                        RegisterActivity.WeakRegisterActivity.get().Phone.setText("");
                                    }
                                    if(!content.getJSONObject("message").isNull("validators_code")){

                                        for(int i=0;i< content.getJSONObject("message").getJSONArray("validators_code").length();i++){
                                            message= message+content.getJSONObject("message").getJSONArray("validators_code").getString(i)+"\n";
                                        }
                                        RegisterActivity.WeakRegisterActivity.get().VerifyCode.setText("");
                                    }

                                    ToastShow.start(RegisterActivity.WeakRegisterActivity.get(),message);
                                    Loading.diss();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();

                                ToastShow.start(RegisterActivity.WeakRegisterActivity.get(),"伺服器斷線");
                                Loading.diss();

                            } catch (IOException e) {

                                ToastShow.start(RegisterActivity.WeakRegisterActivity.get(),"伺服器斷線");
                                Loading.diss();
                                e.printStackTrace();
                            }

                    }
                });




            }
        });


    }


}

