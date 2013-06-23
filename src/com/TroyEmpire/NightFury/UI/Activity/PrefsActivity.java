package com.TroyEmpire.NightFury.UI.Activity;

import com.TroyEmpire.NightFury.Constant.Constant;
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

		// Set the title bar text as this activity label
		TextView titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText(R.string.setting_label);

		TextView enableSmartViberateTextView = (TextView) findViewById(R.id.id_enable_smart_viberate_textview);
		TextView updateCourseScheduleTextView = (TextView) findViewById(R.id.id_update_course_schedule);
		TextView updateExamScheduleTextView = (TextView) findViewById(R.id.id_update_exam_schedule);
		TextView aboutusTextView = (TextView) findViewById(R.id.aboutus_tv);
		TextView shareTextView = (TextView) findViewById(R.id.setting_share_tv);
		// TextView updateMapDataTextView = (TextView)
		// findViewById(R.id.id_update_map_data);
		// TextView updateRestaurantDataTextView = (TextView)
		// findViewById(R.id.id_update_restaurant_data);
		CheckBox enableSmartViberateBox = (CheckBox) findViewById(R.id.id_enable_smart_viberate_checkbox);

		enableSmartViberateTextView.setOnClickListener(this);
		updateCourseScheduleTextView.setOnClickListener(this);
		updateExamScheduleTextView.setOnClickListener(this);
		// updateMapDataTextView.setOnClickListener(this);
		// updateRestaurantDataTextView.setOnClickListener(this);
		enableSmartViberateBox.setOnClickListener(this);
		aboutusTextView.setOnClickListener(this);
		shareTextView.setOnClickListener(this);

		// 初始化智能振机服务
		iScheduleService = new ScheduleService(this);
		smartPhoneViberateService = new SmartPhoneViberateService(this);
		enableSmartViberateBox.setChecked(smartPhoneViberateService
				.isSVSIsOn());
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
			if (enableSmartViberateBox.isChecked() == true) {
				smartPhoneViberateService
						.startSmartPhoneViberateService(iScheduleService
								.getDayCoursePhoneModeTimeUnits(Util
										.getWeekday()));
				smartPhoneViberateService.setWhetherSVSIsOn(true);
				Toast.makeText(this, "打开智能振机系统", Toast.LENGTH_SHORT).show();
			} else {
				smartPhoneViberateService.stopSmartPhoneViberateService();
				smartPhoneViberateService.setWhetherSVSIsOn(false);
				Toast.makeText(this, "关闭智能振机系统", Toast.LENGTH_SHORT).show();
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
		case R.id.setting_share_tv:
			Intent it = new Intent(Intent.ACTION_SEND);
			it.setType("text/plain");
			it.putExtra(Intent.EXTRA_SUBJECT, "分享");
			it.putExtra(Intent.EXTRA_TEXT, Constant.SHARE_DESCRIPTION
					+ Constant.DOWNLOAD_URL);
			startActivity(Intent.createChooser(it, "选择分享方式"));
			break;
		case R.id.aboutus_tv:
			startActivity(new Intent(this, AboutUsActivity.class));
			break;
		// case R.id.id_update_map_data:
		// //initiateDataService.initiateMapData(1);
		// Toast.makeText(this,
		// "该版本尚未实现该功能",
		// Toast.LENGTH_SHORT).show();
		// break;
		// case R.id.id_update_restaurant_data:
		// //initiateDataService.initiateRestaurantData(1);
		// Toast.makeText(this,
		// "该版本尚未实现该功能",
		// Toast.LENGTH_SHORT).show();
		// break;
		}

	}
}
