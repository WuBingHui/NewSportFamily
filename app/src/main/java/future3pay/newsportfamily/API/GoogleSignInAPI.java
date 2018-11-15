package future3pay.newsportfamily.API;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import future3pay.newsportfamily.Activity.LoginActivity;
import future3pay.newsportfamily.Activity.RegisterActivity;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;

public class GoogleSignInAPI extends LoginActivity{




    public static void ConfigureGoogleSignIn(){
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(LoginActivity.WeakLoginActivity.get().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        LoginActivity.WeakLoginActivity.get().mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.WeakLoginActivity.get(), gso);

        // [START initialize_auth]
        // Initialize Firebase Auth
        LoginActivity.WeakLoginActivity.get().mAuth = FirebaseAuth.getInstance();

        // [END initialize_auth]
    }

    // [START signin]
    public  void signIn() {

        Intent signInIntent = LoginActivity.WeakLoginActivity.get().mGoogleSignInClient.getSignInIntent();
        LoginActivity.WeakLoginActivity.get().startActivityForResult(signInIntent, LoginActivity.WeakLoginActivity.get().RC_SIGN_IN);

    }
    // [END signin]



    // [START auth_with_google]
    public static void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        Loading.start(LoginActivity.WeakLoginActivity.get());
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        LoginActivity.WeakLoginActivity.get().mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.WeakLoginActivity.get(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            ///Log.d(TAG, "signInWithCredential:success");

                            UserInfoAPI.UserInfo(acct.getIdToken());
                            ToastShow.start(LoginActivity.WeakLoginActivity.get(),"登入成功");

                            LoginActivity.WeakLoginActivity.get().finish();
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            ToastShow.start(LoginActivity.WeakLoginActivity.get(),"signInWithCredential:failure");
                        }

                        // [START_EXCLUDE]
                      Loading.diss();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]


    public static  void signOut() {

        // Firebase sign out
        LoginActivity.WeakLoginActivity.get().mAuth.signOut();

        // Google sign out
        LoginActivity.WeakLoginActivity.get(). mGoogleSignInClient.signOut().addOnCompleteListener(LoginActivity.WeakLoginActivity.get(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {



                    }
                });

    }

    public static void revokeAccess() {

        // Firebase sign out
        LoginActivity.WeakLoginActivity.get().mAuth.signOut();

        // Google revoke access
        LoginActivity.WeakLoginActivity.get().mGoogleSignInClient.revokeAccess().addOnCompleteListener(LoginActivity.WeakLoginActivity.get(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }


}
