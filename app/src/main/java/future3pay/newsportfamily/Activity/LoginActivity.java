package future3pay.newsportfamily.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cy.dialog.BaseDialog;

import java.lang.ref.WeakReference;

import future3pay.newsportfamily.API.LoginAPI;
import future3pay.newsportfamily.API.ResetPassWordAPI;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;

public class LoginActivity extends AppCompatActivity {

    public static WeakReference<LoginActivity> WeakLoginActivity;
    private EditText LoginAccount, LoginPassWord;
    private Button Login;
    private TextView Register, ResetPassWord;
    public BaseDialog ResetPassWordDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WeakLoginActivity = new WeakReference<>(this);




        actionbar();


        ObjectCreate();

    }



    private void actionbar(){

        //獲取ActionBar對象
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar_login, null);
        TextView actionbar_textview = (TextView) v.findViewById(R.id.actionbar_textview);
        Button back = (Button) v.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
    }


    private void ObjectCreate() {

        LoginAccount = findViewById(R.id.LoginAccount);
        LoginPassWord = findViewById(R.id.LoginPassWord);
        Login = findViewById(R.id.Login);
        Register = findViewById(R.id.Register);
        ResetPassWord = findViewById(R.id.ResetPassWord);

        Register.setOnClickListener(register);
        ResetPassWord.setOnClickListener(reset);
        Login.setOnClickListener(login);

    }


    private Button.OnClickListener login = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (!LoginAccount.getText().toString().equals("") && !LoginPassWord.getText().toString().equals("")) {

                Loading.start(LoginActivity.this);

                //打登入api
                LoginAPI.Login(LoginAccount.getText().toString(), LoginPassWord.getText().toString());
            } else {
                ToastShow.start(LoginActivity.this, "帳號或密碼不得空白");
            }
        }

    };


    private TextView.OnClickListener register = new TextView.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }

    };


    private TextView.OnClickListener reset = new TextView.OnClickListener() {
        @Override
        public void onClick(View view) {

            ResetPassWordDialog = new BaseDialog(LoginActivity.this);

            ResetPassWordDialog.config(R.layout.reset_password, true).show();

            final EditText ResetPassWordEmail = ResetPassWordDialog.findViewById(R.id.ResetPassWordEmail);
            Button SendResetPassWord = ResetPassWordDialog.findViewById(R.id.SendResetPassWord);

            SendResetPassWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Loading.start(ResetPassWordDialog.getContext());
                    if (!ResetPassWordEmail.getText().toString().equals("")) {
                        //打重設密碼 api
                        ResetPassWordAPI.ResetPassWord(ResetPassWordEmail.getText().toString());
                    } else {
                        ToastShow.start(ResetPassWordDialog.getContext(), "信箱不得空白");
                        Loading.diss();
                    }

                }
            });


        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);
    }
}
