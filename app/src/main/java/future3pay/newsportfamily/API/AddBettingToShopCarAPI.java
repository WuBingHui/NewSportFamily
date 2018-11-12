package future3pay.newsportfamily.API;

import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import future3pay.newsportfamily.Activity.LoginActivity;
import future3pay.newsportfamily.Bean.ShopCarInfoBean;
import future3pay.newsportfamily.DoMainUrl;
import future3pay.newsportfamily.Fragment.BettingFragment;
import future3pay.newsportfamily.Fragment.GameResultFragment;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddBettingToShopCarAPI {


    public static void AddBettingToShopCar(String token, final String item,final  JSONObject ItemInfo){

        OkHttpClient client=new OkHttpClient();


        //構建FormBody，傳入要提交的參數
        FormBody formBody = new FormBody
                .Builder()
                .add("item","["+item+"]")
                .build();
        Request request=new Request.Builder()
                .url(DoMainUrl.AddBettingToShopCar)
                .header("Accept","application/json")
                .header("authorization", "bearer " + token)
                .post(formBody)
                .build();
        Call call =client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                BettingFragment.WeakBettingFragment.get().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        //告知使用者連線失敗
                        ToastShow.start(BettingFragment.WeakBettingFragment.get().getActivity(),"無網路狀態，請檢查您的行動網路是否開啟");

                    }

                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

               BettingFragment.WeakBettingFragment.get().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            String json =response.body().string();
                            JSONObject content = new JSONObject(json);
                           // Log.d("aaaaaaaaaaaaaaaaa", String.valueOf(content));
                           // Log.d("aaaaaaaaaaaaaaaaa", "["+item+"]");
                        if(response.isSuccessful()){


                                if(content.getInt("result") == 0){

                                    Index.WeakIndex.get().ShopCarInfoList.add(new ShopCarInfoBean(ItemInfo));
                                    ToastShow.start(BettingFragment.WeakBettingFragment.get().getActivity(),"加入成功");

                                }else{

                                    ToastShow.start(BettingFragment.WeakBettingFragment.get().getActivity(),content.getString("message"));

                                }


                        }else{

                            if(content.getInt("result") == 3){
                                Index.WeakIndex.get().UserInfo.edit().clear().apply();

                            }

                            ToastShow.start(BettingFragment.WeakBettingFragment.get().getActivity(),content.getString("message"));


                        }
                        } catch (JSONException e) {

                         // Log.d("aaaaaaaaaaaaaaaaa",e.toString());
                            e.printStackTrace();
                            ToastShow.start(BettingFragment.WeakBettingFragment.get().getActivity(),"伺服器無回應");

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
