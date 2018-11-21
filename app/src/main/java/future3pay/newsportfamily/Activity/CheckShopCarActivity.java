package future3pay.newsportfamily.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.cy.cyrvadapter.refreshrv.BaseRefreshLayout;
import com.cy.cyrvadapter.refreshrv.VerticalRefreshLayout;

import org.json.JSONException;

import java.lang.ref.WeakReference;

import future3pay.newsportfamily.API.SendBettingFromShopCarAPI;
import future3pay.newsportfamily.Bean.ShopCarInfoBean;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;

public class CheckShopCarActivity extends AppCompatActivity {
    public static WeakReference<CheckShopCarActivity> WeakCheckShopCar;
    private TextView ShopCarBettingType, ShopCarBettingCombination, ShopCarBettingSingleAmount, ShopCarBettingAmount, ShopCarBettingWon;
    private Button ShopCarBettingSend, ShopCarBettingCancel;

    private RVAdapter<ShopCarInfoBean> ShopCarBettingAdapter;
    private VerticalRefreshLayout ShopCarBettingRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_shopcar);
        Intent intent = getIntent();
        WeakCheckShopCar = new WeakReference<>(this);
        ShopCarBettingRV = findViewById(R.id.ShopCarBettingRV);
        ShopCarBettingType = findViewById(R.id.ShopCarBettingType);
        ShopCarBettingCombination = findViewById(R.id.ShopCarBettingCombination);
        ShopCarBettingSingleAmount = findViewById(R.id.ShopCarBettingSingleAmount);
        ShopCarBettingAmount = findViewById(R.id.ShopCarBettingAmount);
        ShopCarBettingWon = findViewById(R.id.ShopCarBettingWon);
        ShopCarBettingSend = findViewById(R.id.ShopCarBettingSend);
        ShopCarBettingCancel = findViewById(R.id.ShopCarBettingCancel);

        ShopCarBettingType.setText(intent.getStringExtra("Combo"));
        ShopCarBettingCombination .setText(intent.getStringExtra("Combination"));
        ShopCarBettingSingleAmount .setText(intent.getStringExtra("SingleAmount"));
        ShopCarBettingAmount .setText(intent.getStringExtra("Amount"));
        ShopCarBettingWon .setText(intent.getStringExtra("Payout"));


        ShopCarBettingSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loading.start(CheckShopCarActivity.this);
                SendBettingFromShopCarAPI.SendBettingFromShopCar(Index.WeakIndex.get().UserInfo.getString("Token",""));
            }

        });


        ShopCarBettingCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        actionbar();

        CheckShopCar();

    }

    private void actionbar() {

        //獲取ActionBar對象
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar_login, null);
        TextView actionbar_textview = v.findViewById(R.id.actionbar_textview);
        Button back = v.findViewById(R.id.Back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));

    }




    private void CheckShopCar() {

        ShopCarBettingAdapter = new RVAdapter<ShopCarInfoBean>(Index.WeakIndex.get().ShopCarInfoList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, ShopCarInfoBean bean, boolean isSelected) {

                try {

                    holder.setText(R.id.ShopCarCardCategory,bean.getItem().getString("Category"));
                    holder.setText(R.id.ShopCarCardPlay,bean.getItem().getString("Play"));
                    holder.setText(R.id.ShopCarCardNo,bean.getItem().getString("Code"));
                    holder.setText(R.id.ShopCarCardMins,bean.getItem().getString("Mins"));
                    holder.setText(R.id.ShopCarCardAway,bean.getItem().getString("Away"));
                    holder.setText(R.id.ShopCarCardHome,bean.getItem().getString("Home"));
                    holder.setText(R.id.ShopCarCardSelect,bean.getItem().getString("Select"));
                    holder.setText(R.id.ShopCarCardOdd,bean.getItem().getString("Odd"));

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }


            @Override
            public int getItemLayoutID(int position, ShopCarInfoBean bean) {
                return R.layout.check_shopcar_card;


            }


            @Override
            public void onItemClick(int position, ShopCarInfoBean bean) {



            }

        };

        ShopCarBettingRV.setAdapter(this, ShopCarBettingAdapter, getResources().getColor(R.color.topcolor),

                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {

                        ShopCarBettingRV.finishRefreshing();

                    }
                });


    }





}
