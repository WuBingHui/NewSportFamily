package future3pay.newsportfamily;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.json.JSONException;
import org.json.JSONObject;

public class BettingRule {
    private static int sum = 0;
    private static int count = 0;

    //單場最高可得金額計算
    public static int Single(int payout) {
        Index.WeakIndex.get().ComboTypeSelect.removeAllViews();
        try {
            int sum = 0;

            for (int i = 0; i < Index.WeakIndex.get().ShopCarInfoList.size(); i++) {

                sum = sum + (int) Math.round(Double.valueOf(Index.WeakIndex.get().ShopCarInfoList.get(i).getItem().getString("Odd")) * 10 * payout);

            }
            return sum;
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return 0;

    }


    //過關最高可得金額計算
    public static int Passing(int payout) {
        Index.WeakIndex.get().ComboTypeSelect.removeAllViews();
        Double odd = 1.0;
        try {
            for (int i = 0; i < Index.WeakIndex.get().ShopCarInfoList.size(); i++) {

                odd = odd * Double.valueOf(Index.WeakIndex.get().ShopCarInfoList.get(i).getItem().getString("Odd"));
            }
            return (int) Math.round(odd * 10 * payout);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }


    //過關組合最高可得金額計算
    public static int PassingTheCombination(int payout) {

        Index.WeakIndex.get().ComboTypeSelect.removeAllViews();
        String com[] = new String[Index.WeakIndex.get().ShopCarInfoList.size()];
        String to[] = new String[com.length];
        Double odd = 1.0;

        try {

            for (int i = 0; i < Index.WeakIndex.get().ShopCarInfoList.size(); i++) {

                odd = odd * Double.valueOf(Index.WeakIndex.get().ShopCarInfoList.get(i).getItem().getString("Odd"));
                com[i] = (String.valueOf(i));

            }


            for (int i = 1; i <= com.length; i++) {

                count = 0;
                sum = 0;
                comb(com, to, i, com.length, i);

                if(sum!=0 && count != 0){

                    Index.WeakIndex.get().ComboCheckBox[i] = new CheckBox(Index.WeakIndex.get().ShopDialog.getContext());
                    Index.WeakIndex.get().ComboCheckBox[i] .setText("過 " + i + " 關( " + count + " 個組合 最高可得 " + sum + " 元)");
                    Index.WeakIndex.get().ComboCheckBox[i] .setTag("{"+"\"pass\":\""+i+"\","+"\"count\":\""+count+"\","+"\"sum\":\""+sum+"\""+"}");
                    Index.WeakIndex.get().ComboTypeSelect.addView( Index.WeakIndex.get().ComboCheckBox[i] );

                    Index.WeakIndex.get().ComboCheckBox[i] .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            try {
                                JSONObject content = new JSONObject(compoundButton.getTag().toString());
                                if(compoundButton.isChecked()){

                                    Index.WeakIndex.get().BettingWon.setText(String.valueOf(Integer.valueOf(Index.WeakIndex.get().BettingWon.getText().toString()) + Integer.valueOf(content.getString("sum"))));
                                }else{
                                    Index.WeakIndex.get().BettingWon.setText(String.valueOf(Integer.valueOf(Index.WeakIndex.get().BettingWon.getText().toString()) - Integer.valueOf(content.getString("sum"))));
                                }




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });

                }

            }


            return (int) Math.round(odd * 10 * payout);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }


    //C(m, n) = C(m-1, n-1) + C(m-1, n)//排列組合
    private static int comb(String[] from, String[] to, int len, int m, int n) {
        try {
        StringBuilder result = new StringBuilder();

        Double odd = 1.0;

        if (n == 0) {

            for (int i = 0; i < len; i++) {

                result.append(to[i]);

            }
            int b_count=0;
            if(b_count ==0 && Index.WeakIndex.get().B_Count ==0){
                for (int i = 0; i < len; i++) {

                    odd = odd * Double.valueOf(Index.WeakIndex.get().ShopCarInfoList.get(Integer.parseInt(to[i])).getItem().getString("Odd"));

                }
                count++;
                sum =sum+  (int) Math.round(odd * (Integer.valueOf(Index.WeakIndex.get().BettingPayout.getText().toString()) * 10));
            }else{
                for(int i = 0;i <result.length();i++){
                    for (int j = 0 ; j < Index.WeakIndex.get().HasB.size();j++){
                        if(Index.WeakIndex.get().HasB.get(j).equals(Integer.valueOf(result.substring(i, i + 1)))){

                            b_count++;

                        }
                    }

                }
                if(b_count ==  Index.WeakIndex.get().B_Count){
                    for(int i = 0;i <result.length();i++){
                        odd = odd * Double.valueOf(Index.WeakIndex.get().ShopCarInfoList.get(Integer.parseInt(to[i])).getItem().getString("Odd"));
                    }
                    count++;
                    sum =sum+  (int) Math.round(odd * (Integer.valueOf(Index.WeakIndex.get().BettingPayout.getText().toString()) * 10));
                }
            }





            result.append("\n");

        } else {
            to[n - 1] = from[m - 1];

            if (m > n - 1) {
                result = new StringBuilder(comb(from, to, len, m - 1, n - 1));

            }
            if (m > n) {
                result.insert(0, comb(from, to, len, m - 1, n));

            }

        }

        return sum;

        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }


}
