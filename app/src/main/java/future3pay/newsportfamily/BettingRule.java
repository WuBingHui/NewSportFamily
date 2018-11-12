package future3pay.newsportfamily;

import android.util.Log;
import android.widget.CheckBox;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class BettingRule {
private  static   int sum = 0;
    private  static int count = 0;
    //單場最高可得金額計算
    public static int Single(int payout) {
        Index.WeakIndex.get().ComboTypeSelect.removeAllViews();
        try {
        int sum = 0;

        for(int i = 0; i< Index.WeakIndex.get().ShopCarInfoList.size(); i++){

                sum=sum+(int)Math.round(Double.valueOf(Index.WeakIndex.get().ShopCarInfoList.get(i).getItem().getString("Odd"))*10*payout);

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
        Double odd= 1.0;
        try {
        for(int i = 0; i<   Index.WeakIndex.get().ShopCarInfoList.size(); i++){

            odd=odd*Double.valueOf(Index.WeakIndex.get().ShopCarInfoList.get(i).getItem().getString("Odd"));
        }
        return  (int)Math.round(odd*10*payout);
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
                com[i]=(String.valueOf(i));
            }



            for (int i = 1; i <= com.length; i++) {
                boolean open = true;
                for(int j = 1 ; j <=Index.WeakIndex.get().HasB.size();j++){
                    if(Index.WeakIndex.get().HasB.get(j).equals(i)){
                         open=false;
                    }
                }
                if(open){
                    count=0;
                    sum = 0;
                    comb(com, to, i, com.length, i);
                    CheckBox checkBox = new CheckBox(Index.WeakIndex.get().ShopDialog.getContext());
                    checkBox.setText("過 "+i+" 關( "+count+" 個組合 最高可得 "+sum+ " 元)");
                    Index.WeakIndex.get().ComboTypeSelect.addView(checkBox);
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

        StringBuilder result = new StringBuilder();

        Double odd= 1.0;

        if (n == 0) {
            for (int i = 0; i < len; i++) {
                try {

                    odd = odd * Double.valueOf(Index.WeakIndex.get().ShopCarInfoList.get(Integer.parseInt(to[i])).getItem().getString("Odd"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            count++;
            result.append((int)Math.round(odd*(Integer.valueOf(Index.WeakIndex.get().BettingPayout.getText().toString())*10)));
            sum =sum+(int)Math.round(odd*(Integer.valueOf(Index.WeakIndex.get().BettingPayout.getText().toString())*10));
            result.append("\n");
        } else {
            to[n-1]=from[m-1];

            if (m > n - 1) {
                result = new StringBuilder(comb(from, to, len, m - 1, n - 1));

            }
            if (m > n) {
                result.insert(0, comb(from, to, len, m - 1, n));

            }

        }
        return sum;
    }


}
