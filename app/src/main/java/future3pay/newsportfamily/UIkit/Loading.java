package future3pay.newsportfamily.UIkit;

import android.app.Activity;
import android.content.Context;

import com.cy.dialog.progress.CYProgressDialog;

import future3pay.newsportfamily.Index;

public class Loading   {

    private static CYProgressDialog cyProgressDialog;


    public static void  start(final Context context){
        Index.WeakIndex.get().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(cyProgressDialog != null){
                    cyProgressDialog.dismiss();
                }
                cyProgressDialog=new CYProgressDialog(context);
                cyProgressDialog.config(0.6f,0xffffffff,20,0xffffffff,0xff2a5caa).show();
                cyProgressDialog.setCanceledOnTouchOutside(false);
            }
        });

    }

    public static void  diss(){
        Index.WeakIndex.get().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(cyProgressDialog!= null){
                    cyProgressDialog.dismiss();
                    cyProgressDialog= null;
                    System.gc();
                }
            }
        });
    }

}

