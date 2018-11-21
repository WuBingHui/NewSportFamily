package future3pay.newsportfamily;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import future3pay.newsportfamily.API.GoogleSignInAPI;
import future3pay.newsportfamily.Activity.LoginActivity;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;

public class GoogleLogin extends LoginActivity{




    public static void ConfigureGoogleSignIn(){

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Index.WeakIndex.get().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        Index.WeakIndex.get().mGoogleSignInClient = GoogleSignIn.getClient(Index.WeakIndex.get(), gso);

        // [START initialize_auth]
        // Initialize Firebase Auth
        Index.WeakIndex.get().mAuth = FirebaseAuth.getInstance();

        // [END initialize_auth]
    }

    // [START signin]
    public  void signIn() {

        Intent signInIntent = Index.WeakIndex.get().mGoogleSignInClient.getSignInIntent();
        LoginActivity.WeakLoginActivity.get().startActivityForResult(signInIntent, Index.WeakIndex.get().RC_SIGN_IN);

    }
    // [END signin]



    // [START auth_with_google]
    public static void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        Loading.start(LoginActivity.WeakLoginActivity.get());
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        Index.WeakIndex.get().mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.WeakLoginActivity.get(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            ///Log.d(TAG, "signInWithCredential:success");

                            GoogleSignInAPI.GoogleSignIn(acct.getIdToken());

                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            ToastShow.start(LoginActivity.WeakLoginActivity.get(),"signInWithCredential:failure");
                            GoogleLogin.signOut();
                            GoogleLogin.revokeAccess();
                            Loading.diss();
                        }

                        // [START_EXCLUDE]
                   //   Loading.diss();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]


    public static  void signOut() {

        // Firebase sign out
        Index.WeakIndex.get().mAuth.signOut();

        // Google sign out
        Index.WeakIndex.get().mGoogleSignInClient.signOut().addOnCompleteListener(Index.WeakIndex.get(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {



                    }
                });

    }

    public static void revokeAccess() {

        // Firebase sign out
        Index.WeakIndex.get().mAuth.signOut();

        // Google revoke access
        Index.WeakIndex.get().mGoogleSignInClient.revokeAccess().addOnCompleteListener(Index.WeakIndex.get(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }


}
