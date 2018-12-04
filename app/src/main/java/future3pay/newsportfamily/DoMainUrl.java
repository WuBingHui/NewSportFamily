package future3pay.newsportfamily;

public class DoMainUrl {

    private volatile static DoMainUrl INSTANCE; //聲明成 volatile
    public static String SingleGame,PopularGame,NowOpening,Register,PhoneSms,ResetPassWord,Login,Info,SportsType,GameInfo,GameResult,GameChampionInfo,GameCountDown,GameActive,BettingRecord,BettingRecordDetail,PointRecord,DepositRecord,DrawRecord,AddBettingToShopCar,RemoveBettingFromShopCar,RemoveAllBettingFromShopCar,CheckBettingFromShopCar,SendBettingFromShopCar,ReSendEmail,GoogleSignIn,FacebookLogin,PhoneVerify;
   private final String domain = "https://fet555888.tw";
    private DoMainUrl (){

        Register =  domain+"/api/mobile/register";//一般註冊會員
        PhoneSms =  domain+"/api/mobile/send/phone-sms";//一般註冊會員-發送簡訊
        ResetPassWord =  domain+"/api/mobile/send/reset-password";//一般會員-忘記密碼
        Login =  domain+"/api/mobile/login";//一般會員登入
        Info =  domain+"/api/mobile/get/user/info";//一般會員-基本資料
        SportsType = domain+"/api/mobile/get/sports"; //賽事球種
        GameInfo = domain+"/api/mobile/get/game/info/"; //一般賽事投注
        GameChampionInfo = domain+"/api/mobile/get/champion/game/info/"; //冠軍賽事投注
        GameResult = domain+"/api/mobile/get/data/info"; //賽事結果
        GameCountDown = domain+"/api/mobile/get/live/game/countdown"; //場中賽事 倒數計時最近一場
        GameActive = domain+"/api/mobile/get/live/game/active";//場中賽事 正在進行中場次
        BettingRecord = domain+"/api/mobile/get/member/all-betting-record";//投注紀錄
        BettingRecordDetail = domain+"/api/mobile/get/member/betting-record/";//投注紀錄-訂單詳細
        PointRecord = domain+"/api/mobile/get/member/points/records";//點數使用紀錄
        DepositRecord = domain+"/api/mobile/get/member/points/deposit-record";//補點紀錄
        DrawRecord = domain+"/api/mobile/get/member/points/draw-record";//提領紀錄
        AddBettingToShopCar = domain+"/api/mobile/get/member/betting/shopcar";//新增投注選項進購物車
        RemoveBettingFromShopCar = domain+"/api/mobile/get/member/betting/shopcar/clean-one";//刪除購物車單筆投注選項
        RemoveAllBettingFromShopCar = domain+"/api/mobile/get/member/betting/shopcar/clean-up";//刪除購物車全部投注選項
        CheckBettingFromShopCar = domain+"/api/mobile/get/member/betting/deal/order";//取得投注資料檢查訂單
        SendBettingFromShopCar = domain+"/api/mobile/get/member/betting/check/order";//確認訂單資訊 送出訂單
        ReSendEmail = domain+"/api/mobile/register/resend-validator-email";//email 重發驗證信

        PhoneVerify = domain+"/api/mobile/members/please-verify-phone";//一般會員 - 手機驗證

        GoogleSignIn = domain+"/api/mobile/socialite/login/google";//會員登入 - 第三方登入 - google
        FacebookLogin = domain+"/api/mobile/socialite/login/facebook";//會員登入 - 第三方登入 - facebook

        NowOpening = domain+"/api/mobile/get/nowopening/sports";//目前開賽的球種
        PopularGame = domain+"/api/mobile/get/popular/game/info";//賽事 - 熱門賽事
        SingleGame = domain+"/api/mobile/get/single/game/info";//賽事 - 單場賽事
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
