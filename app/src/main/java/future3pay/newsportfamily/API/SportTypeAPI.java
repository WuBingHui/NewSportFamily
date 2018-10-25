package future3pay.newsportfamily.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SportTypeAPI {

    public static void SportType(){



        OkHttpClient client=new OkHttpClient();


        //構建FormBody，傳入要提交的參數
        FormBody formBody = new FormBody
                .Builder()
                .build();
        Request request=new Request.Builder()
                .url(DoMainUrl.SportsType)
                .header("Accept","application/json")
                .post(formBody)
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
                        if(response.isSuccessful()){

                            try {
                                String json =response.body().string();
                                JSONObject content = new JSONObject(json);

                                if(content.getInt("result") == 0){
                                    Index.WeakIndex.get().SporeName.clear();
                                    Index.WeakIndex.get().SporeType.clear();

                                    for(int i=0 ;i<content.getJSONArray("sportsType").length();i++){
                                        Index.WeakIndex.get().SporeName.add(content.getJSONArray("sportsType").getJSONObject(i).getString("name"));
                                        Index.WeakIndex.get().SporeType.add(content.getJSONArray("sportsType").getJSONObject(i).getString("type"));
                                     //   Log.d("aaaaaaaaaaaaaa",content.getJSONArray("sportsType").getJSONObject(i).getString("name")+"    "+content.getJSONArray("sportsType").getJSONObject(i).getString("type"));
                                    }

                                }else{

                                    ToastShow.start(Index.WeakIndex.get(),"獲取賽事球種失敗");

                                }
                            } catch (JSONException e) {

                                e.printStackTrace();
                                ToastShow.start(Index.WeakIndex.get(),"伺服器無回應");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{

                            ToastShow.start(Index.WeakIndex.get(),"獲取賽事球種失敗");

                        }
                    }
                });


            }
        });




    }


}
