package com.TroyEmpire.NightFury.UI.Activity;

import com.TroyEmpire.NightFury.Constant.Constant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.Window;

public class LoadingActivity extends Activity {

	private boolean is_touched = false;
	private int delayedTime = 1500;
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_loading);
		
		preferences = this.getSharedPreferences(
				Constant.SHARED_PREFERENCE_NIGHT_FURY, Context.MODE_PRIVATE);
		Handler handler = new Handler();
		handler.postDelayed(new splashHandler(), delayedTime);
	}

	class splashHandler implements Runnable {

		@Override
		public void run() {
			if (false == is_touched) {
				startHomeActivity();
			}
		}

	}

	// 点击直接进入
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			is_touched = true;
			startHomeActivity();
		}
		return super.onTouchEvent(event);
	}
	
	private void startHomeActivity(){
		if (preferences.getBoolean(Constant.FIRST_TIME_TO_USE_KEY,
				false) == false) {
			startActivity(new Intent(getApplication(),
					IntroductionActivity.class));
			finish();
		} else {
			startActivity(new Intent(getApplication(),
					MainActivity.class));
			finish();
		}
	}

}
