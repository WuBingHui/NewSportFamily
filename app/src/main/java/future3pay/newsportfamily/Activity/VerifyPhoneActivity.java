package future3pay.newsportfamily.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import future3pay.newsportfamily.API.ReSendEmailAPI;
import future3pay.newsportfamily.API.ReSendSmsAPI;
import future3pay.newsportfamily.API.SmsAPI;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;

public class VerifyPhoneActivity extends AppCompatActivity {
    public static WeakReference<VerifyPhoneActivity> WeakVerifyPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        actionbar();
        WeakVerifyPhone = new WeakReference<>(this);

        TextView VerifyPhone = findViewById(R.id.VerifyPhone);
        final EditText VerifyCode = findViewById(R.id.VerifyCode);
        VerifyPhone.setText(Index.WeakIndex.get().UserInfo.getString("Phone",""));
        Button SendVerify = findViewById(R.id.SendVerify);
        Button  CheckVerify= findViewById(R.id.CheckVerify);

        SendVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loading.start(WeakVerifyPhone.get());
                ReSendSmsAPI.ReSendSms(Index.WeakIndex.get().UserInfo.getString("Phone",""));

            }
        });


        CheckVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!VerifyCode.getText().toString().equals("")){
                    //驗證api
                    Loading.start(WeakVerifyPhone.get());
                }else{
                    ToastShow.start(VerifyPhoneActivity.this,"驗證碼欄未不得空白");
                }
            }
        });

    }
    private void actionbar(){

        //獲取ActionBar對象
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar_login, null);
        TextView actionbar_textview = (TextView) v.findViewById(R.id.actionbar_textview);
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
