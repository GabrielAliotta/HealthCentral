package com.healthcentral.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class SplashActivity extends Activity {
	private static final int SPLASH_DURATION_MS = 3000;
	private final Handler mHandler = new Handler();
	ViewFlipper vf;
	ViewFlipper viewFlipperLogo;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(R.layout.splash_screen);
		this.mHandler.postDelayed(this.mEndSplash, SPLASH_DURATION_MS);
		this.vf = ((ViewFlipper) findViewById(R.id.appName));
		this.viewFlipperLogo = ((ViewFlipper) findViewById(R.id.logo));
		this.vf.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
		this.vf.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
		this.vf.showNext();
		this.viewFlipperLogo.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
		this.viewFlipperLogo.setOutAnimation(AnimationUtils.loadAnimation(this,	R.anim.push_left_out));
		this.viewFlipperLogo.showNext();
	}

	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		this.mEndSplash.run();
		return super.onTouchEvent(paramMotionEvent);
	}

	private Runnable mEndSplash = new Runnable() {
		public void run() {
			if (!isFinishing()) {
				mHandler.removeCallbacks(this);
				startActivity(new Intent(SplashActivity.this,
						HealthCentralActivity.class));
				finish();
			}
		};
	};
}