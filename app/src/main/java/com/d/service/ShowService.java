package com.d.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d.activity.MainActivity;
import com.d.database.DuLieu;
import com.d.object.DanhNgon;
import com.lhd.danhngon.R;

import java.util.Random;

import duong.AppLog;
import duong.ScreenShort;
/**
 * Created by d on 28/01/2017.
 */

public class ShowService extends Service {
    private int NOT_USED=10;
    private DuLieu duLieu;
    private DanhNgon danhNgon;
    private String image;

    private RelativeLayout relativeLayout;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        duLieu=new DuLieu(this);
        Log.e("faker","onStartCommand MyService");
        danhNgon= (DanhNgon) intent.getExtras().getSerializable(MainActivity.DATA);
        image=intent.getExtras().getString(MainActivity.IMAGE);
        if (danhNgon==null) return START_NOT_STICKY;
        if ((new AppLog()).getValueByName(this,"sw_op","sw_window").contains("1")) showWindow();
        if ((new AppLog()).getValueByName(this,"sw_op","sw_notify").contains("1")) showNotify();
        return START_NOT_STICKY;
    }
    private WindowManager windowManager;
    private View view;
    private WindowManager.LayoutParams params;
    private void showNotify() {
        Intent intentLike = new Intent(this, MainActivity.class);
        intentLike.putExtra("like",danhNgon);
        Intent intentOpen = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOT_USED, intentLike, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntentNull = PendingIntent.getActivity(this, NOT_USED, intentOpen, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_menu_camera)
                        .setContentTitle("Danh Ngôn Sống Mỗi Ngày")
                        .setContentIntent(pendingIntentNull).setColor(getResources().getColor(R.color.colorPrimary))
                        .setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle().bigText
                                (danhNgon.getContent()+" \n"+"~ "+danhNgon.getAuthor()+" ~"))
                        .addAction (android.R.drawable.btn_star,"Yêu thích", pendingIntent);
//        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(10, mBuilder.build());
//        stopSelf();

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
        tvContent.setText(danhNgon.getContent());
        TextView tvAuthor= (TextView) view.findViewById(R.id.tv_wd_author);
        tvAuthor.setText(danhNgon.getAuthor());
        ImageView img= (ImageView) view.findViewById(R.id.im_wd_bn_dn);
        Random random=new Random();
        relativeLayout= (RelativeLayout) view.findViewById(R.id.view_wd_dn);
        Glide.with(this).load(image).into(img);
        Button btYeuThich= (Button) view.findViewById(R.id.bt_wd_yeu_thich);
        Button btAn= (Button) view.findViewById(R.id.bt_wd_an);
        Button btChiaSe= (Button) view.findViewById(R.id.bt_wd_chia_se_anh);
        btAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopSelf();
            }
        });
        btChiaSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenShort screenShort=new ScreenShort();
                screenShort.shareImageByFile(screenShort.takeSreenShortByView(relativeLayout,ShowService.this),ShowService.this);
                stopSelf();

            }
        });
        btYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duLieu.updateDanhNgonFarvorite(danhNgon);
                stopSelf();

            }
        });

        try {
            view.setOnTouchListener(new View.OnTouchListener() {
                private WindowManager.LayoutParams paramsF = params;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = paramsF.x;
                            initialY = paramsF.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
                            paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(view, paramsF);
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e) {}
        windowManager.addView(view, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (view != null) windowManager.removeView(view);
    }
}
