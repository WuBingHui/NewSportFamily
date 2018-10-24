package future3pay.newsportfamily;

import android.util.Log;

class DoMainUrl {
    private volatile static DoMainUrl INSTANCE; //聲明成 volatile
    static String Register,PhoneSms,ResetPassWord,Login,Info,SportsType ;

    private DoMainUrl (){

        Register =  "https://dev-new.fet555888.tw/api/mobile/register";//一般註冊會員
        PhoneSms =  "https://dev-new.fet555888.tw/api/mobile/send/phone-sms";//一般註冊會員-發送簡訊
        ResetPassWord =  "https://dev-new.fet555888.tw/api/mobile/send/reset-password";//一般會員-忘記密碼
        Login =  "https://dev-new.fet555888.tw/api/mobile/login";//一般會員登入
        Info =  "https://dev-new.fet555888.tw/api/mobile/get/user/info";//一般會員-基本資料
        SportsType = "https://dev-new.fet555888.tw/api/mobile/get/sports"; //賽事球種

    }

    static void GetDoMain() {
        if (INSTANCE == null) {
            synchronized (DoMainUrl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DoMainUrl();
                }
            }
        }
    }


}
