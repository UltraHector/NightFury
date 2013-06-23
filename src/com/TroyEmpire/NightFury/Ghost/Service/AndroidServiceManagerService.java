package com.TroyEmpire.NightFury.Ghost.Service;

import com.TroyEmpire.NightFury.Ghost.IService.IAndroidServiceManagerService;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.util.Log;


public class AndroidServiceManagerService implements
		IAndroidServiceManagerService {

	private static final String TAG = "com.TroyEmpire.NightFury.Ghost.Service.AndroidServiceManagerService";
	private ActivityManager manager;

	public AndroidServiceManagerService(Context context) {
		manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
	}

	@Override
	public boolean checkServiceRunningByName(String className) {
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (className.equals(service.service.getClassName())) {
				Log.i(TAG, "our service is running");
				return true;
			}
		}
		return false;
	}
}
