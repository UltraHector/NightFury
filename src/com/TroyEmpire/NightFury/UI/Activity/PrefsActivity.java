package com.TroyEmpire.NightFury.UI.Activity;

import java.util.ArrayList;

import com.TroyEmpire.NightFury.Entity.PhoneModeTimeUnit;
import com.TroyEmpire.NightFury.Enum.JwcAction;
import com.TroyEmpire.NightFury.Ghost.IService.IInitiateDataService;
import com.TroyEmpire.NightFury.Ghost.IService.IScheduleService;
import com.TroyEmpire.NightFury.Ghost.IService.ISmartPhoneViberateService;
import com.TroyEmpire.NightFury.Ghost.Service.InitiateDataService;
import com.TroyEmpire.NightFury.Ghost.Service.ScheduleService;
import com.TroyEmpire.NightFury.Ghost.Service.SmartPhoneViberateService;
import com.TroyEmpire.NightFury.UI.Fragment.UserJwcInfoDialogFragment;
import com.TroyEmpire.NightFury.Util.Util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class PrefsActivity extends FragmentActivity implements OnClickListener {

	private static final String TAG = "com.TroyEmpire.NightFury.UI.Activity.PrefsActivity";

	private IInitiateDataService initiateDataService = new InitiateDataService();
	private ISmartPhoneViberateService smartPhoneViberateService;
	private IScheduleService iScheduleService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		TextView enableSmartViberateTextView = (TextView) findViewById(R.id.id_enable_smart_viberate_textview);
		TextView updateCourseScheduleTextView = (TextView) findViewById(R.id.id_update_course_schedule);
		TextView updateExamScheduleTextView = (TextView) findViewById(R.id.id_update_exam_schedule);
		TextView updateMapDataTextView = (TextView) findViewById(R.id.id_update_map_data);
		TextView updateRestaurantDataTextView = (TextView) findViewById(R.id.id_update_restaurant_data);
		CheckBox enableSmartViberateBox = (CheckBox) findViewById(R.id.id_enable_smart_viberate_checkbox);

		enableSmartViberateTextView.setOnClickListener(this);
		updateCourseScheduleTextView.setOnClickListener(this);
		updateExamScheduleTextView.setOnClickListener(this);
		updateMapDataTextView.setOnClickListener(this);
		updateRestaurantDataTextView.setOnClickListener(this);
		enableSmartViberateBox.setOnClickListener(this);

		// 初始化智能振机服务
		iScheduleService = new ScheduleService(this);
		smartPhoneViberateService = new SmartPhoneViberateService(this);
	}

	// the method define below are used for testing
	private ArrayList<PhoneModeTimeUnit> getTestDayDate() {
		ArrayList<PhoneModeTimeUnit> timeOfClasses = new ArrayList<PhoneModeTimeUnit>();
		PhoneModeTimeUnit time;
		int[] startHourOfClass = new int[] { 21, 21, 21 };
		int[] startMinuteOfClass = new int[] { 47, 49, 22 };

		int[] endHourOfClass = new int[] { 21, 21, 21 };
		int[] endMinuteOfClass = new int[] { 48, 50, 23 };
		for (int i = 0; i < startHourOfClass.length; i++) {
			time = new PhoneModeTimeUnit();
			time.setStartHour(startHourOfClass[i]);
			time.setStartMinute(startMinuteOfClass[i]);
			time.setEndHour(endHourOfClass[i]);
			time.setEndMinute(endMinuteOfClass[i]);
			timeOfClasses.add(time);
		}
		return timeOfClasses;
	}

	// 实现标题栏的Home键
	public void btnHomeOnClick(View v) {
		startActivity(new Intent(this, MainActivity.class));
	}

	// 实现标题栏的返回键
	public void btnBackOnClick(View v) {
		this.finish();
	}

	@Override
	public void onClick(View v) {
		UserJwcInfoDialogFragment userInfoDialog;
		switch (v.getId()) {
		case R.id.id_enable_smart_viberate_checkbox:
			Log.i(TAG, "切换智能真机系统状态");
			CheckBox enableSmartViberateBox = (CheckBox) findViewById(R.id.id_enable_smart_viberate_checkbox);
			if (enableSmartViberateBox.isChecked() == true){
				smartPhoneViberateService
						.startSmartPhoneViberateService(iScheduleService
								.getDayCoursePhoneModeTimeUnits(Util
										.getWeekday()));
				Toast.makeText(this,
						"打开智能振机系统",
						Toast.LENGTH_SHORT).show();
			}
			else{
				smartPhoneViberateService.stopSmartPhoneViberateService();
				Toast.makeText(this,
						"关闭智能振机系统",
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.id_enable_smart_viberate_textview:
			break;
		case R.id.id_update_course_schedule: {
			userInfoDialog = new UserJwcInfoDialogFragment(PrefsActivity.this,
					JwcAction.UPDATE_COURSE_SCHEDULE);
			userInfoDialog.show(getSupportFragmentManager(), "getUserJwcInfo");
			break;
		}
		case R.id.id_update_exam_schedule:
			userInfoDialog = new UserJwcInfoDialogFragment(PrefsActivity.this,
					JwcAction.UPDATE_EXAM_SCHEDULE);
			userInfoDialog.show(getSupportFragmentManager(), "getUserJwcInfo");
			break;
		case R.id.id_update_map_data:
			//initiateDataService.initiateMapData(1);
			Toast.makeText(this,
					"该版本尚未实现该功能",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_update_restaurant_data:
			//initiateDataService.initiateRestaurantData(1);
			Toast.makeText(this,
					"该版本尚未实现该功能",
					Toast.LENGTH_SHORT).show();
			break;
		}

	}
}
