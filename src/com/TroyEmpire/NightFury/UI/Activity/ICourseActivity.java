package com.TroyEmpire.NightFury.UI.Activity;

/**
 * 
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Entity.DayCourseUnit;
import com.TroyEmpire.NightFury.Enum.JwcAction;
import com.TroyEmpire.NightFury.Enum.WeekDay;
import com.TroyEmpire.NightFury.Ghost.IService.IScheduleService;
import com.TroyEmpire.NightFury.Ghost.Service.ScheduleService;
import com.TroyEmpire.NightFury.UI.Fragment.UserJwcInfoDialogFragment;

import android.app.TabActivity;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;

public class ICourseActivity extends TabActivity implements
		OnCheckedChangeListener {

	private TextView titleText;
	private TabHost mHost;
	private Intent scheduleIntent;
	private Intent courseIntent;
	private Intent gradeIntent;
	private Intent examIntent;
	private RadioButton radioBtn1;
	private RadioButton radioBtn2;
	private RadioButton radioBtn3;
	private RadioButton radioBtn4;

	IScheduleService scheduleService = new ScheduleService(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("initRadiosed", "saved Instance");
		setContentView(R.layout.activity_icourse);
		Log.d("initRadiosed", "set ContentView");

		titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText(R.string.icourse_schedule);

		// No JWC information has been saved
//		if (!scheduleService.isAllScheduleSaved()
//				&& !scheduleService.isUserJwcPasswordHasBeenSaved()) {
//			startActivity(new Intent(this, TestActivity.class));
//		}

		// ~~~~~~~~~~~~ 初始化
		this.scheduleIntent = new Intent(this, ICourseScheduleActivity.class);
		this.courseIntent = new Intent(this, ICourseTableActivity.class);
		this.gradeIntent = new Intent(this, ICourseDisplayGradeActivity.class);
		this.examIntent = new Intent(this, ICourseExamTableActivity.class);

		initRadios();
		Log.d("initRadiosed", "init Radiosed");
		setupIntent();
		Log.d("setupIntent", "setupIntent");
		
	}

	/**
	 * 初始化底部按钮
	 */
	private void initRadios() {
		radioBtn1 = ((RadioButton) findViewById(R.id.radio_btn1));
		radioBtn1.setOnCheckedChangeListener(this);
		radioBtn1.setPressed(true);
		radioBtn2 = ((RadioButton) findViewById(R.id.radio_btn2));
		radioBtn2.setOnCheckedChangeListener(this);
		radioBtn3 = ((RadioButton) findViewById(R.id.radio_btn3));
		radioBtn3.setOnCheckedChangeListener(this);
		radioBtn4 = ((RadioButton) findViewById(R.id.radio_btn4));
		radioBtn4.setOnCheckedChangeListener(this);
	}

	/**
	 * 切换模块
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		radioBtn1.setPressed(false);
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.radio_btn1:
				titleText.setText(R.string.icourse_schedule);
				this.mHost.setCurrentTabByTag("schedule_tab");
				break;
			case R.id.radio_btn2:
				titleText.setText(R.string.icourse_this_tern_grades);
				this.mHost.setCurrentTabByTag("grade_tab");
				break;
			case R.id.radio_btn3:
				titleText.setText(R.string.icourse_course_label);
				this.mHost.setCurrentTabByTag("course_tab");
				break;
			case R.id.radio_btn4:
				titleText.setText(R.string.icourse_exam_label);
				this.mHost.setCurrentTabByTag("exam_tab");
				break;
			}
		}
	}

	private void setupIntent() {
		this.mHost = this.getTabHost();
		Log.d("mHost", "mHost");
		TabHost localTabHost = this.mHost;
		Log.d("localHost", "Local");

		localTabHost.addTab(buildTabSpec("schedule_tab",
				R.string.icourse_schedule, R.drawable.icourse_schedule_ic_pressed,
				this.scheduleIntent));
		
		localTabHost.addTab(buildTabSpec("course_tab",
				R.string.icourse_course_label, R.drawable.icourse_table_ic_normal,
				this.courseIntent));

		localTabHost.addTab(buildTabSpec("grade_tab",
				R.string.icourse_grade_label, R.drawable.icon_search_normal,
				this.gradeIntent));

		localTabHost.addTab(buildTabSpec("exam_tab",
				R.string.icourse_exam_label, R.drawable.icourse_exam_table_ic_normal,
				this.examIntent));

	}

	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.mHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}


	// 实现标题栏的Home键
	public void btnHomeOnClick(View v) {
		startActivity(new Intent(this, MainActivity.class));
	}

	// 实现标题栏的返回键
	public void btnBackOnClick(View v) {
		this.finish();
	}
}
