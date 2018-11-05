package future3pay.newsportfamily.Activity;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.cy.cyrvadapter.refreshrv.BaseRefreshLayout;
import com.cy.cyrvadapter.refreshrv.VerticalRefreshLayout;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.List;
import java.util.WeakHashMap;

import future3pay.newsportfamily.API.WithdrawalRecordAPI;
import future3pay.newsportfamily.Bean.BettingRecordBean;
import future3pay.newsportfamily.Bean.WithdrawalRecordBean;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;

public class BettingRecordActivity extends AppCompatActivity {
public static WeakReference<BettingRecordActivity> WeakBettingRecord;

    public List<BettingRecordBean> BettingRecordList;

    public RVAdapter<BettingRecordBean> BettingRecordAdapter;

    public VerticalRefreshLayout BettingRecordRV;

    public TextView StartDate,EndDate,NoBettingRecord;

    private Button Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betting_record);

        WeakBettingRecord = new WeakReference<>(this);

        StartDate = findViewById(R.id.StartDate);
        EndDate = findViewById(R.id.EndDate);
        Search = findViewById(R.id.Search);
        NoBettingRecord = findViewById(R.id.NoBettingRecord);

        StartDate.setOnClickListener(StartTimeSelect);

        EndDate.setOnClickListener(EndTimeSelect);


        actionbar();


    }




private TextView.OnClickListener StartTimeSelect = new TextView.OnClickListener(){
    @Override
    public void onClick(View view) {

        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(BettingRecordActivity.this, R.style.MyDatePickerDialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        StartDate.setText(year+"-"+(month+1)+"-"+String.format("%02d",dayOfMonth));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    };

    private TextView.OnClickListener EndTimeSelect = new TextView.OnClickListener(){
        @Override
        public void onClick(View view) {

            final Calendar calendar = Calendar.getInstance();

            DatePickerDialog dialog = new DatePickerDialog(BettingRecordActivity.this, R.style.MyDatePickerDialogTheme,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            EndDate.setText(year+"-"+(month+1)+"-"+String.format("%02d",dayOfMonth));
                        }
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }

    };




    private void actionbar(){

        //獲取ActionBar對象
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar_login, null);
        TextView actionbar_textview = (TextView) v.findViewById(R.id.actionbar_textview);
        actionbar_textview.setText("投注紀錄");
        Button back = (Button) v.findViewById(R.id.Back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
    }

    private void GetBettingRecord(){

        BettingRecordAdapter = new RVAdapter<BettingRecordBean>(BettingRecordList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, BettingRecordBean bean, boolean isSelected) {

              //  holder.setText(R.id.Amount, bean.getAmount());
              //  holder.setText(R.id.Status, bean.getStatus());
               // holder.setText(R.id.BankInfo, bean.getBank_info());
                //holder.setText(R.id.CreateTime, bean.getCreate_time());
               // holder.setText(R.id.UpdateTime, bean.getUpdate_time());

            }


            @Override
            public int getItemLayoutID(int position, BettingRecordBean bean) {
                return R.layout.withdrawal_record_card;

            }


            @Override
            public void onItemClick(int position, BettingRecordBean bean) {


            }

        };

        BettingRecordRV.setAdapter(this,BettingRecordAdapter,  getResources().getColor(R.color.topcolor),
                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {


                        WithdrawalRecordAPI.UseRecord(Index.WeakIndex.get().UserInfo.getString("Token",""));

                    }
                });


    }

}
