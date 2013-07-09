package com.TroyEmpire.NightFury.Ghost.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Entity.DayCourseUnit;
import com.TroyEmpire.NightFury.Entity.PhoneModeTimeUnit;
import com.TroyEmpire.NightFury.Enum.WeekDay;
import com.TroyEmpire.NightFury.Ghost.IService.IScheduleService;
import com.TroyEmpire.NightFury.Util.ICourseUtil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class ScheduleService implements IScheduleService {

	private Context context;

	public ScheduleService(Context context) {
		this.context = context;
	}

	@Override
	public boolean isCourseScheduleSaved() {
		// check the whether schedules are in the file system
		File couseSchedule = new File(Constant.NIGHTFURY_STORAGE_ROOT
				+ Constant.SCHEDULE_RELATIVE_DIRECTORY + "/"
				+ Constant.COURSE_SCHEDULE_NAME);
		if (couseSchedule.exists())
			return true;
		else
			return false;
	}

	@Override
	public boolean isExamScheduleSaved() {
		// check the whether schedules are in the file system
		File examSchedule = new File(Constant.NIGHTFURY_STORAGE_ROOT
				+ Constant.SCHEDULE_RELATIVE_DIRECTORY + "/"
				+ Constant.EXAM_SCHEDULE_NAME);
		if (examSchedule.exists())
			return true;
		else
			return false;
	}

	@Override
	public boolean isAllScheduleSaved() {
		// check the whether schedules are in the file system
		File couseSchedule = new File(Constant.NIGHTFURY_STORAGE_ROOT
				+ Constant.SCHEDULE_RELATIVE_DIRECTORY + "/"
				+ Constant.COURSE_SCHEDULE_NAME);
		File examSchedule = new File(Constant.NIGHTFURY_STORAGE_ROOT
				+ Constant.SCHEDULE_RELATIVE_DIRECTORY + "/"
				+ Constant.EXAM_SCHEDULE_NAME);
		if (couseSchedule.exists() && examSchedule.exists())
			return true;
		else
			return false;
	}

	@Override
	public String getCourseScheduleHtmlFilePath() {
		return Constant.NIGHTFURY_STORAGE_ROOT
				+ Constant.SCHEDULE_RELATIVE_DIRECTORY + "/"
				+ Constant.COURSE_SCHEDULE_NAME;
	}

	@Override
	public String getExamScheduleHtmlFilePath() {
		return Constant.NIGHTFURY_STORAGE_ROOT
				+ Constant.SCHEDULE_RELATIVE_DIRECTORY + "/"
				+ Constant.EXAM_SCHEDULE_NAME;
	}

	@Override
	public boolean isUserJwcAccountNumberHasBeenSaved() {
		SharedPreferences userInfo = context.getSharedPreferences(
				Constant.SHARED_PREFERENCE_USER_JWC_INFO, Context.MODE_PRIVATE);
		return userInfo.contains(Constant.USER_JWC_ACCOUNT_NUMBER);
	}

	@Override
	public boolean isUserJwcPasswordHasBeenSaved() {
		SharedPreferences userInfo = context.getSharedPreferences(
				Constant.SHARED_PREFERENCE_USER_JWC_INFO, Context.MODE_PRIVATE);
		return userInfo.contains(Constant.USER_JWC_PASSWORD);
	}

	// raw String like : 1#电子商务南海楼$2#专家系统专家楼
	// return the formatted day course units\
	/*****************************************/
	// the corse content is saved as a string
	// in the sharedpreferences
	// example: 语文#语文######历史####$英语#英语#####
	// 每天的课程以$隔开，一天之内的课程分为14个段，如果时间段内有课则会有课程的内容
	/*****************************************/
	@Override
	public List<DayCourseUnit> getDayCourseUnits(WeekDay weekDay) {
		SharedPreferences pref = context.getSharedPreferences(
				Constant.SHARED_PREFERENCE_SCHEDULE, Context.MODE_PRIVATE);
		List<DayCourseUnit> dayCourseUnits = new ArrayList<DayCourseUnit>();
		String[] units = pref.getString(weekDay.toString(), "").split("['$']");

		// get the time period
		String startTimeSlice = ""; // record the startTime period of the course
		String endTimeSlice = ""; // record the startTime period of the course
		String content = ""; // record the course content
		for (int i = 0; i < units.length; i++) {
			DayCourseUnit dayCourseUnit = new DayCourseUnit();
			String unit = units[i];
			if (unit.equals("") || unit == null)
				break;

			String atoms[] = unit.split("#");
			startTimeSlice = atoms[0];
			endTimeSlice = atoms[0];
			content = atoms[1];
			while (++i < units.length
					&& units[i].split("#")[1].equalsIgnoreCase(content)) {
				endTimeSlice = units[i].split("#")[0];
			}
			// one time slice back
			i--;
			dayCourseUnit.setContent(content);
			dayCourseUnit.setTimePeriod(ICourseUtil.getTimePeriod(
					startTimeSlice, endTimeSlice));
			dayCourseUnits.add(dayCourseUnit);
		}
		return dayCourseUnits;
	}

	@Override
	public ArrayList<PhoneModeTimeUnit> getDayCoursePhoneModeTimeUnits(
			WeekDay weekDay) {
		SharedPreferences pref = context.getSharedPreferences(
				Constant.SHARED_PREFERENCE_SCHEDULE, Context.MODE_PRIVATE);
		ArrayList<PhoneModeTimeUnit> dayCourseUnits = new ArrayList<PhoneModeTimeUnit>();
		String[] units = pref.getString(weekDay.toString(), "").split("['$']");

		// get the time period
		String startTimeSlice = ""; // record the startTime period of the course
		String endTimeSlice = ""; // record the startTime period of the course
		String content = ""; // record the course content
		for (int i = 0; i < units.length; i++) {
			PhoneModeTimeUnit dayCourseUnit = new PhoneModeTimeUnit();
			String unit = units[i];
			if (unit.equals("") || unit == null)
				break;

			String atoms[] = unit.split("#");
			startTimeSlice = atoms[0];
			endTimeSlice = atoms[0];
			content = atoms[1];
			while (++i < units.length
					&& units[i].split("#")[1].equalsIgnoreCase(content)) {
				endTimeSlice = units[i].split("#")[0];
			}
			// one time slice back
			i--;

			// set the phone mode unit
			startTimeSlice = ICourseUtil.getTimePeriodByStartSliceId(Integer
					.valueOf(startTimeSlice));
			endTimeSlice = ICourseUtil.getTimePeriodByEndSliceId(Integer
					.valueOf(endTimeSlice));

			dayCourseUnit.setStartHour(Integer.valueOf(startTimeSlice
					.split(":")[0]));
			dayCourseUnit.setStartMinute(Integer.valueOf(startTimeSlice
					.split(":")[1]));
			dayCourseUnit
					.setEndHour(Integer.valueOf(endTimeSlice.split(":")[0]));
			dayCourseUnit
					.setEndMinute(Integer.valueOf(endTimeSlice.split(":")[1]));
			dayCourseUnits.add(dayCourseUnit);
		}
		return dayCourseUnits;
	}

}
