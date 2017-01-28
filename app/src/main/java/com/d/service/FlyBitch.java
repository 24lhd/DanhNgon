package com.d.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.d.danhngon.R;

public class FlyBitch extends Service {
	private WindowManager windowManager;
	private View view;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

		final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		params.gravity = Gravity.CENTER;
		params.x = 0;
		params.y = 100;
		view=new TextView(this);
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
				stopSelf();
			}
		});
//		tvContent.setText(danhNgon.getContent());
//		tvAuthor.setText("~ "+danhNgon.getAuthor()+" ~");
//		Glide.with(activity).load(draw[random.nextInt(draw.length-1)]).into(img);
		windowManager.addView(view, params);
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
		} catch (Exception e) {
		}

		return START_NOT_STICKY;
	}

	@Override
	public void onCreate() {
		super.onCreate();


	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (view != null) windowManager.removeView(view);
	}

}
