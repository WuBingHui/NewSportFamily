package future3pay.newsportfamily;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import future3pay.newsportfamily.API.SportTypeAPI;
import future3pay.newsportfamily.Fragment.BettingFragment;


public class Index extends AppCompatActivity {
    public String SportType="s-442";
    public FragmentManager manager = null;
    public FragmentTransaction transaction = null;
private FrameLayout IndexFrame;
public SharedPreferences UserInfo ;
public List<String> GameType;
public List<String> GameName;
public static WeakReference<Index> WeakIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        WeakIndex = new WeakReference<>(this);

        UserInfo = PreferenceManager
                .getDefaultSharedPreferences(this);

        GameType = new ArrayList<>();
        GameName = new ArrayList<>();

        IndexFrame = findViewById(R.id.IndexFrame);

        ButtomTab buttomTab = new ButtomTab();
        buttomTab.CreateTab();

        DoMainUrl.GetDoMain();//加載一次domain
        actionbar();



       // Intent intent=new Intent();
       // intent.setClass(this,LoginActivity.class);
      // startActivity(intent);

        //透過下方程式碼，取得Activity中執行的個體。
       manager = getSupportFragmentManager();
       transaction = manager.beginTransaction();

        SwitchFragment.init(savedInstanceState,manager,transaction);
    }

private void actionbar(){
    //客製化 ActionBar
    final ActionBar abar = getSupportActionBar();
    getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.white));
    View viewActionBar = getLayoutInflater().inflate(R.layout.actionbar_lable, null);
    ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.MATCH_PARENT,
            Gravity.CENTER);
    TextView actionbar_textview = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
    actionbar_textview.setText(R.string.app_name);
    abar.setCustomView(viewActionBar, params);
    abar.setDisplayShowCustomEnabled(true);
    abar.setDisplayShowTitleEnabled(false);
    abar.setDisplayHomeAsUpEnabled(false);
    abar.setHomeButtonEnabled(false);
//
}

    private class ButtomTab{
        private void CreateTab(){
            AHBottomNavigation bottomNavigation  = (AHBottomNavigation) findViewById(R.id.BottomNavigation);
            // Create items
            AHBottomNavigationItem item1 = new AHBottomNavigationItem("一般投注", R.mipmap.betting, R.color.topcolor);
            AHBottomNavigationItem item2 = new AHBottomNavigationItem("場中賽事", R.mipmap.bettingmid,R.color.topcolor);
          //  AHBottomNavigationItem item3 = new AHBottomNavigationItem("購物車", R.drawable.ic_launcher_background,R.color.topcolor);
            AHBottomNavigationItem item4 = new AHBottomNavigationItem("賽事結果", R.mipmap.result, R.color.topcolor);
            AHBottomNavigationItem item5 = new AHBottomNavigationItem("我的帳戶", R.mipmap.member, R.color.topcolor);

// Add items
            bottomNavigation.addItem(item1);
            bottomNavigation.addItem(item2);
          //  bottomNavigation.addItem(item3);
            bottomNavigation.addItem(item4);
            bottomNavigation.addItem(item5);
//"#3d628c"
// Set background color
            bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FFFFFF"));

            //bottomNavigation.setNotification("0",2);
            // Change colors
            bottomNavigation.setAccentColor(Color.parseColor("#218838"));
            bottomNavigation.setInactiveColor(Color.parseColor("#757575"));

            // Manage titles
            bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

            // bottomNavigation.setCurrentItem(0);


            // Set listeners
            bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
                @Override
                public boolean onTabSelected(int position, boolean wasSelected) {
                    switch (position){
                        case 0 :
                            if(!wasSelected){
                                SwitchFragment.selectFragment("BettingFragment");
                            }
                            break;
                        case 1 :
                            if(!wasSelected){
                                SwitchFragment.selectFragment("BettingCountDownFragment");
                            }
                            break;
                        case 2:
                            if(!wasSelected){
                                SwitchFragment.selectFragment("GameResultFragment");
                            }
                            break;
                        case 3 :
                            if(!wasSelected){
                                SwitchFragment.selectFragment("MemberFragment");
                            }
                            break;
                    }
                    return true;
                }
            });


        }

    }


}
