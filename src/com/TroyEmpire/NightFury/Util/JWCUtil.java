package com.TroyEmpire.NightFury.Util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

import com.TroyEmpire.NightFury.Constant.Constant;

public class JWCUtil {

	/**
	 * Get login parameters except username and password which can be read from
	 * plain html
	 * 
	 * @param loginDoc
	 *            the document of the login page
	 */
	public static Map<String, String> getExtraLoginParameters(Document loginDoc) {
		Map<String, String> extraParameters = new HashMap<String, String>();
		String value, name;
		name = Constant.VIEWSTATE;
		value = loginDoc.getElementById("__VIEWSTATE").val();
		extraParameters.put(name, value);
		name = Constant.EVENTVALIDATION;
		value = loginDoc.getElementById("__EVENTVALIDATION").val();
		extraParameters.put(name, value);
		name = Constant.LOGIN;
		value = "登陆";
		extraParameters.put(name, value);
		return extraParameters;
	}

	/**
	 * Get the parameters to export the course or exam schedule
	 * 
	 * @param
	 */
	public static Map<String, String> getExtraScheduleParameters(Document scheduleDoc) {
		Map<String, String> extraParameters = new HashMap<String, String>();
		String value, name;
		name = Constant.VIEWSTATE;
		value = scheduleDoc.getElementById(Constant.VIEWSTATE).val();
		extraParameters.put(name, value);
		name = Constant.EVENTVALIDATION;
		value = scheduleDoc.getElementById(Constant.EVENTVALIDATION).val();
		extraParameters.put(name, value);
		name = Constant.SCHEDULE_LASTFOCUS;
		value = "";
		extraParameters.put(name, value);
		name = Constant.SCHEDULE_EVENTARGUMENT;
		value = "";
		extraParameters.put(name, value);
		name = Constant.SCHEDULE_EVENTTARGET;
		value = "";
		extraParameters.put(name, value);
		return extraParameters;
	}

	public static Map<String, String> getExamScoreParameters(Document scheduleDoc) {
		Map<String, String> extraParameters = new HashMap<String, String>();
		String value, name;
		name = Constant.VIEWSTATE;
		value = scheduleDoc.getElementById(Constant.VIEWSTATE).val();
		extraParameters.put(name, value);
		name = Constant.EVENTVALIDATION;
		value = scheduleDoc.getElementById(Constant.EVENTVALIDATION).val();
		extraParameters.put(name, value);
		name = Constant.SCHEDULE_LASTFOCUS;
		value = "";
		extraParameters.put(name, value);
		name = Constant.SCHEDULE_EVENTARGUMENT;
		value = "";
		extraParameters.put(name, value);
		name = Constant.SCHEDULE_EVENTTARGET;
		value = Constant.SCHEDULE_EVENTTARGET_VALUE;
		extraParameters.put(name, value);
		name = Constant.SCHEDULE_EXAM_SCORE_XH;
		value = scheduleDoc.getElementById(Constant.SCHEDULE_EXAM_SCORE_XH)
				.val();
		extraParameters.put(name, value);
		name = Constant.SCHEULE_EXAM_SCORE_XM;
		value = scheduleDoc.getElementById(Constant.SCHEULE_EXAM_SCORE_XM)
				.val();
		extraParameters.put(name, value);
		name = Constant.SCHEDULE_EXAM_SCORE_YXZY;
		value = scheduleDoc.getElementById(Constant.SCHEDULE_EXAM_SCORE_YXZY)
				.val();
		extraParameters.put(name, value);
		return extraParameters;
	}

	/*
	 * 下面的month > 8有三个，改月份的话要一起改哦
	 */
	public static String getCurrentSchoolYear() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		if (month > 8)
			return year + "-" + (year + 1);
		else
			return (year - 1) + "-" + year;
	}

	public static String getCurrentSemesterOneWord() {
		int month = Calendar.getInstance().get(Calendar.MONTH);
		if (month > 8)
			return "上";
		else
			return "下";
	}

	public static String getCurrentSemester() {
		int month = Calendar.getInstance().get(Calendar.MONTH);
		if (month > 8)
			return Constant.FIRST_SEMESTER;
		else
			return Constant.SECOND_SEMESTER;
	}
}
