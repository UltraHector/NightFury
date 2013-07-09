package com.TroyEmpire.NightFury.UI.BroadCastReceiver;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Ghost.IService.IPhoneModeService;
import com.TroyEmpire.NightFury.Ghost.Service.PhoneModeService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.Time;
import android.util.Log;

public class ChangeToViberateClockReceiver extends BroadcastReceiver {

	private static final String TAG = "com.TroyEmpire.NightFury.UI.BroadCastReceiver.ChangeToViberateClockReceiver";
	private IPhoneModeService phoneModeService;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		phoneModeService = new PhoneModeService(context);

		// save the original phone mode
		SharedPreferences phoneModePrefereces = context.getSharedPreferences(
				Constant.SHARED_PREFERENCE_NIGHT_FURY, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = phoneModePrefereces.edit();
		editor.putInt(Constant.PHONE_MODE, phoneModeService.getPhoneMode());
		editor.commit(); // save the data

		phoneModeService.setPhoneSilentMode();
		Log.i(TAG, "把手机调成了振动模式");
	}

}
