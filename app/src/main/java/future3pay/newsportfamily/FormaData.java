package future3pay.newsportfamily;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormaData {
    //時間戳記的轉換
    public static String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp * 1000;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        result = format.format(new Date(timeStamp));
        return result;
    }
}
