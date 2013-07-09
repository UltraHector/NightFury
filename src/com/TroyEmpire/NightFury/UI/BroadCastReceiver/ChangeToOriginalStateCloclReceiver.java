package com.TroyEmpire.NightFury.UI.BroadCastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.Time;
import android.util.Log;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Ghost.IService.IPhoneModeService;
import com.TroyEmpire.NightFury.Ghost.Service.PhoneModeService;

public class ChangeToOriginalStateCloclReceiver extends BroadcastReceiver {

	private static final String TAG = "com.TroyEmpire.NightFury.UI.BroadCastReceiver.ChangeToOriginalStateCloclReceiver";
	private IPhoneModeService phoneModeService;

	@Override
	public void onReceive(Context context, Intent intent) {
		phoneModeService = new PhoneModeService(context);
		
		// 获取原始手机状态
		SharedPreferences phoneModePrefereces = context.getSharedPreferences(
				Constant.SHARED_PREFERENCE_NIGHT_FURY, Context.MODE_PRIVATE);
		int originalMode = phoneModePrefereces.getInt(Constant.PHONE_MODE, 0);
		
		// 恢复到原来的模式
		phoneModeService.recoverPhoneOriginalMode(originalMode);
		Log.i(TAG,"恢复到手机原来模式");

	}

}
