package com.d.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.d.activity.MainActivity;
import com.d.danhngon.R;

/**
 * Created by d on 28/01/2017.
 */

public class MyService extends Service {
    private int NOT_USED=10;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }
    private WindowManager windowManager;
    private View view;
    private WindowManager.LayoutParams params;

    @Override
    public void onCreate() {
        showNotify();

        super.onCreate();

    }

    private void showNotify() {
        final Intent emptyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOT_USED, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_menu_camera)
                        .setContentTitle("Danh Ngôn Sống Mỗi Ngày")
                        .setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle().bigText("content \n author")).addAction (android.R.drawable.btn_star,"Yêu thích", pendingIntent);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(10, mBuilder.build());

    }

    private void showWindow() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER;
//        params.x = 0;
//        params.y = 100;
        LayoutInflater inflater=LayoutInflater.from(this);
        view=inflater.inflate(R.layout.card_window_danh_ngon,null);
        TextView tvContent= (TextView) view.findViewById(R.id.tv_wd_content);
        TextView tvAuthor= (TextView) view.findViewById(R.id.tv_wd_author);
        ImageView img= (ImageView) view.findViewById(R.id.im_wd_bn_dn);
        Button btYeuThich= (Button) view.findViewById(R.id.bt_wd_yeu_thich);
        Button btAn= (Button) view.findViewById(R.id.bt_wd_an);
        btAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                windowManager.removeViewImmediate(view);
                stopSelf();
            }
        });
        btYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

            }
        });

        windowManager.addView(view, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (view != null) windowManager.removeView(view);
    }
}
