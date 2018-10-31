package future3pay.newsportfamily;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.cy.dialog.BaseDialog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import future3pay.newsportfamily.API.SportTypeAPI;
import future3pay.newsportfamily.API.UserInfoAPI;


public class Index extends AppCompatActivity {
    public String SportType="s-442";
    public FragmentManager manager = null;
    public FragmentTransaction transaction = null;
private FrameLayout IndexFrame;
public SharedPreferences UserInfo ;
public List<String> GameType;
public List<String> GameName;
    public List<String> GameCategory;
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
        GameCategory=  new ArrayList<>();
        IndexFrame = findViewById(R.id.IndexFrame);

        ButtomTab buttomTab = new ButtomTab();
        buttomTab.CreateTab();

        DoMainUrl.GetDoMain();//加載一次domain
        actionbar();

        SportTypeAPI.SportType();//取球種Api


       // Intent intent=new Intent();
       // intent.setClass(this,LoginActivity.class);
      // startActivity(intent);

        //透過下方程式碼，取得Activity中執行的個體。
       manager = getSupportFragmentManager();
       transaction = manager.beginTransaction();

        SwitchFragment.init(savedInstanceState,manager,transaction);

    }

private void actionbar(){

    //獲取ActionBar對象
    ActionBar bar = getSupportActionBar();
    bar.setDisplayShowCustomEnabled(true);
    View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar_index, null);
    TextView actionbar_textview = (TextView) v.findViewById(R.id.actionbar_textview);
    Button menu = (Button) v.findViewById(R.id.menu);
    menu.setOnClickListener(Menu);
    bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
}


private Button.OnClickListener Menu = new Button.OnClickListener(){

    @Override
    public void onClick(View view) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        BaseDialog dialog_left = new BaseDialog(Index.this);

        dialog_left.config(R.layout.menu, 0.5f, Gravity.LEFT | Gravity.CENTER, BaseDialog.AnimInType.LEFT,
                (int)(metrics.widthPixels/1.5), WindowManager.LayoutParams.MATCH_PARENT, true).show();


    }
};


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
