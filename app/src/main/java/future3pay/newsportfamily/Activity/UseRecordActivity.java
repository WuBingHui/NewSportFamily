package future3pay.newsportfamily.Activity;

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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import future3pay.newsportfamily.API.UseRecordAPI;
import future3pay.newsportfamily.Bean.UseRecordBean;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;

public class UseRecordActivity extends AppCompatActivity {
    public static WeakReference<UseRecordActivity> WeakUseRecord;

    public List<UseRecordBean> UseRecordList;

    public RVAdapter<UseRecordBean> UseRecordAdapter;


    public VerticalRefreshLayout UseRecordRV;

    public TextView NoUseRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_record);

        actionbar();
        WeakUseRecord= new WeakReference<>(this);
        UseRecordList = new ArrayList<>();
        UseRecordRV = findViewById(R.id.UseRecordRV);
        NoUseRecord= findViewById(R.id.NoUseRecord);
        NoUseRecord.setVisibility(View.GONE);

        Loading.start(this);

        UseRecordAPI.UseRecord(Index.WeakIndex.get().UserInfo.getString("Token",""));

        GetUseRecord();


    }


    private void GetUseRecord(){

        UseRecordAdapter = new RVAdapter<UseRecordBean>(UseRecordList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, UseRecordBean bean, boolean isSelected) {

                holder.setText(R.id.UseRecordNo, bean.getOrderNo());
                holder.setText(R.id.UseRecordDate, bean.getRecordTime());
                holder.setText(R.id.UseRecordCashPoints, bean.getWonPoints());
                holder.setText(R.id.UseRecordFamilyPoints, bean.getOriginPoints());

            }


            @Override
            public int getItemLayoutID(int position, UseRecordBean bean) {
                return R.layout.use_record_card;

            }


            @Override
            public void onItemClick(int position, UseRecordBean bean) {


            }

        };

        UseRecordRV.setAdapter(this,UseRecordAdapter,  getResources().getColor(R.color.topcolor),
                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {


                        UseRecordAPI.UseRecord(Index.WeakIndex.get().UserInfo.getString("Token",""));

                    }
                });




    }


    private void actionbar(){

        //獲取ActionBar對象
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar_login, null);
        TextView actionbar_textview = v.findViewById(R.id.actionbar_textview);
        actionbar_textview.setText("使用紀錄");
        Button back = v.findViewById(R.id.Back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
    }

}
