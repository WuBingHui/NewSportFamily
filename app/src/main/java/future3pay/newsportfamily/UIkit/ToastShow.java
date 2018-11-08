package future3pay.newsportfamily.UIkit;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.cy.dialog.progress.CYProgressDialog;

public class ToastShow extends  Activity {
   private static Toast str;
    public static void  start(final Context context, final String message){
        new Activity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(str != null){
                    str.cancel();
                }
                 str =  Toast.makeText(context,message,Toast.LENGTH_SHORT);
                str.setGravity(Gravity.CENTER,0,0);
                str.show();
            }
        });

    }
}
