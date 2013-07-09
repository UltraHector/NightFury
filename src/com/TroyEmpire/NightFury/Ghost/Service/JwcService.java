package com.TroyEmpire.NightFury.Ghost.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Entity.ExamScore;
import com.TroyEmpire.NightFury.Enum.ScheduleType;
import com.TroyEmpire.NightFury.Ghost.IService.IJwcService;
import com.TroyEmpire.NightFury.Util.JWCUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.util.Log;

public class JwcService implements IJwcService {

	private static final String TAG = "com.TroyEmpire.NightFury.Ghost.Service.JwcService";

	private List<ExamScore> examScore = new ArrayList<ExamScore>();

	@Override
	public int isUserLogInfoCorrect(Map<String, String> userInfo) {
		try {
			Connection jwcLoginUrlConnection = Jsoup
					.connect(Constant.JWC_LOGIN_WINDOW_URL);
			Connection.Response jwcLoginUrlGetResponse = jwcLoginUrlConnection
					.method(Method.GET).execute();
			Map<String, String> cookies = jwcLoginUrlGetResponse.cookies();
			// TODO ?
			Map<String, String> loginParameters = JWCUtil
					.getExtraLoginParameters(jwcLoginUrlGetResponse.parse());
			loginParameters.putAll(userInfo);
			Connection.Response loginResponse = jwcLoginUrlConnection
					.cookies(cookies).method(Method.POST).data(loginParameters)
					.execute();
			if (loginResponse.parse().getElementsByTag("title").html()
					.equals(Constant.LOGIN_SUCCESS_RESPONSE_TITILE)) {
				return 0;
			}
		} catch (Exception e) {
			return 2;
		}
		return 1;
	}

	@Override
	public List<ExamScore> getExamScore(Map<String, String> cookies) {
		Connection jwcExamScoreReceiveParameterConnection = Jsoup
				.connect(Constant.JWC_RECEIVE_EXAM_SCORE_PARAMETERS_URL);
		try {
			Connection.Response res1 = jwcExamScoreReceiveParameterConnection
					.cookies(cookies).execute();
			Map<String, String> jwcExtraParameters = JWCUtil
					.getExamScoreParameters(res1.parse());
			jwcExtraParameters.put(Constant.SCHEDULE_EXAM_SCORE_XN,
					JWCUtil.getCurrentSchoolYear());
			jwcExtraParameters.put(Constant.SCHEDULE_EXAM_SCORE_XQ,
					JWCUtil.getCurrentSemesterOneWord());
			Document doc = jwcExamScoreReceiveParameterConnection
					.cookies(cookies).method(Method.POST)
					.data(jwcExtraParameters).execute().parse();
			Element result = doc
					.getElementById(Constant.JWC_GET_EXAM_SCORE_TABLE_ID);
			formatExamScore(result.child(0));

		} catch (Exception e) {
			// unexpected error happened.
			Log.i(TAG, "Unexpected error happend when get the exam score");
			return null;
		}
		return this.examScore;
	}

	@Override
	public Response getAndSaveTheCaptcha(Response response) {
		try {
			// set the validation imge
			Connection jwcLoginUrlConnection = Jsoup
					.connect(Constant.JWC_LOGIN_WINDOW_URL);
			Connection jwcValidationCode = Jsoup
					.connect(Constant.JWC_VALIDATION_CODE_URL);
			response = jwcLoginUrlConnection.method(Method.GET).execute();

			// Temple save the validation code
			File tempFolder = new File(Constant.NIGHTFURY_TEMP);
			if (!tempFolder.exists())
				tempFolder.mkdirs();
			File tempValidationCode = new File(Constant.NIGHTFURY_TEMP
					+ "/temp.png");
			tempValidationCode.createNewFile();
			FileUtils.writeByteArrayToFile(tempValidationCode,
					jwcValidationCode.cookies(response.cookies())
							.ignoreContentType(true).execute().bodyAsBytes());
			return response;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i(TAG, e.toString());
		}
		return null;
	}

	/**
	 * 用户信息错误返回 空的map
	 * 网络故障返回null
	 * 正常返回登录成功的cookies
	 */
	@Override
	public Map<String, String> connectToJwc(Map<String, String> userCredential,
			Response response) {
		try {
			Connection jwcLoginUrlConnection = Jsoup
					.connect(Constant.JWC_LOGIN_WINDOW_URL);
			Map<String, String> loginParameters = JWCUtil
					.getExtraLoginParameters(response.parse());
			loginParameters.putAll(userCredential);
			if (!jwcLoginUrlConnection.cookies(response.cookies())
					.method(Method.POST).data(loginParameters).execute()
					.parse().getElementsByTag("title").get(0).html()
					.contains("整体架构")) {
				Log.i(TAG,"Wrong Credentials, Log Into JWC Failed");
				return new HashMap<String,String>();
			}
		} catch (IOException e) {	
			Log.i(TAG,"Network Error, Log Into JWC Failed");
			return null;
		}
		return response.cookies();
	}

	@Override
	public boolean updateUserSchedules(Map<String, String> cookies,
			ScheduleType SCHEEDULETYPE) {
		try {
			// Connect to the Schedule main url
			Connection jwcScheduleMainUrlConnection = Jsoup
					.connect(Constant.JWC_RECEIVE_SCHEDULE_PARAMETERS_URL);
			Connection.Response res1 = jwcScheduleMainUrlConnection.cookies(
					cookies).execute();
			// Gather all the post parameters for exporting schedule
			Map<String, String> jwcExtortScheduleParameters = JWCUtil
					.getExtraScheduleParameters(res1.parse());
			jwcExtortScheduleParameters.put(Constant.SCHEDULE_SEMESTER,
					JWCUtil.getCurrentSemester());
			jwcExtortScheduleParameters.put(Constant.SCHEDULE_YEAR,
					JWCUtil.getCurrentSchoolYear());

			// Decide which schedule to export
			if (SCHEEDULETYPE == ScheduleType.COURSESCHEDULE)
				jwcExtortScheduleParameters.put(
						Constant.SCHEDULE_EXPORT_COURSE, "导出课程表");
			else
				jwcExtortScheduleParameters.put(Constant.SCHEDULE_EXPORT_EXAM,
						"导出考试表");

			// Send parameters to schedule url and receive the schedule
			jwcScheduleMainUrlConnection.cookies(cookies).method(Method.POST)
					.data(jwcExtortScheduleParameters).execute();
			Connection jwcScheduleUrlIncludingExportOptions = Jsoup
					.connect(Constant.JWC_SCHEDULE_URL_INCLUDING_EXPORT_OPTIONS);
			Document doc = jwcScheduleUrlIncludingExportOptions
					.method(Method.GET).cookies(cookies).execute().parse();
			String pureSchedulePageurl = Jsoup
					.connect(
							Constant.JWC_MAIN_URL
									+ doc.getElementById(
											"ReportFrameReportViewer1")
											.attr(Constant.SCHEDULE_EXPORT_INTERNAL_FRAME_ATTR))
					.method(Method.GET).cookies(cookies).execute().parse()
					.getElementById("frameset").child(1)
					.attr(Constant.SCHEDULE_EXPORT_INTERNAL_FRAME_ATTR);
			String scheduleBody = Jsoup
					.connect(Constant.JWC_MAIN_URL + pureSchedulePageurl)
					.cookies(cookies).execute().body();

			// /////////////////////////////////////////////////
			/***** Write the schedules to the file system ****/
			// /////////////////////////////////////////////////
			String path = Constant.NIGHTFURY_STORAGE_ROOT;
			File couseScheduleDirectory = new File(
					Constant.NIGHTFURY_STORAGE_ROOT
							+ Constant.SCHEDULE_RELATIVE_DIRECTORY);
			if (!couseScheduleDirectory.exists()) {
				couseScheduleDirectory.mkdirs();
			}
			if (SCHEEDULETYPE == ScheduleType.COURSESCHEDULE)
				path += Constant.SCHEDULE_RELATIVE_DIRECTORY + "/"
						+ Constant.COURSE_SCHEDULE_NAME;
			else
				path += Constant.SCHEDULE_RELATIVE_DIRECTORY + "/"
						+ Constant.EXAM_SCHEDULE_NAME;
			File schedule = new File(path);
			if (schedule.exists()) {
				schedule.delete();
			}
			schedule.createNewFile();
			FileOutputStream fos = new FileOutputStream(schedule);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			out.write(scheduleBody);
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void formatExamScore(Element resultList) {
		this.examScore = new ArrayList<ExamScore>();
		List<Element> elements = resultList.children();
		// remove the first element which doesnot contain any exam info
		elements.remove(0);
		for (Element ele : elements) {
			ExamScore score = new ExamScore();
			score.setCourceName(ele.child(1).html());
			if (ele.child(2).html().trim().contains("&"))
				score.setScore(" ");
			else
				score.setScore(ele.child(2).html());
			score.setStatus(ele.child(4).html());
			score.setCreditPoints(ele.child(6).html());
			this.examScore.add(score);
		}
	}

	/*****************************************/
	// the corse content is saved as a string
	// in the sharedpreferences
	// example: 语文#语文######历史####$英语#英语#####
	// 每天的课程以$隔开，一天之内的课程分为14个段，如果时间段内有课则会有课程的内容
	/*****************************************/
	@Override
	public void updateFormattedSchdeule(SharedPreferences pref) {
		Editor editor = pref.edit();
		String path = Constant.NIGHTFURY_STORAGE_ROOT
				+ Constant.SCHEDULE_RELATIVE_DIRECTORY + "/"
				+ Constant.COURSE_SCHEDULE_NAME;
		File courseSchedule = new File(path);
		try {
			Document doc = Jsoup.parse(courseSchedule, "UTF-8");
			// the table uses the class named "a8"
			List<Element> weekDays = doc.getElementsByClass("a8").get(0)
					.child(0).children();
			// remove first three
			weekDays.remove(0);
			weekDays.remove(0);
			weekDays.remove(0);
			// get and save the seven days schedule
			for (Element ele : weekDays) {
				String weekdayMark = ele.child(0).child(0).html().trim();
				String dayContent = "";
				List<Element> timeSlices = ele.children();
				timeSlices.remove(0); // remove 周几 mark
				int countSlice = 1;
				for (Element timeSlice : timeSlices) {
					if (!timeSlice.html().trim().startsWith("&")) {
						dayContent += countSlice + "#"
								+ timeSlice.child(0).html() + "$";
					}
					countSlice++;
				}
				editor.putString(weekdayMark, dayContent);
			}
			editor.commit();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean removePassword(SharedPreferences userInfo) {
		Editor editor = userInfo.edit();
		editor.remove(Constant.USER_JWC_PASSWORD);
		return editor.commit();
	}

}
