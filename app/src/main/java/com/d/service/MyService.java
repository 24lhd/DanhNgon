package com.d.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import duong.ChucNangPhu;

/**
 * Created by d on 28/01/2017.
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        ChucNangPhu.showLog("onStartCommand");
        if (intent==null){
            ChucNangPhu.showLog("null");
//            WindowManager.LayoutParams p = new WindowManager.LayoutParams(
//                    // Shrink the window to wrap the content rather than filling the screen
//                    WindowManager.LayoutParams.WRAP_CONTENT,
//                    WindowManager.LayoutParams.WRAP_CONTENT,
//                    // Display it on top of other application windows, but only for the current user
//                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
//                    // Don't let it grab the input focus
//                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                    // Make the underlying application window visible through any transparent parts
//                    PixelFormat.TRANSLUCENT);
//
//            p.gravity = Gravity.TOP | Gravity.RIGHT;
//            p.x = 0;
//            p.y = 100;
//            EditText editText=new EditText(this);
//            WindowManager windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
//            windowManager.addView(editText, p);
            builder.setTitle("sssssssss");
            builder.setMessage("Sssssssssssssss");
            builder.show();
            ChucNangPhu.showLog("sssssssssssssssss");
        }


        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Toast.makeText(getBaseContext(),"onCreate", Toast.LENGTH_LONG).show();
//        EditText editText=new EditText(this);
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
//                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
//                PixelFormat.TRANSLUCENT);
//        params.gravity = Gravity.RIGHT | Gravity.TOP;
//        params.setTitle("Load Average");
//        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
//        wm.addView(editText, params);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        // ATTENTION: GET THE X,Y OF EVENT FROM THE PARAMETER
//        // THEN CHECK IF THAT IS INSIDE YOUR DESIRED AREA
//
//        Toast.makeText(this,"onTouchEvent", Toast.LENGTH_LONG).show();
//        return true;
//    }
}
