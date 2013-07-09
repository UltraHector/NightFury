package com.TroyEmpire.NightFury.UI.Activity;

import com.TroyEmpire.NightFury.Constant.Constant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;


public class AboutUsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		// Set the title bar text as this activity label
		TextView titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText(R.string.about_us_label);
		
		WebView showText = (WebView) findViewById(R.id.about_display_webview);
		showText.getSettings().setJavaScriptEnabled(true);
		showText.loadUrl(Constant.HEBE_MANUAL_HTML_PATH);
	}
	
	// 实现标题栏的Home键
	public void btnHomeOnClick(View v) {
		startActivity(new Intent(this, MainActivity.class));
	}

	// 实现标题栏的返回键
	public void btnBackOnClick(View v) {
		finish();
	}
}
