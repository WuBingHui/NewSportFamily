package future3pay.newsportfamily;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.cy.dialog.progress.CYProgressDialog;

public class Loading   {

  private static    CYProgressDialog cyProgressDialog;


    public static void  start(final Context context){
        new Activity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cyProgressDialog=new CYProgressDialog(context);
                cyProgressDialog.config(0.6f,0xffffffff,20,0xffffffff,0xff2a5caa).show();
                cyProgressDialog.setCanceledOnTouchOutside(false);
            }
        });

    }

    public static void  diss(){
        new Activity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cyProgressDialog.dismiss();
            }
        });
    }


}

