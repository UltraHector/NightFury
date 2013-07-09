package com.TroyEmpire.NightFury.UI.BroadCastReceiver;

import com.TroyEmpire.NightFury.Ghost.IService.IScheduleService;
import com.TroyEmpire.NightFury.Ghost.IService.ISmartPhoneViberateService;
import com.TroyEmpire.NightFury.Ghost.Service.ScheduleService;
import com.TroyEmpire.NightFury.Ghost.Service.SmartPhoneViberateService;
import com.TroyEmpire.NightFury.Util.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;

public class RefreshScheduleClockReceiver extends BroadcastReceiver {

	private static final String TAG = "com.TroyEmpire.NightFury.UI.BroadCastReceiver.RefreshScheduleClockReceiver";
	private ISmartPhoneViberateService smartPhoneViberateService;
	private IScheduleService iScheduleService;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "每天凌晨，更新今日日程");
		iScheduleService = new ScheduleService(context);
		smartPhoneViberateService = new SmartPhoneViberateService(context);

		// 先关闭之前的闹钟，再重新设置启动
		smartPhoneViberateService.stopSmartPhoneViberateService();
		smartPhoneViberateService
				.startSmartPhoneViberateService(iScheduleService
						.getDayCoursePhoneModeTimeUnits(Util.getWeekday()));
	}
}
