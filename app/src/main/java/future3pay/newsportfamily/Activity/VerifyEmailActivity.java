package future3pay.newsportfamily.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import future3pay.newsportfamily.API.ReSendEmailAPI;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;

public class VerifyEmailActivity extends AppCompatActivity {
    public static WeakReference<VerifyEmailActivity> WeakVerifyEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        actionbar();
        WeakVerifyEmail = new WeakReference<>(this);

     TextView VerifyEmail = findViewById(R.id.VerifyEmail);

     if(Index.WeakIndex.get() != null){
         VerifyEmail.setText(Index.WeakIndex.get().UserInfo.getString("Email",""));
     }

     Button SendVerify = findViewById(R.id.SendVerify);

        SendVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loading.start(VerifyEmailActivity.this);

                if(Index.WeakIndex.get() != null){
                    ReSendEmailAPI.ReSendEmail(Index.WeakIndex.get().UserInfo.getString("Token",""));
                }

            }
        });

    }
    private void actionbar(){

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

}
