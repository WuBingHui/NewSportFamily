package future3pay.newsportfamily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cy.dialog.BaseDialog;

import java.lang.ref.WeakReference;

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

        ObjectCreate();

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


}
