package future3pay.newsportfamily.UIkit;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.cy.dialog.BaseDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import future3pay.newsportfamily.Fragment.BettingFragment;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;

public class NormalGameOddDialog {


    public static void NormalGameOdd(final String bets, final String code, final String mins, final String away, final String home) {


        BettingFragment.WeakBettingFragment.get().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                BaseDialog dialog = new BaseDialog(BettingFragment.WeakBettingFragment.get().getActivity());
                dialog.config(R.layout.normal_game_odd_dialog, true).show();

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
                        Drawable BtnBk = Index.WeakIndex.get().getResources().getDrawable(R.drawable.corners_3);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                                ConstraintLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 16, 16, 0);

                        for (int j = 0; j < content.getJSONObject(i).getJSONArray("codes").length(); j++) {

                            Button odd = new Button(dialog.getContext());
                            odd.setLayoutParams(params);
                            odd.setGravity(Gravity.CENTER_HORIZONTAL);
                            //odd.setBackground(BtnBk);
                            odd.setText(content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("name")+content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("outComeConditions") + "\n" + content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("odds"));

                            if(content.getJSONObject(i).getJSONArray("codes").getJSONObject(j).getString("status").equals("active")){
                                odd.setEnabled(true);
                            }else{
                                odd.setEnabled(false);
                            }
                                linearLayout.addView(odd);
                        }
                        OddInterFace.addView(scrollView);


                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                }


            }

        });


    }


}
