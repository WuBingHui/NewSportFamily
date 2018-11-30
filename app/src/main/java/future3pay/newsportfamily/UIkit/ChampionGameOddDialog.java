package future3pay.newsportfamily.UIkit;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cy.dialog.BaseDialog;

import org.json.JSONArray;
import org.json.JSONException;

import future3pay.newsportfamily.Fragment.BettingFragment;
import future3pay.newsportfamily.R;

public class ChampionGameOddDialog {



    public static void ChampionGameOdd(final String bets, final String code, final String title,final String mins,final  String category,final  String time) {


        BettingFragment.WeakBettingFragment.get().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                final BaseDialog dialog = new BaseDialog(BettingFragment.WeakBettingFragment.get().getActivity());
                dialog.config(R.layout.game_odd_dialog, true).show();

                TextView GameType = dialog.findViewById(R.id.GameType);
                Button OddCancel = dialog.findViewById(R.id.OddCancel);
                OddCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                TextView NormalGameOddTitle = dialog.findViewById(R.id.NormalGameOddTitle);
                NormalGameOddTitle.setText(title);


                        GameType.setText(code);
                        GameType.setBackgroundResource(R.drawable.corners_8);



                LinearLayout OddInterFace = dialog.findViewById(R.id.OddInterFace);

                try {

                    JSONArray content = new JSONArray(bets);

                    HorizontalScrollView scrollView = new HorizontalScrollView(dialog.getContext());
                    LinearLayout linearLayout = new LinearLayout(dialog.getContext());
                    linearLayout.setVerticalGravity(LinearLayout.HORIZONTAL);
                    scrollView.addView(linearLayout);
                    OddInterFace.addView(scrollView);
                    for (int i = 0; i < content.length(); i++) {

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);

                        params.setMargins(0, 16, 16, 0);

                            Button odd = new Button(dialog.getContext());
                            odd.setText(content.getJSONObject(i).getString("id")+content.getJSONObject(i).getString("option-zh") + "\n" + content.getJSONObject(i).getString("odds"));
                        String Title = code +" "+ title;
                        String StartTime =time;
                        String Category = category;
                        String Mins = mins;
                        String Play = title;
                        String Select =content.getJSONObject(i).getString("id")+content.getJSONObject(i).getString("option-zh");
                        String Odd = content.getJSONObject(i).getString("odds");
                        String Item ="{\"ni\":\"" +content.getJSONObject(i).getString("ni") + "\",\"name\":\"" + content.getJSONObject(i).getString("name") + "\",\"Id\":\"" + content.getJSONObject(i).getString("ni") + "_" + content.getJSONObject(i).getString("num") + "\"}";
                        odd.setTag("{"+"\"Play\":\""+Play+"\","+"\"Code\":\""+code+"\","+"\"Home\":\""+""+"\","+"\"Away\":\""+""+"\","+"\"Title\":\""+Title+"\","+"\"StartTime\":\""+StartTime+"\","+"\"Category\":\""+Category+"\","+"\"Mins\":\""+Mins+"\","+"\"Select\":\""+Select+"\","+"\"Odd\":\""+Odd+"\","+"\"Item\":"+Item+"}");
                            odd.setBackgroundResource(R.drawable.corners_3);
                            odd.setLayoutParams(params);
                            odd.setGravity(Gravity.CENTER_HORIZONTAL);
                            odd.setOnClickListener(NormalGameOddDialog.bet);

                            if(content.getJSONObject(i).getString("opStatus").equals("active")){

                                odd.setEnabled(true);

                            }else{

                                odd.setEnabled(false);

                            }



                            if(i %3 ==0 && i!= 0 && content.length() >3){

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

                } catch (JSONException e) {
                   // Log.d("aaaaaaaaaaaaaaaaaaa",e.toString());
                    e.printStackTrace();

                }


            }

        });


    }


}
