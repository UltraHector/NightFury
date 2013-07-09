package com.TroyEmpire.NightFury.Ghost.IService;

import java.util.List;
import java.util.Map;

import org.jsoup.Connection.Response;

import android.content.SharedPreferences;

import com.TroyEmpire.NightFury.Entity.ExamScore;
import com.TroyEmpire.NightFury.Enum.ScheduleType;

public interface IJwcService {

	/**
	 * @param userInfo
	 * @return 0 every thing is ok 1 when the password is wrong, 2 when the
	 *         network error, 3 other problem
	 */
	public int isUserLogInfoCorrect(Map<String, String> userInfo);

	/**
	 * get the exam scores
	 * 
	 * @param cookies
	 *            the cookies which has already login
	 */
	public List<ExamScore> getExamScore(Map<String, String> cookies);

	/**
	 * get and save the captcha temporarily
	 * 
	 * @return response the response which contains the cookies stuff
	 */
	Response getAndSaveTheCaptcha(Response response);

	/**
	 * login to jwc
	 * 
	 * @param userCredential
	 *            accountnumber + password + captcha
	 * @param response
	 *            the response corresponding to the captcha
	 */
	Map<String, String> connectToJwc(Map<String, String> userCredential,
			Response response);

	/**
	 * update the user schedules including course and exam schedule
	 * 
	 * @param cookies
	 *            the cookies which has already login
	 * @param true if update successfully, false if error happened when updating
	 */
	public boolean updateUserSchedules(Map<String, String> cookies,
			ScheduleType scheduleType);

	/**
	 * update the course schedule which is saved in the sharedpreferences
	 */
	void updateFormattedSchdeule(SharedPreferences pref);

	/**
	 * remove the save password
	 */
	boolean removePassword(SharedPreferences userInfo);

}
