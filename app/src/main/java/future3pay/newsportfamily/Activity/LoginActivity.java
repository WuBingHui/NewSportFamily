package future3pay.newsportfamily.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cy.dialog.BaseDialog;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.ref.WeakReference;

import future3pay.newsportfamily.FacebookLogin;
import future3pay.newsportfamily.GoogleLogin;
import future3pay.newsportfamily.API.LoginAPI;
import future3pay.newsportfamily.API.ResetPassWordAPI;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;

import static com.google.android.gms.auth.api.signin.GoogleSignIn.getClient;

public class LoginActivity extends AppCompatActivity {

    public static WeakReference<LoginActivity> WeakLoginActivity;
    private EditText LoginAccount, LoginPassWord;
    private Button Login,GoogleSignInBtn;
    public LoginButton FacebookSignInBtn;
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


    private void ObjectCreate() {

        LoginAccount = findViewById(R.id.LoginAccount);
        LoginPassWord = findViewById(R.id.LoginPassWord);
        Login = findViewById(R.id.Login);
        Register = findViewById(R.id.Register);
        ResetPassWord = findViewById(R.id.ResetPassWord);
        GoogleSignInBtn= findViewById(R.id.GoogleSignInBtn);
        FacebookSignInBtn= findViewById(R.id.FacebookSignInBtn);
        Register.setOnClickListener(register);
        ResetPassWord.setOnClickListener(reset);
        Login.setOnClickListener(login);
        GoogleSignInBtn.setOnClickListener(login);
        FacebookSignInBtn.setOnClickListener(login);

    }


    private Button.OnClickListener login = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {

            switch (view.getId()){
                    case R.id.Login:

                        if (!LoginAccount.getText().toString().equals("") && !LoginPassWord.getText().toString().equals("")) {

                            Loading.start(LoginActivity.this);

                            //打登入api
                            LoginAPI.Login(LoginAccount.getText().toString(), LoginPassWord.getText().toString());
                        } else {
                            ToastShow.start(LoginActivity.this, "帳號或密碼不得空白");
                        }

                        break;
                    case R.id.GoogleSignInBtn:

                        GoogleLogin googleLogin = new GoogleLogin();

                        googleLogin.signIn();

                        break;
                case R.id.FacebookSignInBtn:

                   FacebookSignInBtn.setReadPermissions("email", "public_profile");
                   FacebookSignInBtn.registerCallback(Index.WeakIndex.get().mCallbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            // Log.d(TAG, "facebook:onSuccess:" + loginResult);
                            FacebookLogin.SignIn(loginResult.getAccessToken());

                        }

                        @Override
                        public void onCancel() {
                            // Log.d(TAG, "facebook:onCancel");
                            // [START_EXCLUDE]
                            //updateUI(null);
                            // [END_EXCLUDE]
                        }

                        @Override
                        public void onError(FacebookException error) {

                            ToastShow.start(LoginActivity.WeakLoginActivity.get(), String.valueOf(error));
                            //Log.d(TAG, "facebook:onError", error);
                            // [START_EXCLUDE]
                            //updateUI(null);
                            // [END_EXCLUDE]
                        }

                    });

                    break;

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

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Index.WeakIndex.get().RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                GoogleLogin.firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                // [START_EXCLUDE]
                ToastShow.start(LoginActivity.WeakLoginActivity.get(),"Google Sign In failed");
                // [END_EXCLUDE]
            }
        }else{
            Index.WeakIndex.get().mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    // [END onactivityresult]



}
