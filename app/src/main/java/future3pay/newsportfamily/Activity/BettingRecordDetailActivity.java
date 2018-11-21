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
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import future3pay.newsportfamily.API.BettingRecordDetailAPI;
import future3pay.newsportfamily.Bean.BettingRecordDetailBean;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;

public class BettingRecordDetailActivity extends AppCompatActivity {
    public static WeakReference<BettingRecordDetailActivity> WeakBettingRecordDetail;

    public List<BettingRecordDetailBean> BettingRecordDetailList;

    public RVAdapter<BettingRecordDetailBean> BettingRecordDetailAdapter;

    public VerticalRefreshLayout BettingRecordDetailRV;

    private TextView DetailOrderNo, DetailBettingType, DetailBettingCombination, DetailBettingSingleAmount, DetailBettingAmount, DetailBettingTime, DetailBettingReason, DetailBettingPayout, DetailBettingDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betting_record_detail);

        WeakBettingRecordDetail = new WeakReference<>(this);

        BettingRecordDetailRV = findViewById(R.id.BettingRecordDetailRV);

        DetailOrderNo = findViewById(R.id.DetailOrderNo);
        DetailBettingType = findViewById(R.id.DetailBettingType);
        DetailBettingCombination = findViewById(R.id.DetailBettingCombination);
        DetailBettingSingleAmount = findViewById(R.id.DetailBettingSingleAmount);
        DetailBettingAmount = findViewById(R.id.DetailBettingAmount);
        DetailBettingReason = findViewById(R.id.DetailBettingReason);
        DetailBettingPayout = findViewById(R.id.DetailBettingPayout);
        DetailBettingDate = findViewById(R.id.DetailBettingDate);


        BettingRecordDetailList = new ArrayList<>();

        Intent intent = getIntent();

        actionbar();

        Loading.start(this);
        GetRecord();
        BettingRecordDetailAPI.BettingRecordDetail(Index.WeakIndex.get().UserInfo.getString("Token", ""), intent.getStringExtra("order"));
    }

    private void actionbar() {

        //獲取ActionBar對象
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar_login, null);
        TextView actionbar_textview = v.findViewById(R.id.actionbar_textview);
        actionbar_textview.setText("訂單資訊");
        Button back = v.findViewById(R.id.Back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
    }


    private void GetRecord() {

        BettingRecordDetailAdapter = new RVAdapter<BettingRecordDetailBean>(BettingRecordDetailList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, BettingRecordDetailBean bean, boolean isSelected) {

                DetailOrderNo.setText("#" + bean.getOrderNo());
                DetailBettingType.setText(bean.getBetsType());
                DetailBettingCombination.setText(bean.getCombination());
                DetailBettingSingleAmount.setText(bean.getSingleAmount());
                DetailBettingAmount.setText(bean.getAmount());
                DetailBettingReason.setText(bean.getErrorReason());
                DetailBettingPayout.setText(bean.getPayout());
                DetailBettingDate.setText(bean.getBetsTime());

                try {

                    JSONObject content = new JSONObject(bean.getInfos());
                    holder.setText(R.id.DetailCode, content.getString("code"));
                    holder.setText(R.id.DetailCategory, content.getString("category"));
                    holder.setText(R.id.DetailTime, content.getString("gameStartTime"));
                    holder.setText(R.id.DetailTeam, content.getString("gameTeam"));
                    holder.setText(R.id.DetailSelect, content.getString("option"));
                    holder.setText(R.id.DetailResult, content.getString("result"));
                    holder.setText(R.id.DetailPlay, content.getString("play"));
                    holder.setText(R.id.DetailOdd, content.getString("odd"));
                    holder.setText(R.id.DetailStatus, content.getString("status"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


            @Override
            public int getItemLayoutID(int position, BettingRecordDetailBean bean) {
                return R.layout.betting_record_detail_card;


            }


            @Override
            public void onItemClick(int position, BettingRecordDetailBean bean) {


            }

        };

        BettingRecordDetailRV.setAdapter(this, BettingRecordDetailAdapter, getResources().getColor(R.color.topcolor),
                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {


                        BettingRecordDetailAPI.BettingRecordDetail(Index.WeakIndex.get().UserInfo.getString("Token", ""), DetailOrderNo.getText().toString().substring(1));

                    }
                });


    }


}
