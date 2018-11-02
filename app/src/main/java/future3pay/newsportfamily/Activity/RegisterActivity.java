package future3pay.newsportfamily.Activity;

import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import future3pay.newsportfamily.API.RegisterAPI;
import future3pay.newsportfamily.API.SmsAPI;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import future3pay.newsportfamily.VerifyData;

public class RegisterActivity extends AppCompatActivity {
    public EditText Account, PassWord, AgainPassWord, UserName, Email, BankAccount, InviteCode, Phone, VerifyCode;
    private TextView AccountNotice, PassWordNotice, AgainPassWordNotice, UserNameNotice, EmailNotice, BankAccountNotice, InviteCodeNotice, PhoneNotice, VerifyCodeNotice;
    private Spinner BankList;
    private Button SendSms, SendRegister;
    private CountDownTimer SendTimer;
    public static WeakReference<RegisterActivity> WeakRegisterActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        actionbar();

        WeakRegisterActivity = new WeakReference<>(this);



        ObjectCreate();

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



    private void ObjectCreate() {


        PassWordNotice = findViewById(R.id.PassWordNotice);
        AgainPassWordNotice = findViewById(R.id.AgainPassWordNotice);
        UserNameNotice = findViewById(R.id.UserNameNotice);
        EmailNotice = findViewById(R.id.EmailNotice);
        BankAccountNotice = findViewById(R.id.BankAccountNotice);
        PhoneNotice = findViewById(R.id.PhoneNotice);


        Account = findViewById(R.id.Account);
        PassWord = findViewById(R.id.PassWord);
        AgainPassWord = findViewById(R.id.AgainPassWord);
        UserName = findViewById(R.id.UserName);
        Email = findViewById(R.id.Email);
        BankAccount = findViewById(R.id.BankAccount);
        InviteCode = findViewById(R.id.InviteCode);
        Phone = findViewById(R.id.Phone);
        VerifyCode = findViewById(R.id.VerifyCode);

        BankList = findViewById(R.id.BankList);

        SendSms = findViewById(R.id.SendSms);
        SendRegister = findViewById(R.id.SendRegister);


        PassWord.setOnFocusChangeListener(PassWordTextWatcher);
        AgainPassWord.setOnFocusChangeListener(AgainPassWordWatcher);
        UserName.setOnFocusChangeListener(UserNameWatcher);
        Email.setOnFocusChangeListener(EmailWatcher);
        BankAccount.setOnFocusChangeListener(BankAccountWatcher);
        Phone.setOnFocusChangeListener(PhoneWatcher);

        SendSms.setOnClickListener(Sms);

        SendRegister.setOnClickListener(Register);
    }


    private Button.OnClickListener Register = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {

            Loading.start(RegisterActivity.this);

            if (!Account.getText().toString().equals("")) {
                if (!PassWord.getText().toString().equals("")) {
                    if (!AgainPassWord.getText().toString().equals("")) {
                        if (!UserName.getText().toString().equals("")) {
                            if (!Email.getText().toString().equals("")) {
                                if (!BankAccount.getText().toString().equals("")) {
                                    if (!Phone.getText().toString().equals("")) {
                                        if (!VerifyCode.getText().toString().equals("")) {
                                            //打註冊api

                                            RegisterAPI.RegisterApi(Account.getText().toString(),PassWord.getText().toString(),AgainPassWord.getText().toString(),UserName.getText().toString(),Email.getText().toString(),Phone.getText().toString(),BankList.getSelectedItem().toString(),BankAccount.getText().toString(),VerifyCode.getText().toString(),InviteCode.getText().toString());

                                        } else {

                                            ToastShow.start(RegisterActivity.this,"驗證碼不得空白");
                                            VerifyCode.requestFocus();
                                            Loading.diss();
                                        }
                                    } else {

                                        ToastShow.start(RegisterActivity.this,"手機號碼不得空白");
                                        Phone.requestFocus();
                                        Loading.diss();
                                    }
                                } else {

                                    ToastShow.start(RegisterActivity.this,"銀行帳號不得空白");
                                    BankAccount.requestFocus();
                                    Loading.diss();
                                }
                            } else {

                                ToastShow.start(RegisterActivity.this,"電子郵件不得空白");
                                Email.requestFocus();
                                Loading.diss();
                            }
                        } else {

                            ToastShow.start(RegisterActivity.this,"姓名不得空白");
                            UserName.requestFocus();
                            Loading.diss();
                        }
                    } else {
                        ToastShow.start(RegisterActivity.this,"確認密碼不得空白");
                        AgainPassWord.requestFocus();
                        Loading.diss();
                    }
                } else {
                    ToastShow.start(RegisterActivity.this,"密碼不得空白");
                    PassWord.requestFocus();
                    Loading.diss();
                }
            } else {
                ToastShow.start(RegisterActivity.this,"帳號不得空白");
                Account.requestFocus();
                Loading.diss();
            }

        }

    };

    private Button.OnClickListener Sms = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {

            if (!Account.getText().toString().equals("")) {
                if (!PassWord.getText().toString().equals("")) {
                    if (!AgainPassWord.getText().toString().equals("")) {
                        if (!UserName.getText().toString().equals("")) {
                            if (!Email.getText().toString().equals("")) {
                                if (!BankAccount.getText().toString().equals("")) {
                                    if (!Phone.getText().toString().equals("")) {
                                                if (VerifyData.VerifyPhone(Phone.getText().toString())) {
                                                    SendSms.setEnabled(false);
                                                    Phone.setEnabled(false);

                                                    //打簡訊 api
                                                    SmsAPI.SmsApi(Phone.getText().toString());


                                                    SendTimer = new CountDownTimer(300000, 1000) {
                                                        int i = 300;

                                                        @Override
                                                        public void onFinish() {
                                                            //時間到的時候
                                                            SendSms.setEnabled(true);
                                                            Phone.setEnabled(true);
                                                            SendSms.setText("發送驗證碼");
                                                            SendTimer.cancel();
                                                        }

                                                        @Override
                                                        public void onTick(long millisUntilFinished) {

                                                            SendSms.setText(String.valueOf(i));
                                                            i--;
                                                        }
                                                    }.start();
                                                } else {
                                                    ToastShow.start(RegisterActivity.this,"手機格式不正確");
                                                }

                                    } else {

                                        ToastShow.start(RegisterActivity.this,"手機號碼不得空白");
                                        Phone.requestFocus();

                                    }
                                } else {

                                    ToastShow.start(RegisterActivity.this,"銀行帳號不得空白");
                                    BankAccount.requestFocus();

                                }
                            } else {

                                ToastShow.start(RegisterActivity.this,"電子郵件不得空白");
                                Email.requestFocus();

                            }
                        } else {

                            ToastShow.start(RegisterActivity.this,"姓名不得空白");
                            UserName.requestFocus();

                        }
                    } else {

                        ToastShow.start(RegisterActivity.this,"確認密碼不得空白");
                        AgainPassWord.requestFocus();

                    }
                } else {

                    ToastShow.start(RegisterActivity.this,"密碼不得空白");
                    PassWord.requestFocus();

                }
            } else {
                ToastShow.start(RegisterActivity.this,"帳號不得空白");
                Account.requestFocus();

            }

        }
    };
    private EditText.OnFocusChangeListener PassWordTextWatcher = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                // 得到焦點後處理的內容
            } else {
                // 失去焦點後處理的內容
                if (!VerifyData.VerifyLength(PassWord.getText().toString(), 6)) {
                    PassWordNotice.setText("密碼最少六位數");

                    PassWord.setText("");
                } else {
                    PassWordNotice.setText("OK!");
                }
            }

        }
    };

    private EditText.OnFocusChangeListener AgainPassWordWatcher = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                // 得到焦點後處理的內容
            } else {
                // 失去焦點後處理的內容
                if (!AgainPassWord.getText().toString().equals("")) {
                    if (!AgainPassWord.getText().toString().equals(PassWord.getText().toString())) {
                        AgainPassWordNotice.setText("確認密碼與密碼不一致");

                        AgainPassWord.setText("");
                    } else {
                        AgainPassWordNotice.setText("OK!");
                    }
                }
            }

        }
    };

    private EditText.OnFocusChangeListener UserNameWatcher = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                // 得到焦點後處理的內容
            } else {
                // 失去焦點後處理的內容
                if (!VerifyData.VerifyChiness(UserName.getText().toString())) {
                    UserNameNotice.setText("僅接受中文格式");

                    UserName.setText("");
                } else {
                    UserNameNotice.setText("OK!");
                }
            }

        }
    };


    private EditText.OnFocusChangeListener EmailWatcher = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                // 得到焦點後處理的內容
            } else {
                // 失去焦點後處理的內容
                if (!VerifyData.VerifyEmail(Email.getText().toString())) {
                    EmailNotice.setText("信箱格式錯誤");
                    Email.setText("");
                } else {
                    EmailNotice.setText("OK!");
                }
            }

        }
    };


    private EditText.OnFocusChangeListener BankAccountWatcher = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                // 得到焦點後處理的內容
            } else {
                // 失去焦點後處理的內容
                if (!VerifyData.VerifyLength(BankAccount.getText().toString(), 10)) {
                    BankAccountNotice.setText("銀行帳號格式不正確");
                    BankAccount.setText("");
                } else {
                    BankAccountNotice.setText("OK!");
                }
            }

        }
    };


    private EditText.OnFocusChangeListener PhoneWatcher = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                // 得到焦點後處理的內容
            } else {
                // 失去焦點後處理的內容
                if (!VerifyData.VerifyPhone(Phone.getText().toString())) {
                    PhoneNotice.setText("手機格式不正確");
                    Phone.setText("");
                } else {
                    PhoneNotice.setText("OK!");
                }
            }

        }
    };


}
