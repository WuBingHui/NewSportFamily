package future3pay.newsportfamily.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;

public class UserInfoActivity extends AppCompatActivity {
    private TextView account, phone, email, bank, bank_code, perssion, store, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


        actionbar();

        account = findViewById(R.id.account);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        bank = findViewById(R.id.bank);
        bank_code = findViewById(R.id.bank_code);
        perssion = findViewById(R.id.perssion);
        store = findViewById(R.id.store);
        name = findViewById(R.id.name);


        account.setText(Index.WeakIndex.get().UserInfo.getString("UserName",""));
        phone.setText(Index.WeakIndex.get().UserInfo.getString("Phone",""));
        email.setText(Index.WeakIndex.get().UserInfo.getString("Email",""));
        bank.setText(Index.WeakIndex.get().UserInfo.getString("BankAccount",""));
        bank_code.setText(Index.WeakIndex.get().UserInfo.getString("BankName",""));
        perssion.setText(Index.WeakIndex.get().UserInfo.getString("Permission",""));
        store.setText(Index.WeakIndex.get().UserInfo.getString("BelongStore",""));
        name.setText(Index.WeakIndex.get().UserInfo.getString("Name",""));


    }

    private void actionbar() {

        //獲取ActionBar對象
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar_login, null);
        TextView actionbar_textview = (TextView) v.findViewById(R.id.actionbar_textview);
        actionbar_textview.setText("個人資料");
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
