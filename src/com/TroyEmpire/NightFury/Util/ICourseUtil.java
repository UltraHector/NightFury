package com.TroyEmpire.NightFury.Util;

public class ICourseUtil {

	// 1 represents 08:00, 2 represents 09:00
	public static String getTimePeriodByStartSliceId(int sliceId) {
		switch (sliceId) {
		case 1:
			return "08:00";
		case 2:
			return "09:00";
		case 3:
			return "10:20";
		case 4:
			return "11:20";
		case 5:
			return "13:00";
		case 6:
			return "14:00";
		case 7:
			return "15:00";
		case 8:
			return "16:00";
		case 9:
			return "17:00";
		case 10:
			return "18:00";
		case 11:
			return "19:00";
		case 12:
			return "20:00";
		case 13:
			return "21:00";
		case 14:
			return "22:00";
		}
		return "08:00";
	}

	// 1 represents 08:00, 2 represents 09:00
	public static String getTimePeriodByEndSliceId(int sliceId) {
		switch (sliceId) {
		case 1:
			return "08:50";
		case 2:
			return "09:50";
		case 3:
			return "11:10";
		case 4:
			return "12:10";
		case 5:
			return "13:50";
		case 6:
			return "14:50";
		case 7:
			return "15:50";
		case 8:
			return "16:50";
		case 9:
			return "17:50";
		case 10:
			return "18:50";
		case 11:
			return "19:50";
		case 12:
			return "20:50";
		case 13:
			return "21:50";
		case 14:
			return "22:50";
		}
		return "08:50";
	}
	
	/**
	 * @param startSlice the time slice of the course
	 * @param endSlice the time slice of the course
	 * @return the time period which the course covers
	 */
	public static String getTimePeriod(String startSlice, String endSlice) {
		String timePeriod = "";
		timePeriod += ICourseUtil.getTimePeriodByStartSliceId(Integer.valueOf(startSlice));
		timePeriod += "-"
				+ ICourseUtil.getTimePeriodByEndSliceId(Integer.valueOf(endSlice));
		return timePeriod;
	}

}
