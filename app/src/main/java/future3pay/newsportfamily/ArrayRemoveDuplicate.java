package future3pay.newsportfamily;

import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import future3pay.newsportfamily.Bean.GameResultBean;
import future3pay.newsportfamily.Fragment.GameResultFragment;

public class ArrayRemoveDuplicate {
    public static String[] RemoveDuplicate(List<GameResultBean> array_category) {
        String array[] = new String[array_category.size()];
        for(int i = 0 ; i < array_category.size();i++){
            array[i] = GameResultFragment.WeakGameResult.get().GameResulList.get(i).getCategory();
        }

        HashSet<String> set = new HashSet<>(Arrays.asList(array));
        String result[] = set.toArray(new String[0]);

        return result;
    }
}
