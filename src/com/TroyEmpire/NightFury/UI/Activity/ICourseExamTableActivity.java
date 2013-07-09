package com.TroyEmpire.NightFury.UI.Activity;


import com.TroyEmpire.NightFury.Ghost.IService.IScheduleService;
import com.TroyEmpire.NightFury.Ghost.Service.ScheduleService;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class ICourseExamTableActivity extends Activity {
	private WebView webView = null;
	IScheduleService scheduleService = new ScheduleService(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icourse_table);
		webView = (WebView) findViewById(R.id.webView_displayISchedule);

		// displayScheduleWebView.getSettings().setJavaScriptEnabled(true); //
		// enable the java script
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setUseWideViewPort(true);

		webView.getSettings().setDefaultTextEncodingName("utf-8"); // set encoding name as utf-8
		// Log.i("encoding-type", "Default Encoding = " +
		// displayScheduleWebView.getSettings().getDefaultTextEncodingName());

		String schedulePath = "file://"
				+ scheduleService.getExamScheduleHtmlFilePath();
		Log.d("path",schedulePath);
		webView.loadUrl(schedulePath);
	}
}
