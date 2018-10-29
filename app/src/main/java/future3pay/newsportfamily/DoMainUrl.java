package future3pay.newsportfamily;

import android.util.Log;

public class DoMainUrl {
    private volatile static DoMainUrl INSTANCE; //聲明成 volatile
    public static String Register,PhoneSms,ResetPassWord,Login,Info,SportsType,GameInfo,GameResult,GameChampionInfo,GameCountDown,GameActive,BettingRecord,BettingRecordDetail,PointRecord,DepositRecord,DrawRecord ;

    private DoMainUrl (){

        Register =  "https://dev-new.fet555888.tw/api/mobile/register";//一般註冊會員
        PhoneSms =  "https://dev-new.fet555888.tw/api/mobile/send/phone-sms";//一般註冊會員-發送簡訊
        ResetPassWord =  "https://dev-new.fet555888.tw/api/mobile/send/reset-password";//一般會員-忘記密碼
        Login =  "https://dev-new.fet555888.tw/api/mobile/login";//一般會員登入
        Info =  "https://dev-new.fet555888.tw/api/mobile/get/user/info";//一般會員-基本資料
        SportsType = "https://dev-new.fet555888.tw/api/mobile/get/sports"; //賽事球種
        GameInfo = "https://dev-new.fet555888.tw/api/mobile/get/game/info/"; //一般賽事投注
        GameChampionInfo = "https://dev-new.fet555888.tw/api/mobile/get/champion/game/info/"; //冠軍賽事投注
        GameResult = "https://dev-new.fet555888.tw/api/mobile/get/data/info"; //賽事結果
        GameCountDown = "https://dev-new.fet555888.tw/api/mobile/get/live/game/countdown"; //場中賽事 倒數計時最近一場
        GameActive = "https://dev-new.fet555888.tw/api/mobile/get/live/game/active";//場中賽事 正在進行中場次
        BettingRecord = "https://dev-new.fet555888.tw/api/mobile/get/member/all-betting-record";//投注紀錄
        BettingRecordDetail = "https://dev-new.fet555888.tw/api/mobile/get/member/betting-record/";//投注紀錄-訂單詳細
        PointRecord = "https://dev-new.fet555888.tw/api/mobile/get/member/points/records";//點數使用紀錄
        DepositRecord = "https://dev-new.fet555888.tw/api/mobile/get/member/points/deposit-record";//補點紀錄
        DrawRecord = "https://dev-new.fet555888.tw/api/mobile/get/member/points/draw-record";//提領紀錄




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
