package future3pay.newsportfamily;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import future3pay.newsportfamily.API.FacebookLoginAPI;
import future3pay.newsportfamily.Activity.LoginActivity;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;

public class FacebookLogin extends LoginActivity {


    public static void ConfigureFacebookSignIn(){

        Index.WeakIndex.get().mCallbackManager = CallbackManager.Factory.create();

    }


    // [START auth_with_facebook]
    public static void SignIn(final AccessToken token) {

        // [START_EXCLUDE silent]
        Loading.start(LoginActivity.WeakLoginActivity.get());
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Index.WeakIndex.get().mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Index.WeakIndex.get(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "signInWithCredential:success");
                            FacebookLoginAPI.FacebookLogin(token.getToken());
                          //  FirebaseUser user = mAuth.getCurrentUser();
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                           // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            ToastShow.start(LoginActivity.WeakLoginActivity.get(), "Authentication failed.");
                            Loading.diss();
                           // Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
                                    //Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_facebook]


    public static void signOut() {
        Index.WeakIndex.get().mAuth.signOut();
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        //updateUI(null);
    }


}
