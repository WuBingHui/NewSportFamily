package future3pay.newsportfamily;

import java.util.regex.Pattern;

public class VerifyData {



    //判斷長度
     public static Boolean VerifyLength(String str, int length) {

        return !str.equals("") && str.length() >= length;

    }

    //判斷是否中文
    public static Boolean VerifyChiness(String str) {

            Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5]+");
            if(str.equals("")){
                return false;
            }else{
                for(int i=0;i<str.length();i++){
                    if(!pattern.matcher(str.substring(i,i+1)).matches()){
                        return false;

                    }
                }
                return true;
            }
    }

    //驗證信箱
     public static boolean VerifyEmail(String email) {
          Pattern EMAIL_PATTERN = Pattern
                 .compile("^\\w+\\.*\\w+@(\\w+\\.){1,5}[a-zA-Z]{2,3}$");
         boolean result = false;
         if (EMAIL_PATTERN.matcher(email).matches()) {
             result = true;
         }
         return result;
     }

   //驗證手機
     public static boolean VerifyPhone(String msisdn) {
        Pattern MSISDN_PATTERN = Pattern
                 .compile("[+-]?\\d{10,12}");
         boolean result = false;
         if (MSISDN_PATTERN.matcher(msisdn).matches()) {
             result = true;
         }
         return result;
     }

}
