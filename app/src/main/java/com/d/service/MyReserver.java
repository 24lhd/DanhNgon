package com.d.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by Faker on 9/5/2016.
 */
public class MyReserver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String s = intent.getAction();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null) {
            boolean b = activeNetInfo.isConnectedOrConnecting();
            if (b) {
//                Random random = new Random();
//                if (random.nextInt(6) == 2) {
//                    Uri uri = Uri.parse("market://details?id=com.duongstudio.videotintuc");
//                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
//                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    try {
//                        context.startActivity(goToMarket);
//                    } catch (ActivityNotFoundException e) {
//                        context.startActivity(new Intent(Intent.ACTION_VIEW,
//                                Uri.parse("http://play.google.com/store/apps/details?id=com.duongstudio.videotintuc")));
//                    }
//                }
            }
        }

    }

    private void rate() {

    }
}
