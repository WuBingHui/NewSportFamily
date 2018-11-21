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

import future3pay.newsportfamily.API.WithdrawalRecordAPI;
import future3pay.newsportfamily.Bean.WithdrawalRecordBean;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;

public class WithdrawalRecordActivity extends AppCompatActivity {
    public static WeakReference<WithdrawalRecordActivity> WeakWithdrawalRecord;

    public List<WithdrawalRecordBean> WithdrawalRecordList;

    public RVAdapter<WithdrawalRecordBean> WithdrawalRecordAdapter;

    public VerticalRefreshLayout WithdrawalRecordRV;

    public TextView NoWithdrawalRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_withdrawal_record);

        WeakWithdrawalRecord = new WeakReference<>(this);
        WithdrawalRecordList = new ArrayList<>();
        WithdrawalRecordRV = findViewById(R.id.WithdrawalRecordRV);
        NoWithdrawalRecord= findViewById(R.id.NoWithdrawalRecord);
        NoWithdrawalRecord.setVisibility(View.GONE);


        actionbar();

        Loading.start(this);

        WithdrawalRecordAPI.UseRecord(Index.WeakIndex.get().UserInfo.getString("Token",""));
        GetWithdeawalRecord();

    }



    private void GetWithdeawalRecord(){

        WithdrawalRecordAdapter = new RVAdapter<WithdrawalRecordBean>(WithdrawalRecordList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, WithdrawalRecordBean bean, boolean isSelected) {

                holder.setText(R.id.Amount, bean.getAmount());
                holder.setText(R.id.Status, bean.getStatus());
                holder.setText(R.id.BankInfo, bean.getBank_info());
                holder.setText(R.id.CreateTime, bean.getCreate_time());
                holder.setText(R.id.UpdateTime, bean.getUpdate_time());

            }


            @Override
            public int getItemLayoutID(int position, WithdrawalRecordBean bean) {
                return R.layout.withdrawal_record_card;

            }


            @Override
            public void onItemClick(int position, WithdrawalRecordBean bean) {


            }

        };

        WithdrawalRecordRV.setAdapter(this,WithdrawalRecordAdapter,  getResources().getColor(R.color.topcolor),
                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {


                        WithdrawalRecordAPI.UseRecord(Index.WeakIndex.get().UserInfo.getString("Token",""));

                    }
                });


    }

    private void actionbar(){

        //獲取ActionBar對象
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar_login, null);
        TextView actionbar_textview = v.findViewById(R.id.actionbar_textview);
        actionbar_textview.setText("提領紀錄");
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
