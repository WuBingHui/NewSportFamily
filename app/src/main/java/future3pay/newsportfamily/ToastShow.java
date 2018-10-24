package future3pay.newsportfamily;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.cy.dialog.progress.CYProgressDialog;

public class ToastShow extends  Activity {

    public static void  start(final Context context, final String message){
        new Activity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast str =  Toast.makeText(context,message,Toast.LENGTH_LONG);
                str.setGravity(Gravity.CENTER,0,0);
                str.show();
            }
        });

    }
}
