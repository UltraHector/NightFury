package com.TroyEmpire.NightFury.Ghost.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.Time;
import android.util.Log;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Entity.PhoneModeTimeUnit;
import com.TroyEmpire.NightFury.Ghost.IService.ISmartPhoneViberateService;
import com.TroyEmpire.NightFury.UI.BroadCastReceiver.ChangeToOriginalStateCloclReceiver;
import com.TroyEmpire.NightFury.UI.BroadCastReceiver.RefreshScheduleClockReceiver;
import com.TroyEmpire.NightFury.UI.BroadCastReceiver.ChangeToViberateClockReceiver;

public class SmartPhoneViberateService implements ISmartPhoneViberateService {

	private static final String TAG = "com.TroyEmpire.NightFury.Ghost.Service.SmartPhoneViberateService";

	private Context context;
	private AlarmManager alarmMgr;
	private List<PhoneModeTimeUnit> phoneModeTimeUnits;
	private List<PendingIntent> viberateScheduleClockPendingIntents;
	private PendingIntent refreshScheduleClockPendingIntent;
	private List<PendingIntent> recoverScheduleClockPendingIntents;

	public SmartPhoneViberateService(Context context) {
		this.context = context;
		alarmMgr = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
	}

	@Override
	public void startSmartPhoneViberateService(
			List<PhoneModeTimeUnit> phoneModeTimeUnits) {
		Log.i(TAG, "启动智能振机系统");
		// initialize the intents
		viberateScheduleClockPendingIntents = new ArrayList<PendingIntent>();
		recoverScheduleClockPendingIntents = new ArrayList<PendingIntent>();
		refreshScheduleClockPendingIntent = getRefreshScheduleClockPendingIntent(0);

		for (int i = 0; i < phoneModeTimeUnits.size(); i++) {
			viberateScheduleClockPendingIntents
					.add(getViberateScheduleClockPendingIntent(i));
			recoverScheduleClockPendingIntents
					.add(getRecoverScheduleClockPendingIntent(i));
		}
		savePendingIntentsNumbers(phoneModeTimeUnits.size());
		this.phoneModeTimeUnits = phoneModeTimeUnits;

		setTheAlarmToRefreshTheSchdule();
		setTheAlarmAccordingToTimeUnits();
	}

	private void savePendingIntentsNumbers(int number) {
		// save the original phone mode
		SharedPreferences phoneModePrefereces = context.getSharedPreferences(
				Constant.SHARED_PREFERENCE_NIGHT_FURY, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = phoneModePrefereces.edit();
		editor.putInt(Constant.PHONE_MODE_PENDING_INTENTS_NUMBER, number);
		editor.commit(); // save the data
	}

	@Override
	public void stopSmartPhoneViberateService() {
		Log.i(TAG, "关闭智能振机系统");
		// save the original phone mode
		SharedPreferences phoneModePrefereces = context.getSharedPreferences(
				Constant.SHARED_PREFERENCE_NIGHT_FURY, Context.MODE_PRIVATE);
		int number = phoneModePrefereces.getInt(Constant.PHONE_MODE_PENDING_INTENTS_NUMBER,0);
		// 与上面的intent匹配（filterEquals(intent)）的闹钟会被取消
		alarmMgr.cancel(getRefreshScheduleClockPendingIntent(0));
		for (int i = 0; i < number; i++) {
			
			alarmMgr.cancel(getViberateScheduleClockPendingIntent(i));
			alarmMgr.cancel(getRecoverScheduleClockPendingIntent(i));
		}
		refreshScheduleClockPendingIntent = null;
		viberateScheduleClockPendingIntents = null;
		recoverScheduleClockPendingIntents = null;

	}

	private void setTheAlarmToRefreshTheSchdule() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, 1);
		alarmMgr.set(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(),
				refreshScheduleClockPendingIntent);

	}

	private void setTheAlarmAccordingToTimeUnits() {
		for (int i = 0; i < phoneModeTimeUnits.size(); i++) {
			PhoneModeTimeUnit phoneModeTimeUnit = phoneModeTimeUnits.get(i);

			Time timeNow = new Time();
			timeNow.setToNow();
			Time timeStart = new Time();
			timeStart.setToNow();
			Time timeEnd = new Time();
			timeEnd.setToNow();

			if (phoneModeTimeUnit != null) {
				timeStart.set(0, phoneModeTimeUnit.getStartMinute(),
						phoneModeTimeUnit.getStartHour(), timeNow.monthDay,
						timeNow.month, timeNow.year);
				timeEnd.set(0, phoneModeTimeUnit.getEndMinute(),
						phoneModeTimeUnit.getEndHour(), timeNow.monthDay,
						timeNow.month, timeNow.year);

				// 如果某一项日程开始时间还未到来，则正常设置开始和结束时间
				if (timeNow.before(timeStart)) {
					setTheClockForScheduleUnits(phoneModeTimeUnit, timeNow, i);
				} else if (timeNow.before(timeEnd) && timeNow.after(timeStart)) {
					// 如果日程已经开始，但是还没有结束则马上调为振机
					phoneModeTimeUnit.setStartHour(0);
					phoneModeTimeUnit.setStartMinute(0);
					setTheClockForScheduleUnits(phoneModeTimeUnit, timeNow, i);
				}
				// 如果某一项日程结束时间已经过去则忽略

			}
		}
	}

	private void setTheClockForScheduleUnits(
			PhoneModeTimeUnit phoneModeTimeUnit, Time time, int id) {
		time.set(0, phoneModeTimeUnit.getStartMinute(),
				phoneModeTimeUnit.getStartHour(), time.monthDay, time.month,
				time.year);
		alarmMgr.set(AlarmManager.RTC_WAKEUP, time.toMillis(true),
				viberateScheduleClockPendingIntents.get(id));
		time.set(0, phoneModeTimeUnit.getEndMinute(),
				phoneModeTimeUnit.getEndHour(), time.monthDay, time.month,
				time.year);
		Log.i(TAG, time.format2445());
		alarmMgr.set(AlarmManager.RTC_WAKEUP, time.toMillis(true),
				recoverScheduleClockPendingIntents.get(id));
	}

	private PendingIntent getViberateScheduleClockPendingIntent(int id) {
		Intent scheduleClockIntent = new Intent(context,
				ChangeToViberateClockReceiver.class);
		PendingIntent scheduleClockPendingIntent = PendingIntent.getBroadcast(
				context, id, scheduleClockIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		return scheduleClockPendingIntent;
	}

	private PendingIntent getRefreshScheduleClockPendingIntent(int id) {
		Intent intent = new Intent(context, RefreshScheduleClockReceiver.class);
		PendingIntent refreshScheduleClockIntent = PendingIntent.getBroadcast(
				context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return refreshScheduleClockIntent;
	}

	private PendingIntent getRecoverScheduleClockPendingIntent(int id) {
		Intent intent = new Intent(context,
				ChangeToOriginalStateCloclReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return pendingIntent;
	}

}
