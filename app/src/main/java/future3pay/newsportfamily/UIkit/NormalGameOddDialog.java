package future3pay.newsportfamily.UIkit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cy.dialog.BaseDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import future3pay.newsportfamily.API.AddBettingToShopCarAPI;
import future3pay.newsportfamily.Fragment.BettingCountDownFragment;
import future3pay.newsportfamily.Fragment.BettingFragment;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;

public class NormalGameOddDialog {

    private static BaseDialog dialog;
    public static void NormalGameOdd(final String bets, final String code, final String mins, final String away, final String home , final String ni,final  String category,final String time) {


        Index.WeakIndex.get().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(dialog== null){
                    dialog = new BaseDialog(Index.WeakIndex.get());
                    dialog.config(R.layout.game_odd_dialog, true).show();
                    dialog.setOnCancelListener(cancel);
                }

                TextView GameType = dialog.findViewById(R.id.GameType);

                TextView NormalGameOddTitle = dialog.findViewById(R.id.NormalGameOddTitle);
                NormalGameOddTitle.setText(code + " " + away + " VS " + home);

                switch (mins) {
                    case "1":
                        GameType.setText("單場");
                        GameType.setBackgroundResource(R.drawable.corners_7);
                        break;
                    case "2":
                        GameType.setText("一般賽事");
                        GameType.setBackgroundResource(R.drawable.corners_6);
                        break;
                    case "3":
                        GameType.setText("一般賽事");
                        GameType.setBackgroundResource(R.drawable.corners_6);
                        break;
                }


                LinearLayout OddInterFace = dialog.findViewById(R.id.OddInterFace);
                OddInterFace.removeAllViews();
                try {

                    JSONArray content = new JSONArray(bets);

                    for (int i = 0; i < content.length(); i++) {
                        if(content.getJSONObject(i).getString("sts").equals("active")){
                            TextView PlayMethod = new TextView(dialog.getContext());
                            PlayMethod.setText(content.getJSONObject(i).getString("mins") +" "+ content.getJSONObject(i).getJSONObject("betsTitle").getString("title"));
                            PlayMethod.setTextSize(18);
                            OddInterFace.addView(PlayMethod);

                            HorizontalScrollView scrollView = new HorizontalScrollView(dialog.getContext());
                            LinearLayout linearLayout = new LinearLayout(dialog.getContext());
                            scrollView.removeAllViews();
                            linearLayout.removeAllViews();
                            linearLayout.setVerticalGravity(LinearLayout.HORIZONTAL);
                            scrollView.addView(linearLayout);
                            OddInterFace.addView(scrollView);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);

                            params.setMargins(0, 16, 16, 0);

                            for (int j = 0; j < content.getJSONObject(i).getJSONArray("codes").length(); j++) {

                                Button odd = new Button(dialog.getContext());
                                odd.setText(content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("name")+content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("outComeConditions") + "\n" + content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("odds"));
                                String Title = code + " " + away + "VS" + home;
                                String StartTime =time;
                                String Category = category;
                                String Mins = content.getJSONObject(i).getString("mins");
                                String Play = content.getJSONObject(i).getJSONObject("betsTitle").getString("title");
                                String Select =content.getJSONObject(i).getJSONObject("betsTitle").getString("title")+" "+ content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("name")+content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("outComeConditions");
                                String Odd = content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("odds");
                                String Item ="{\"ni\":\"" +ni + "\",\"name\":\"" + content.getJSONObject(i).getJSONObject("betsTitle").getString("titleCode") + "\",\"Id\":\"" + ni + "_" + content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("id") + "\"}";
                                odd.setTag("{"+"\"Play\":\""+Play+"\","+"\"Code\":\""+code+"\","+"\"Home\":\""+home+"\","+"\"Away\":\""+away+"\","+"\"Title\":\""+Title+"\","+"\"StartTime\":\""+StartTime+"\","+"\"Category\":\""+Category+"\","+"\"Mins\":\""+Mins+"\","+"\"Select\":\""+Select+"\","+"\"Odd\":\""+Odd+"\","+"\"Item\":"+Item+"}");
                                odd.setBackgroundResource(R.drawable.corners_3);
                                odd.setLayoutParams(params);
                                odd.setGravity(Gravity.CENTER_HORIZONTAL);
                                odd.setOnClickListener(bet);
                                if(content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("status").equals("active")){

                                    odd.setEnabled(true);

                                }else{

                                    odd.setEnabled(false);

                                }


                                if(j %3 ==0 && j!= 0 && content.getJSONObject(i).getJSONArray("codes").length() >3){

                                    scrollView = new HorizontalScrollView(dialog.getContext());
                                    linearLayout = new LinearLayout(dialog.getContext());
                                    linearLayout.setVerticalGravity(LinearLayout.HORIZONTAL);
                                    scrollView.addView(linearLayout);
                                    OddInterFace.addView(scrollView);
                                    linearLayout.addView(odd);

                                }else{

                                    linearLayout.addView(odd);

                                }





                            }
                        }


                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                }


            }

        });


    }


    public static Button.OnClickListener bet = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {

            if(!Index.WeakIndex.get().UserInfo.getString("Token","").equals("")){
                if(Index.WeakIndex.get().ShopCarInfoList.size() <= 8){

                    try {
                        JSONObject content = new JSONObject(view.getTag().toString());
                        Loading.start(Index.WeakIndex.get());
                        AddBettingToShopCarAPI.AddBettingToShopCar(Index.WeakIndex.get().UserInfo.getString("Token",""),String.valueOf(content.getString("Item")),content);

                    } catch (JSONException e) {
                       // Log.d("aaaaaaaaaaaaaaaaaaaaaa",e.toString());
                        e.printStackTrace();
                    }

                }else{
                    ToastShow.start(Index.WeakIndex.get(),"投注最多8筆!");
                }
            }else{
                ToastShow.start(Index.WeakIndex.get(),"尚未登入");
            }

        }

    };

    //dialog把釋放
    private static AlertDialog.OnCancelListener cancel = new AlertDialog.OnCancelListener(){

        @Override
        public void onCancel(DialogInterface dialogInterface) {

            dialog = null;

            if(BettingCountDownFragment.WeakBettingCountDown != null && BettingCountDownFragment.WeakBettingCountDown.get().scheduledThreadPool3 != null){

                BettingCountDownFragment.WeakBettingCountDown.get().scheduledThreadPool3.shutdownNow();

            }

        }

    };
}
