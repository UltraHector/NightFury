package com.TroyEmpire.NightFury.Ghost.IService;

import java.util.ArrayList;
import java.util.List;
import com.TroyEmpire.NightFury.Entity.DayCourseUnit;
import com.TroyEmpire.NightFury.Entity.PhoneModeTimeUnit;
import com.TroyEmpire.NightFury.Enum.WeekDay;

public interface IScheduleService {

	/**
	 * whether the user has already saved the course schedule
	 */
	public boolean isCourseScheduleSaved();

	/**
	 * whether the user has already saved the exam schedule
	 */
	public boolean isExamScheduleSaved();

	/**
	 * whether the user has already saved all the schedules
	 */
	public boolean isAllScheduleSaved();

	/**
	 * get the path of the course schedule html
	 */
	public String getCourseScheduleHtmlFilePath();

	/**
	 * get the path of the course schedule html
	 */
	public String getExamScheduleHtmlFilePath();

	public boolean isUserJwcPasswordHasBeenSaved();

	public boolean isUserJwcAccountNumberHasBeenSaved();

	public List<DayCourseUnit> getDayCourseUnits(WeekDay weekDay);
	
	/**
	 * get the phone mode time units according to a week day
	 * currently we don't consider the 10 minutes break, because 有些老师会拖堂
	 */
	public ArrayList<PhoneModeTimeUnit> getDayCoursePhoneModeTimeUnits(WeekDay weekDay);
}
