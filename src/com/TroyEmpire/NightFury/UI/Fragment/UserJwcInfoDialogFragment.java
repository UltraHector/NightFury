package com.TroyEmpire.NightFury.UI.Fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection.Response;
import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Entity.ExamScore;
import com.TroyEmpire.NightFury.Enum.JwcAction;
import com.TroyEmpire.NightFury.Enum.ScheduleType;
import com.TroyEmpire.NightFury.Ghost.IService.IJwcService;
import com.TroyEmpire.NightFury.Ghost.Service.JwcService;
import com.TroyEmpire.NightFury.UI.Activity.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
// API = 8 and above
public class UserJwcInfoDialogFragment extends DialogFragment {

	private static final String TAG = "com.TroyEmpire.NightFury.UI.Fragment.UserJwcInfoDialogFragment";

	private IJwcService jwcService;
	private Activity activity;
	private View dialogView;
	private SharedPreferences userJwcInfoSharedPreference;
	private JwcAction jwcAction;
	private Response response;
	private Map<String, String> cookies;
	private Button confirmButton;
	private Handler handler;

	// the flag to indicate the status of the network execution

	@SuppressLint("ValidFragment")
	public UserJwcInfoDialogFragment(Activity activity, JwcAction jwcAction) {
		this.activity = activity;
		this.jwcAction = jwcAction;
		this.jwcService = new JwcService();
		this.userJwcInfoSharedPreference = activity.getSharedPreferences(
				Constant.SHARED_PREFERENCE_USER_JWC_INFO, Context.MODE_PRIVATE);
	}

	@TargetApi(Build.VERSION_CODES.FROYO)
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		dialogView = inflater.inflate(R.layout.fragment_ischedule_jwc_dialog,
				null);
		// UI thread handler
		handler = new Handler();
		builder.setView(dialogView)
				.setTitle(Constant.NEED_INPUT_JWC_USER_INFO)
				// click the confirm button and call the function
				.setPositiveButton(R.string.confirm,
						new Dialog.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// Do nothing , we will override it
							}
						})
				.setNegativeButton(R.string.concel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								UserJwcInfoDialogFragment.this.getDialog()
										.cancel();
							}
						});

		// ---------------------------------------------------------
		// create the builder
		// ---------------------------------------------------------
		final AlertDialog dialog_ready = builder.create();

		// ----------------------------------------------------------
		// Override the onclick() of positive button API 8 and above
		// ----------------------------------------------------------
		dialog_ready.setOnShowListener(new DialogInterface.OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {
				confirmButton = dialog_ready
						.getButton(DialogInterface.BUTTON_POSITIVE);
				/*
				 * the confirm button's state should be decide by the network
				 * status
				 */
				setDefaultValue();
				confirmButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// decide whether to save the user's info
						saveUserInfo();
						UpdateScheduleAsynTask updateScheduleAsynTask = new UpdateScheduleAsynTask();
						updateScheduleAsynTask.execute();
					}
				});
			}
		});
		return dialog_ready;
	}

	// start to get the exam score if get it successfully broadcast an action
	private boolean startDisplayExamScore() {
		// broad cast a new packet has been received
		Intent intent = new Intent(Constant.BROADCAST_GET_EXAM_ACTION);
		List<ExamScore> examScores = jwcService.getExamScore(cookies);
		if (examScores == null)
			return false;
		int i = examScores.size();
		intent.putExtra(Constant.NUMBER_OF_EXAM_SCORES, i);
		for (ExamScore examScore : examScores) {
			intent.putExtra("examScores_" + (i--), examScore);
		}
		this.activity.sendBroadcast(intent);
		return true;
	}

	private boolean startUpdateSchedule() {
		return startUpdateCourseSchedule() && startUpdateExamchedule();

	}

	private boolean startUpdateCourseSchedule() {
		if (this.jwcService.updateUserSchedules(cookies,
				ScheduleType.COURSESCHEDULE)) {
			SharedPreferences pref = activity.getSharedPreferences(
					Constant.SHARED_PREFERENCE_SCHEDULE, Context.MODE_PRIVATE);
			this.jwcService.updateFormattedSchdeule(pref);
			return true;
		} else {
			return false;
		}
	}

	private boolean startUpdateExamchedule() {
		if (this.jwcService.updateUserSchedules(cookies,
				ScheduleType.EXAMSCHEDULE)) {
			return true;
		} else {
			return false;
		}
	}

	private void setDefaultValue() {
		// set the default value
		((EditText) (dialogView
				.findViewById(R.id.fragment_ischedule_jwc_dialog_edittext_account_number)))
				.setText(userJwcInfoSharedPreference.getString(
						Constant.USER_JWC_ACCOUNT_NUMBER, ""));
		((EditText) (dialogView
				.findViewById(R.id.fragment_ischedule_jwc_dialog_edittext_password)))
				.setText(userJwcInfoSharedPreference.getString(
						Constant.USER_JWC_PASSWORD, ""));
		CheckBox saveInfo = (CheckBox) (dialogView
				.findViewById(R.id.fragment_ischedule_jwc_dialog_checkbox_remember_me));
		saveInfo.setChecked(true);
		// set the validation code
		DownloadCaptchaAsynTask downloadCaptchaAsynTask = new DownloadCaptchaAsynTask();
		downloadCaptchaAsynTask.execute();
	}

	// Get the user's JWC information credentials
	private Map<String, String> getUserCredential() {
		Map<String, String> userJwcCredential = new HashMap<String, String>();
		userJwcCredential
				.put(Constant.LOGIN_WINDOW_TAG_USER_ACCOUNT_NUMBER,
						((EditText) (dialogView
								.findViewById(R.id.fragment_ischedule_jwc_dialog_edittext_account_number)))
								.getText().toString());
		userJwcCredential
				.put(Constant.LOGIN_WINDOW_TAG_PASSWORD,
						((EditText) (dialogView
								.findViewById(R.id.fragment_ischedule_jwc_dialog_edittext_password)))
								.getText().toString());

		userJwcCredential
				.put(Constant.CAPTCHA,
						((EditText) (dialogView
								.findViewById(R.id.fragment_ischedule_jwc_dialog_edittext_captcha)))
								.getText().toString());
		return userJwcCredential;
	}

	private void saveUserInfo() {
		EditText userAccountNumber = (EditText) (dialogView
				.findViewById(R.id.fragment_ischedule_jwc_dialog_edittext_account_number));
		EditText userPassword = (EditText) (dialogView
				.findViewById(R.id.fragment_ischedule_jwc_dialog_edittext_password));
		CheckBox saveInfo = (CheckBox) (dialogView
				.findViewById(R.id.fragment_ischedule_jwc_dialog_checkbox_remember_me));
		// Save the Jwc input by the user into the system
		if (saveInfo.isChecked()) {
			SharedPreferences.Editor editor = userJwcInfoSharedPreference
					.edit();
			editor.putString(Constant.USER_JWC_ACCOUNT_NUMBER,
					userAccountNumber.getText().toString());
			editor.putString(Constant.USER_JWC_PASSWORD, userPassword.getText()
					.toString());
			editor.commit(); // save the data
		} else {
			SharedPreferences userInfo = activity.getSharedPreferences(
					Constant.SHARED_PREFERENCE_USER_JWC_INFO,
					Context.MODE_PRIVATE);
			jwcService.removePassword(userInfo);
		}
	}

	/*******************************************
	 * download the captcha
	 *******************************************/
	private class DownloadCaptchaAsynTask extends
			AsyncTask<Void, Void, Boolean> {
		ProgressDialog progDailog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progDailog = new ProgressDialog(activity);
			progDailog.setMessage("拉取验证码中……");
			progDailog.setIndeterminate(false);
			progDailog.setCancelable(true);
			progDailog.show();
		}

		@Override
		protected Boolean doInBackground(Void... argms) {
			response = jwcService.getAndSaveTheCaptcha(response);
			try {
				if (response.parse().getElementsByTag("title").get(0).html()
						.contains("用户登录"))
					return true;
				else {
					handler.post(setConfirmButtonToUnabled);
					return false;
				}
			} catch (Exception e) {
				handler.post(setConfirmButtonToUnabled);
				return false;
			}
		}

		private Runnable setConfirmButtonToUnabled = new Runnable() {
			@Override
			public void run() {
				confirmButton.setEnabled(false);
			}
		};

		@Override
		protected void onPostExecute(Boolean status) {
			Bitmap bitmap;
			try {
				if (status) {
					bitmap = BitmapFactory.decodeFile(Constant.NIGHTFURY_TEMP
							+ "/temp.png");
				} else {
					bitmap = BitmapFactory.decodeStream(activity.getAssets()
							.open("captchaError.jpg"));
					EditText captchaText = (EditText) dialogView
							.findViewById(R.id.fragment_ischedule_jwc_dialog_edittext_captcha);
					captchaText.setHintTextColor(Color.RED);
					captchaText.setHint("网络错误");
					captchaText.setEnabled(false);
				}
				ImageView validationView = (ImageView) dialogView
						.findViewById(R.id.fragment_ischedule_jwc_dialog_imageview_captcha);
				validationView.setImageBitmap(bitmap);
				if (progDailog.isShowing()) {
					progDailog.dismiss();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/********************************************
	 * the task for logining into jwc
	 *******************************************/
	private class UpdateScheduleAsynTask extends AsyncTask<Void, Void, Integer> {

		ProgressDialog progDailog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progDailog = new ProgressDialog(activity);
			switch (jwcAction) {
			case UPDATE_SCHEDULE:
				progDailog.setMessage("正在更新课程信息……");
				break;
			case GET_EXAM_SCORE:
				progDailog.setMessage("正在获取成绩……");
				break;
			case UPDATE_COURSE_SCHEDULE:
				progDailog.setMessage("正在更新课程表……");
				break;
			case UPDATE_EXAM_SCHEDULE:
				progDailog.setMessage("正在更新考试表……");
				break;
			}
			progDailog.setIndeterminate(false);
			progDailog.setCancelable(true);
			progDailog.show();
		}

		/**********************************************/
		// returned value represents the status when update schedules
		// 0 represents OK ;
		// 1 represents account error or captcha error
		// 2 represents logged in but download schedule error
		/***********************************************/
		@Override
		protected Integer doInBackground(Void... argms) {
			boolean updateScheduleStatus = false;
			cookies = jwcService.connectToJwc(getUserCredential(), response);
			if (cookies != null) {
				if(cookies.size() == 0)
					return 1;  // 由JWC反馈的cookie为空，则是用户的账户信息错误
				switch (jwcAction) {
				case UPDATE_SCHEDULE:
					updateScheduleStatus = startUpdateSchedule();
					break;
				case GET_EXAM_SCORE:
					updateScheduleStatus = startDisplayExamScore();
					break;
				case UPDATE_COURSE_SCHEDULE:
					updateScheduleStatus = startUpdateCourseSchedule();
					break;
				case UPDATE_EXAM_SCHEDULE:
					updateScheduleStatus = startUpdateExamchedule();
					break;
				}
			} else {
				return 2;
			}
			if (updateScheduleStatus) {
				dismiss();
				return 0;
			} else {
				return 2;
			}
		}

		@Override
		protected void onPostExecute(Integer status) {
			if (progDailog.isShowing()) {
				progDailog.dismiss();
			}
			if (status.intValue() == 0) {
				Log.i(TAG, "Login JWC Successfully");
			} else if (status.intValue() == 1) {
				// set the error feedback
				handler.post(new Runnable() {
					@Override
					public void run() {
						((TextView) dialogView
								.findViewById(R.id.fragment_ischedule_jwc_dialog_textview_error_content))
								.setText("账户信息或验证码有误");
						((EditText) (dialogView
								.findViewById(R.id.fragment_ischedule_jwc_dialog_edittext_captcha))).setText("");
					}
				});
				threadGetCaptcha();
				Log.i(TAG, "Login JWC Failed");
			} else {
				// set the error feedback
				handler.post(new Runnable() {
					@Override
					public void run() {
						((TextView) dialogView
								.findViewById(R.id.fragment_ischedule_jwc_dialog_textview_error_content))
								.setText("网络故障，检查网络，重试一次");
						((EditText) (dialogView
								.findViewById(R.id.fragment_ischedule_jwc_dialog_edittext_captcha))).setText("");
					}
				});	
				threadGetCaptcha();
				Log.i(TAG, "Download schedule Failed");
			}

		}
	}

	private void threadGetCaptcha() {
		DownloadCaptchaAsynTask downloadCaptchaAsynTask = new DownloadCaptchaAsynTask();
		downloadCaptchaAsynTask.execute();
	}
}