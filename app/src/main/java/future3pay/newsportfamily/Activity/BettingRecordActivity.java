package future3pay.newsportfamily.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.cy.cyrvadapter.refreshrv.BaseRefreshLayout;
import com.cy.cyrvadapter.refreshrv.VerticalRefreshLayout;
import com.cy.dialog.BaseDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.WeakHashMap;

import future3pay.newsportfamily.API.BettingRecordAPI;
import future3pay.newsportfamily.API.BettingRecordDetailAPI;
import future3pay.newsportfamily.API.WithdrawalRecordAPI;
import future3pay.newsportfamily.Bean.BettingRecordBean;
import future3pay.newsportfamily.Bean.WithdrawalRecordBean;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;

public class BettingRecordActivity extends AppCompatActivity {
    public static WeakReference<BettingRecordActivity> WeakBettingRecord;

    public List<BettingRecordBean> BettingRecordList;

    public RVAdapter<BettingRecordBean> BettingRecordAdapter;

    public VerticalRefreshLayout BettingRecordRV;

    public TextView StartDate, EndDate, NoBettingRecord, PurseTotal, BetsTotal, SubTotal;

    private Button Search;

    public String Status = "all";

    public Spinner OrderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betting_record);

        WeakBettingRecord = new WeakReference<>(this);
        BettingRecordRV = findViewById(R.id.BettingRecordRV);
        StartDate = findViewById(R.id.StartDate);
        EndDate = findViewById(R.id.EndDate);
        Search = findViewById(R.id.Search);

        PurseTotal = findViewById(R.id.PurseTotal);
        BetsTotal = findViewById(R.id.BetsTotal);
        SubTotal = findViewById(R.id.SubTotal);


        BettingRecordList = new ArrayList<>();

        NoBettingRecord = findViewById(R.id.NoBettingRecord);
        OrderStatus = findViewById(R.id.OrderStatus);

        StartDate.setOnClickListener(StartTimeSelect);

        EndDate.setOnClickListener(EndTimeSelect);

        Search.setOnClickListener(SearchRecord);

        OrderStatus.setOnItemSelectedListener(StatusSelect);
        actionbar();
        GetBettingRecord();

        Calendar mCal = Calendar.getInstance();
        CharSequence s = DateFormat.format("yyyy-MM-dd", mCal.getTime());    // kk:24小時制, hh:12小時制

        StartDate.setText(s);
        EndDate.setText(s);

    }

    private Button.OnClickListener SearchRecord = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {


            if (!String.valueOf(StartDate.getText()).equals("") && !String.valueOf(EndDate.getText()).equals("")) {

                Loading.start(BettingRecordActivity.this);
                BettingRecordAPI.BettingRecord(Index.WeakIndex.get().UserInfo.getString("Token", ""));

            } else {

                Toast message = Toast.makeText(BettingRecordActivity.this, "請選擇日期", Toast.LENGTH_SHORT);
                message.setGravity(Gravity.CENTER, 0, 0);
                message.show();

            }

        }

    };


    private TextView.OnClickListener StartTimeSelect = new TextView.OnClickListener() {
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
                            StartDate.setText(year + "-" + (month + 1) + "-" + String.format("%02d", dayOfMonth));

                        }
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }

    };

    private TextView.OnClickListener EndTimeSelect = new TextView.OnClickListener() {
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
                            EndDate.setText(year + "-" + (month + 1) + "-" + String.format("%02d", dayOfMonth));
                        }
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }

    };


    private void actionbar() {

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

    private void GetBettingRecord() {

        BettingRecordAdapter = new RVAdapter<BettingRecordBean>(BettingRecordList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, BettingRecordBean bean, boolean isSelected) {

                PurseTotal.setText(bean.getPurseTotal());
                BetsTotal.setText(bean.getBetsTotal());
                SubTotal.setText(bean.getSubTotal());

                try {

                    JSONObject content = new JSONObject(bean.getOrders());
                    holder.setText(R.id.BettingOrderNo, content.getString("order_no"));
                    holder.setText(R.id.BettingGameType, content.getString("bets_type"));
                    holder.setText(R.id.BettingMoney, content.getString("amount"));
                    holder.setText(R.id.BettingWonMoney, content.getString("purseAmount"));
                    holder.setText(R.id.BettingResult, content.getString("status"));
                    holder.setText(R.id.BettingDate, content.getString("bets_time"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


            @Override
            public int getItemLayoutID(int position, BettingRecordBean bean) {
                return R.layout.betting_record_card;


            }


            @Override
            public void onItemClick(int position, BettingRecordBean bean) {

                try {
                    JSONObject content = new JSONObject(bean.getOrders());

                    Intent intent = new Intent();
                    intent.putExtra("order",content.getString("order_no"));
                    intent.setClass(BettingRecordActivity.this,BettingRecordDetailActivity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        };

        BettingRecordRV.setAdapter(this, BettingRecordAdapter, getResources().getColor(R.color.topcolor),
                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {


                        BettingRecordAPI.BettingRecord(Index.WeakIndex.get().UserInfo.getString("Token", ""));

                    }
                });


    }


    private AdapterView.OnItemSelectedListener StatusSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            switch (String.valueOf(OrderStatus.getSelectedItem())) {
                case "全部":
                    Status = "all";
                    break;
                case "等待處理":
                    Status = "StoreProcessing";
                    break;
                case "出單完成":
                    Status = "done";
                    break;
                case "已派彩":
                    Status = "Purse";
                    break;
                case "未中獎":
                    Status = "Lose";
                    break;
                case "已取消":
                    Status = "Cancel";
                    break;
            }
            //BettingRecordAPI.BettingRecord(Index.WeakIndex.get().UserInfo.getString("Token", ""));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

}
