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

import future3pay.newsportfamily.API.DepositRecordAPI;
import future3pay.newsportfamily.API.UseRecordAPI;
import future3pay.newsportfamily.Bean.DepositRecordBean;
import future3pay.newsportfamily.Bean.UseRecordBean;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;

public class MakeUpPointRecordActivity extends AppCompatActivity {

    public static WeakReference<MakeUpPointRecordActivity> WeakMakeUpPointRecord;

    public List<DepositRecordBean> DepositRecordList;

    public RVAdapter<DepositRecordBean> DepositRecordAdapter;

    public VerticalRefreshLayout DepositRecordRV;

    public TextView NoDepositRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_up_point_record);
        WeakMakeUpPointRecord = new WeakReference<>(this);
        DepositRecordList = new ArrayList<>();
        DepositRecordRV = findViewById(R.id.DepositRecordRV);
        NoDepositRecord= findViewById(R.id.NoDepositRecord);
        NoDepositRecord.setVisibility(View.GONE);

        actionbar();

        Loading.start(this);
        DepositRecordAPI.UseRecord(Index.WeakIndex.get().UserInfo.getString("Token",""));
        GetDepositRecord();

    }


    private void GetDepositRecord(){

        DepositRecordAdapter = new RVAdapter<DepositRecordBean>(DepositRecordList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, DepositRecordBean bean, boolean isSelected) {

                holder.setText(R.id.DepositRecordNo, bean.getOrderNo());
                holder.setText(R.id.DepositRecordDate, bean.getRecordTime());
                holder.setText(R.id.DepositRecordCashPoints, bean.getWonPoints());
                holder.setText(R.id.DepositRecordFamilyPoints, bean.getOriginPoints());

            }


            @Override
            public int getItemLayoutID(int position, DepositRecordBean bean) {
                return R.layout.deposit_record_card;

            }


            @Override
            public void onItemClick(int position, DepositRecordBean bean) {


            }

        };

        DepositRecordRV.setAdapter(this,DepositRecordAdapter,  getResources().getColor(R.color.topcolor),
                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {


                        DepositRecordAPI.UseRecord(Index.WeakIndex.get().UserInfo.getString("Token",""));

                    }
                });




    }


    private void actionbar(){

        //獲取ActionBar對象
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar_login, null);
        TextView actionbar_textview = (TextView) v.findViewById(R.id.actionbar_textview);
        actionbar_textview.setText("補點紀錄");
        Button back = (Button) v.findViewById(R.id.Back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
    }

}
