package com.d.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.d.activity.MainActivity;
import com.d.database.DuLieu;
import com.d.object.DanhNgon;

import java.util.ArrayList;
import java.util.Random;

public class FlyBitch extends Service {
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	private ArrayList<DanhNgon> danhNgons;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//		tvContent.setText(danhNgon.getContent());
//		tvAuthor.setText("~ "+danhNgon.getAuthor()+" ~");
//		Glide.with(activity).load(draw[random.nextInt(draw.length-1)]).into(img);
		DuLieu duLieu=new DuLieu(this);
		danhNgons=duLieu.getDanhNgon();
		registerReceiver(myBroadcastOnScrern, new IntentFilter(Intent.ACTION_SCREEN_ON));
//		registerReceiver(myBroadcastOnScrern, new IntentFilter(Intent.ACTION_SCREEN_OFF));
		return START_STICKY;
	}
	BroadcastReceiver myBroadcastOnScrern = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                Log.e("faker", " ACTION_SCREEN_ON");

				Intent intent1=new Intent(FlyBitch.this,MyService.class);
				stopService(intent1);

				Intent intentMyService=new Intent(FlyBitch.this,MyService.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable(MainActivity.DATA,getRandomDanhNgon());
				intentMyService.putExtras(bundle);
				startService(intentMyService);
			}
			else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Log.e("faker", " ACTION_SCREEN_OFF");
				Intent intent1=new Intent(FlyBitch.this,MyService.class);
				stopService(intent1);
			}
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
//		try{
//			if(myBroadcastOnScrern!=null)
//				unregisterReceiver(myBroadcastOnScrern);
//		}catch(Exception e) {}
	}

	public DanhNgon getRandomDanhNgon() {
		return danhNgons.get((new Random()).nextInt(danhNgons.size() - 1));
	}
}
