package future3pay.newsportfamily;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.cy.cyrvadapter.adapter.RVAdapter;
import com.cy.cyrvadapter.refreshrv.BaseRefreshLayout;
import com.cy.cyrvadapter.refreshrv.VerticalRefreshLayout;
import com.cy.dialog.BaseDialog;
import com.sevenheaven.segmentcontrol.SegmentControl;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import future3pay.newsportfamily.API.GameChampionInfoAPI;
import future3pay.newsportfamily.API.GameNormalInfoAPI;
import future3pay.newsportfamily.API.RemoveAllBettingFromShopCarAPI;
import future3pay.newsportfamily.API.RemoveSingleItemBettingAPI;
import future3pay.newsportfamily.API.SportTypeAPI;
import future3pay.newsportfamily.API.UserInfoAPI;
import future3pay.newsportfamily.Activity.LoginActivity;
import future3pay.newsportfamily.Bean.GameChampionInfoDetailBean;
import future3pay.newsportfamily.Bean.GameNormalInfoBean;
import future3pay.newsportfamily.Bean.ShopCarInfoBean;
import future3pay.newsportfamily.Fragment.BettingFragment;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;


public class Index extends AppCompatActivity {
    public String SportType = "s-441";
    public String SportRoot = "0";
    public FragmentManager manager = null;
    public FragmentTransaction transaction = null;
    private FrameLayout IndexFrame;
    public SharedPreferences UserInfo;
    public List<String> GameType;
    public List<String> GameName;
    public List<String> GameCategory;
    public List<Integer> HasB;
    public List<ShopCarInfoBean> ShopCarInfoList;
    public  BaseDialog ShopDialog;
    public RVAdapter<ShopCarInfoBean> ShopCarAdapter;

    public VerticalRefreshLayout ShopCarRV;

    private SegmentControl ShopCarPlaySelect;
    private BaseDialog DialogMenu;
    public AHBottomNavigation bottomNavigation;
    public TextView actionbar_textview, BettingPayout, BettingSum, BettingWon;
    public LinearLayout ComboTypeSelect;
    public Button menu, back, shop, RemoveAll, SendOrder;
    public ScrollView MenuScroll;
    public int Play = 0;
    public static WeakReference<Index> WeakIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        WeakIndex = new WeakReference<>(this);

        MenuScroll = findViewById(R.id.MenuScroll);


        UserInfo = PreferenceManager
                .getDefaultSharedPreferences(this);

        GameType = new ArrayList<>();
        GameName = new ArrayList<>();
        GameCategory = new ArrayList<>();
        ShopCarInfoList = new ArrayList<>();
        HasB=new ArrayList<>();
        IndexFrame = findViewById(R.id.IndexFrame);


        ButtomTab buttomTab = new ButtomTab();
        buttomTab.CreateTab();

        DoMainUrl.GetDoMain();//加載一次domain
        actionbar();

        SportTypeAPI.SportType();//取球種Api

        if (!UserInfo.getString("Token", "").equals("")) {
            UserInfoAPI.UserInfo(UserInfo.getString("Token", ""));
        }

        //透過下方程式碼，取得Activity中執行的個體。
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        SwitchFragment.init(savedInstanceState, manager, transaction);

    }

    private void actionbar() {

        //獲取ActionBar對象
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar_index, null);
        actionbar_textview = (TextView) v.findViewById(R.id.actionbar_textview);
        menu = (Button) v.findViewById(R.id.menu);
        back = (Button) v.findViewById(R.id.back);
        shop = (Button) v.findViewById(R.id.shop);
        back.setVisibility(View.GONE);

        back.setOnClickListener(Back);
        menu.setOnClickListener(Menu);
        shop.setOnClickListener(Shop);
        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));

    }

    private SegmentControl.OnSegmentControlClickListener SelectPlay = new SegmentControl.OnSegmentControlClickListener() {

        @Override
        public void onSegmentControlClick(int index) {

            switch (index) {
                case 0:
                    Play = 0;
                    BettingSum.setText(String.valueOf(Integer.valueOf(BettingPayout.getText().toString()) * 10*ShopCarInfoList.size()));
                    BettingWon.setText(String.valueOf(BettingRule.Single(Integer.valueOf(BettingPayout.getText().toString()))));
                    break;
                case 1:
                    Play = 1;
                    BettingSum.setText(String.valueOf(Integer.valueOf(BettingPayout.getText().toString()) * 10));
                    BettingWon.setText(String.valueOf(BettingRule.Passing(Integer.valueOf(BettingPayout.getText().toString()))));
                    break;
                case 2:
                    Play = 2;
                    BettingSum.setText(String.valueOf(Integer.valueOf(BettingPayout.getText().toString()) * 10));
                    BettingWon.setText(String.valueOf(BettingRule.PassingTheCombination(Integer.valueOf(BettingPayout.getText().toString()))));
                    break;
            }
            ShopCarAdapter.notifyDataSetChanged();
        }

    };


    private Button.OnClickListener Shop = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {

            if (ShopCarInfoList.size() > 0) {

                Play =0;

                 ShopDialog = new BaseDialog(Index.this);
                ShopDialog.config(R.layout.shop_car, true).show();
                ShopCarRV = ShopDialog.findViewById(R.id.ShopCarRV);
                ShopCarPlaySelect = ShopDialog.findViewById(R.id.ShopCarPlaySelect);
                BettingPayout = ShopDialog.findViewById(R.id.BettingPayout);
                BettingSum = ShopDialog.findViewById(R.id.BettingSum);
                BettingWon = ShopDialog.findViewById(R.id.BettingWon);
                RemoveAll = ShopDialog.findViewById(R.id.RemoveAll);
                SendOrder = ShopDialog.findViewById(R.id.SendOrder);
                ComboTypeSelect=ShopDialog.findViewById(R.id.ComboTypeSelect);
                RemoveAll.setOnClickListener(remove);
                ShopCarPlaySelect.setOnSegmentControlClickListener(SelectPlay);

                BettingSum.setText(String.valueOf(Integer.valueOf(BettingPayout.getText().toString()) * 10*ShopCarInfoList.size()));
                BettingWon.setText(String.valueOf(BettingRule.Single(Integer.valueOf(BettingPayout.getText().toString()))));

                BettingPayout.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if (!String.valueOf(BettingPayout.getText()).equals("")) {

                            if (Integer.valueOf(BettingPayout.getText().toString()) * 10 <= 100000) {

                                switch (Play){

                                    case 0:
                                        BettingSum.setText(String.valueOf(Integer.valueOf(BettingPayout.getText().toString()) * 10*ShopCarInfoList.size()));
                                        BettingWon.setText(String.valueOf(BettingRule.Single(Integer.valueOf(BettingPayout.getText().toString()))));
                                        break;
                                    case 1:
                                        BettingSum.setText(String.valueOf(Integer.valueOf(BettingPayout.getText().toString()) * 10));
                                        BettingWon.setText(String.valueOf(BettingRule.Passing(Integer.valueOf(BettingPayout.getText().toString()))));
                                        break;
                                    case 2:
                                        BettingSum.setText(String.valueOf(Integer.valueOf(BettingPayout.getText().toString()) * 10));
                                        BettingWon.setText(String.valueOf(BettingRule.PassingTheCombination(Integer.valueOf(BettingPayout.getText().toString()))));
                                        break;

                                }


                            } else {
                                BettingPayout.setText("10");
                                BettingSum.setText(String.valueOf(Integer.valueOf(BettingPayout.getText().toString()) * 10));
                                ToastShow.start(Index.this, "最多投注10萬");
                            }
                        } else {
                            BettingPayout.setText("10");
                        }


                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                ShopCar();
            } else {
                ToastShow.start(Index.this, "無投注資料");
            }

        }
    };


    private Button.OnClickListener remove = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (Index.WeakIndex.get().ShopCarInfoList.size() > 0) {
                Loading.start(Index.this);
                RemoveAllBettingFromShopCarAPI.RemoveAllBettingFromShopCar(UserInfo.getString("Token", ""));

            } else {
                ToastShow.start(Index.WeakIndex.get(), "無訂單紀錄");
            }

        }
    };

    //返回聯賽列表
    private Button.OnClickListener Back = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {

            actionbar_textview.setText("運彩家族");
            back.setVisibility(View.GONE);
            menu.setVisibility(View.VISIBLE);
            bottomNavigation.setVisibility(View.VISIBLE);
            BettingFragment.WeakBettingFragment.get().BettingRV.setVisibility(View.VISIBLE);
            BettingFragment.WeakBettingFragment.get().BettingDetailRV.setVisibility(View.GONE);

        }
    };


    //菜單裡面的按鈕產生
    private Button.OnClickListener Menu = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            DialogMenu = new BaseDialog(Index.this);
            DialogMenu.config(R.layout.menu, 0.5f, Gravity.LEFT | Gravity.CENTER, BaseDialog.AnimInType.LEFT,
                    (int) (metrics.widthPixels / 1.5), WindowManager.LayoutParams.MATCH_PARENT, true).show();

            LinearLayout Normal = DialogMenu.findViewById(R.id.NormalGame);
            LinearLayout Champion = DialogMenu.findViewById(R.id.ChampionGame);


            for (int i = 0; i < GameName.size(); i++) {

                Button NormalType = new Button(DialogMenu.getContext());
                Button ChampionType = new Button(DialogMenu.getContext());

                NormalType.setTextColor(Color.parseColor("#218838"));
                ChampionType.setTextColor(Color.parseColor("#218838"));

                NormalType.setBackgroundColor(Color.parseColor("#ffffff"));
                ChampionType.setBackgroundColor(Color.parseColor("#ffffff"));

                NormalType.setTextSize(18);
                ChampionType.setTextSize(18);

                NormalType.setPadding(16, 0, 0, 0);
                ChampionType.setPadding(16, 0, 0, 0);

                ChampionType.setGravity(Gravity.CENTER | Gravity.START);
                NormalType.setGravity(Gravity.CENTER | Gravity.START);

                NormalType.setTag("0" + GameType.get(i));
                ChampionType.setTag("1" + GameType.get(i));

                NormalType.setText(GameName.get(i));
                ChampionType.setText(GameName.get(i));

                NormalType.setOnClickListener(SportTypeSelect);
                ChampionType.setOnClickListener(SportTypeSelect);

                Normal.addView(NormalType);
                Champion.addView(ChampionType);


            }

        }

    };

    //菜單裡的按鈕選擇後的方法
    private Button.OnClickListener SportTypeSelect = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {

            SportRoot = view.getTag().toString().substring(0, 1);
            DialogMenu.dismiss();
            Loading.start(Index.this);

            SportType = view.getTag().toString().substring(1);

            switch (SportRoot) {
                case "0":
                    GameNormalInfoAPI.GameInfo();
                    BettingFragment.WeakBettingFragment.get().GetNormalBetting();
                    break;
                case "1":
                    GameChampionInfoAPI.GameInfo();
                    BettingFragment.WeakBettingFragment.get().GetChampionBetting();
                    break;
            }


        }
    };


    private class ButtomTab {
        private void CreateTab() {
            bottomNavigation = (AHBottomNavigation) findViewById(R.id.BottomNavigation);

            // Create items
            AHBottomNavigationItem item1 = new AHBottomNavigationItem("一般投注", R.mipmap.betting, R.color.topcolor);
            AHBottomNavigationItem item2 = new AHBottomNavigationItem("場中賽事", R.mipmap.bettingmid, R.color.topcolor);
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

                    actionbar_textview.setText("運彩家族");
                    back.setVisibility(View.GONE);
                    menu.setVisibility(View.VISIBLE);

                    switch (position) {
                        case 0:
                            if (!wasSelected) {
                                SwitchFragment.selectFragment("BettingFragment");
                            }
                            break;
                        case 1:
                            if (!wasSelected) {
                                SwitchFragment.selectFragment("BettingCountDownFragment");
                            }
                            break;
                        case 2:
                            if (!wasSelected) {
                                SwitchFragment.selectFragment("GameResultFragment");
                            }
                            break;
                        case 3:
                            if (!wasSelected) {
                                if (!UserInfo.getString("Token", "").equals("")) {

                                    SwitchFragment.selectFragment("MemberFragment");
                                } else {
                                    Intent intent = new Intent();
                                    intent.setClass(Index.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                            break;
                    }
                    return true;
                }
            });


        }

    }


    //手機返回鍵設置
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 攔截返回鍵
            new AlertDialog.Builder(Index.this)//在哪個activity執行
                    .setTitle("確認視窗")
                    .setMessage("確定要結束應用程式嗎?")
                    .setPositiveButton("取消",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            })
                    .setNegativeButton("確認",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                }
                            }).show();
        }
        return true;
    }
    ///

    //購物車
    public void ShopCar() {

        ShopCarAdapter = new RVAdapter<ShopCarInfoBean>(ShopCarInfoList) {
            @Override
            public void bindDataToView(final RVViewHolder holder, final int position, ShopCarInfoBean bean, boolean isSelected) {

                try {

                    holder.setText(R.id.BettingItemTitle, bean.getItem().getString("Title"));
                    holder.setText(R.id.BettingItemTime, bean.getItem().getString("StartTime"));
                    holder.setText(R.id.BettingItemCategory, bean.getItem().getString("Category"));
                    holder.setText(R.id.BettingItemMins, bean.getItem().getString("Mins"));
                    holder.setText(R.id.BettingItemSelect, bean.getItem().getString("Select"));
                    holder.setText(R.id.BettingItemOdd, bean.getItem().getString("Odd"));
                    holder.itemView.findViewById(R.id.BettingItemB).setTag("0");

                    holder.itemView.findViewById(R.id.BettingItemB).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Drawable B_On = getResources().getDrawable(R.color.topcolor);

                            if (view.getTag().toString().equals("0")) {
                                view.setTag("1");
                                HasB.add(position+1);
                                holder.itemView.findViewById(R.id.BettingItemB).setBackground(B_On);

                                holder.setTextColor(R.id.BettingItemB, R.color.white);
                            } else {
                                view.setTag("0");
                                HasB.set(position+1,0);
                                holder.itemView.findViewById(R.id.BettingItemB).setBackground(null);

                                holder.setTextColor(R.id.BettingItemB, R.color.black);
                            }
                           // BettingSum.setText(String.valueOf(Integer.valueOf(BettingPayout.getText().toString()) * 10));
                           // BettingWon.setText(String.valueOf(BettingRule.PassingTheCombination(Integer.valueOf(BettingPayout.getText().toString()))));
                        }
                    });

                    if (Play == 2) {
                        holder.itemView.findViewById(R.id.BettingItemB).setVisibility(View.VISIBLE);
                    } else {
                        holder.itemView.findViewById(R.id.BettingItemB).setVisibility(View.GONE);
                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }


            @Override
            public int getItemLayoutID(int position, ShopCarInfoBean bean) {

                return R.layout.shop_car_card;

            }


            @Override
            public void onItemClick(int position, ShopCarInfoBean bean) {

                try {
                    Loading.start(Index.this);
                    RemoveSingleItemBettingAPI.RemoveSingleItemBetting(UserInfo.getString("Token", ""), position, bean.getItem().getString("Item"));
                } catch (JSONException e) {
                    Loading.diss();
                    e.printStackTrace();
                }

            }

        };

        ShopCarRV.setAdapter(Index.this, ShopCarAdapter, getResources().getColor(R.color.topcolor),
                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {

                        ShopCarAdapter.notifyDataSetChanged();
                        ShopCarRV.finishRefreshing();

                    }
                });


    }


}
