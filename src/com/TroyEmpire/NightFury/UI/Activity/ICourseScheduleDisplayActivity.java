package com.TroyEmpire.NightFury.UI.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.TroyEmpire.NightFury.Entity.DayCourseUnit;
import com.TroyEmpire.NightFury.Enum.WeekDay;
import com.TroyEmpire.NightFury.Ghost.IService.IScheduleService;
import com.TroyEmpire.NightFury.Ghost.Service.ScheduleService;
import com.TroyEmpire.NightFury.Util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class ICourseScheduleDisplayActivity extends Activity{
	
	private IScheduleService scheduleService = new ScheduleService(this);
	private WeekDay weekday;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_icourse_schedule_display);
		
		int weekdayFrom = getIntent().getIntExtra("weekday", 0);
		
		weekday = Util.getInt2Weekday(weekdayFrom);
		
		ListView lv = (ListView) findViewById(R.id.icourse_schedule_lv);
		List<DayCourseUnit> courses = scheduleService
				.getDayCourseUnits(weekday);
		List<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		for (DayCourseUnit course : courses) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("time", course.getTimePeriod());
			map.put("courseName", course.getContent());
			listItem.add(map);
		}

		if (listItem.isEmpty()) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("time", "全天");
			map.put("courseName", "哇塞，整天没课，好好利用空余时间吧，少年！");
			listItem.add(map);
		}

		SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,
				R.layout.icourse_schedule_item, new String[] { "time",
						"courseName" }, new int[] { R.id.ischedule_time_tv,
						R.id.ischedule_course_tv });
		lv.setAdapter(listItemAdapter);
		
	}
}
