package com.d.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class FlyBitch extends Service {
	private WindowManager windowManager;
	private View view;
	private WindowManager.LayoutParams params;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//		tvContent.setText(danhNgon.getContent());
//		tvAuthor.setText("~ "+danhNgon.getAuthor()+" ~");
//		Glide.with(activity).load(draw[random.nextInt(draw.length-1)]).into(img);

//		try {
//			view.setOnTouchListener(new View.OnTouchListener() {
//				private WindowManager.LayoutParams paramsF = params;
//				private int initialX;
//				private int initialY;
//				private float initialTouchX;
//				private float initialTouchY;
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					switch (event.getAction()) {
//						case MotionEvent.ACTION_DOWN:
//							initialX = paramsF.x;
//							initialY = paramsF.y;
//							initialTouchX = event.getRawX();
//							initialTouchY = event.getRawY();
//							break;
//						case MotionEvent.ACTION_UP:
//							break;
//						case MotionEvent.ACTION_MOVE:
//							paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
//							paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
//							windowManager.updateViewLayout(view, paramsF);
//							break;
//					}
//					return false;
//				}
//			});
//		} catch (Exception e) {
//		}
		registerReceiver(myBroadcastOnScrern, new IntentFilter(Intent.ACTION_SCREEN_ON));
//		registerReceiver(myBroadcastOnScrern, new IntentFilter(Intent.ACTION_SCREEN_OFF));
		return START_STICKY;
	}
	BroadcastReceiver myBroadcastOnScrern = new BroadcastReceiver() {
		//When Event is published, onReceive method is called
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                Log.e("faker", " ACTION_SCREEN_ON");
//				startParser();
				Intent intent1=new Intent(FlyBitch.this,MyService.class);
				startService(intent1);

			}else
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Log.e("faker", " ACTION_SCREEN_OFF");
//				startParser();
//				Intent intent1=new Intent(FlyBitch.this,MyService.class);
//				stopService(intent1);
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
}
