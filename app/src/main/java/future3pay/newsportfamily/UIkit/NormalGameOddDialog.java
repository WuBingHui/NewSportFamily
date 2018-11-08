package future3pay.newsportfamily.UIkit;

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

import future3pay.newsportfamily.API.AddBettingToShopCarAPI;
import future3pay.newsportfamily.Fragment.BettingFragment;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;

public class NormalGameOddDialog {


    public static void NormalGameOdd(final String bets, final String code, final String mins, final String away, final String home , final String ni) {


        BettingFragment.WeakBettingFragment.get().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                BaseDialog dialog = new BaseDialog(BettingFragment.WeakBettingFragment.get().getActivity());
                dialog.config(R.layout.game_odd_dialog, true).show();

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

                try {

                    JSONArray content = new JSONArray(bets);

                    for (int i = 0; i < content.length(); i++) {
                        TextView PlayMethod = new TextView(dialog.getContext());
                        PlayMethod.setText(content.getJSONObject(i).getJSONObject("betsTitle").getString("title"));
                        PlayMethod.setTextSize(18);
                        OddInterFace.addView(PlayMethod);

                        HorizontalScrollView scrollView = new HorizontalScrollView(dialog.getContext());
                        LinearLayout linearLayout = new LinearLayout(dialog.getContext());
                        linearLayout.setVerticalGravity(LinearLayout.HORIZONTAL);
                        scrollView.addView(linearLayout);
                        OddInterFace.addView(scrollView);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);

                        params.setMargins(0, 16, 16, 0);

                        for (int j = 0; j < content.getJSONObject(i).getJSONArray("codes").length(); j++) {



                            Button odd = new Button(dialog.getContext());
                            odd.setText(content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("name")+content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("outComeConditions") + "\n" + content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("odds"));
                            odd.setTag("{\"ni\":\"" +ni + "\",\"name\":\"" + content.getJSONObject(i).getJSONObject("betsTitle").getString("titleCode") + "\",\"Id\":\"" + ni + "_" + content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("id") + "\"}");
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
                    Loading.start(BettingFragment.WeakBettingFragment.get().getActivity());
                    AddBettingToShopCarAPI.AddBettingToShopCar(Index.WeakIndex.get().UserInfo.getString("Token",""),String.valueOf(view.getTag()));
                }else{
                    ToastShow.start(Index.WeakIndex.get(),"投注最多8筆!");
                }
            }else{
                ToastShow.start(Index.WeakIndex.get(),"尚未登入");
            }



        }

    };

}
