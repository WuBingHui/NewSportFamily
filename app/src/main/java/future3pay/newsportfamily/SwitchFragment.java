package future3pay.newsportfamily;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import future3pay.newsportfamily.Fragment.BettingCountDownFragment;
import future3pay.newsportfamily.Fragment.BettingFragment;
import future3pay.newsportfamily.Fragment.GameResultFragment;
import future3pay.newsportfamily.Fragment.MemberFragment;

public class SwitchFragment extends AppCompatActivity {
    private static Fragment BettingCountDownFragment;
    private static Fragment BettingFragment;
    private static Fragment GameResultFragment;
    private static Fragment MemberFragment;



    private static FragmentManager m_manager;
    private static FragmentTransaction m_transaction;


    ///初始化&建立介面
   /* public static void init2(Bundle savedInstanceState,FragmentManager manager,FragmentTransaction transaction){
        switchfragment.m_manager=manager;
        switchfragment.m_transaction=transaction;
        if (savedInstanceState == null) {
            m_bettingFragment=new bettingFragment();
            m_bettingMidFragment=new bettingMidFragment();
            m_saveFragment=new saveFragment();
            m_memberCenterFragment=new memberCenterFragment();
            m_gameResultFragment=new gameResultFragment();



            transaction.add(R.id.fragmentshow, m_bettingMidFragment, "betm");
            transaction.add(R.id.fragmentshow, m_gameResultFragment, "game");
            transaction.add(R.id.fragmentshow, m_saveFragment, "save");

            transaction.add(R.id.fragmentshow, m_memberCenterFragment, "mem");

            transaction.add(R.id.fragmentshow, m_bettingFragment, "bet").hide(m_memberCenterFragment).hide(m_saveFragment).hide(m_gameResultFragment).hide(m_bettingMidFragment).commitAllowingStateLoss();
        }else{

            m_bettingFragment =manager.findFragmentByTag("bet");
            m_gameResultFragment =manager.findFragmentByTag("game");
            m_bettingMidFragment = manager.findFragmentByTag("betm");
            m_saveFragment =manager.findFragmentByTag("save");
            m_memberCenterFragment = manager.findFragmentByTag("member");
            m_loginFragment = manager.findFragmentByTag("log");


            transaction.show(m_bettingFragment).hide(m_bettingMidFragment).detach(m_saveFragment).hide(m_memberCenterFragment).hide(m_gameResultFragment).commitAllowingStateLoss();
        }
    }
*/



    public static void init(Bundle savedInstanceState, FragmentManager manager, FragmentTransaction transaction){
        m_manager=manager;
        m_transaction=transaction;

        if (savedInstanceState == null) {

            BettingFragment=new BettingFragment();
            BettingCountDownFragment=new BettingCountDownFragment();
            GameResultFragment=new GameResultFragment();
            MemberFragment=new MemberFragment();

            transaction.add(R.id.IndexFrame, BettingFragment, "BettingFragment");
            transaction.add(R.id.IndexFrame, BettingCountDownFragment, "BettingCountDownFragment");
            transaction.add(R.id.IndexFrame, GameResultFragment, "GameResultFragment");
            transaction.add(R.id.IndexFrame, MemberFragment, "MemberFragment");

            transaction.show(BettingFragment).detach(BettingCountDownFragment).hide(GameResultFragment).hide(MemberFragment).commitAllowingStateLoss();

        }else{


            BettingFragment = manager.findFragmentByTag("BettingFragment");
            BettingCountDownFragment = manager.findFragmentByTag("BettingCountDownFragment");
            GameResultFragment = manager.findFragmentByTag("GameResultFragment");
            MemberFragment = manager.findFragmentByTag("MemberFragment");




            transaction.show(BettingFragment).detach(BettingCountDownFragment).hide(GameResultFragment).hide(MemberFragment).commitAllowingStateLoss();

        }
    }

    //切換tab 的方法
    public static void selectFragment(String tag){
        m_transaction = m_manager.beginTransaction();
        m_transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);

        BettingFragment = m_manager.findFragmentByTag("BettingFragment");
        BettingCountDownFragment = m_manager.findFragmentByTag("BettingCountDownFragment");
        GameResultFragment = m_manager.findFragmentByTag("GameResultFragment");
        MemberFragment = m_manager.findFragmentByTag("MemberFragment");








        switch (tag)
        {
            case "BettingFragment":
                m_transaction.show(BettingFragment).detach(BettingCountDownFragment).hide(GameResultFragment).hide(MemberFragment).commitAllowingStateLoss();
                break;
            case "BettingCountDownFragment":
                m_transaction.hide(BettingFragment).attach(BettingCountDownFragment).hide(GameResultFragment).hide(MemberFragment).commitAllowingStateLoss();
                break;
            case "GameResultFragment":
                m_transaction.hide(BettingFragment).detach(BettingCountDownFragment).show(GameResultFragment).hide(MemberFragment).commitAllowingStateLoss();
                break;
            case "MemberFragment":
                m_transaction.hide(BettingFragment).detach(BettingCountDownFragment).hide(GameResultFragment).show(MemberFragment).commitAllowingStateLoss();
                break;

        }


    }

}
