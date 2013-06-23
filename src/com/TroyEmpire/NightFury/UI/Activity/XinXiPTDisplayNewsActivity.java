package com.TroyEmpire.NightFury.UI.Activity;

import com.TroyEmpire.NightFury.Entity.News;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class XinXiPTDisplayNewsActivity extends Activity {

	private WebView newsWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xinxipt_display_news);

		// Set the title bar text as this activity label
		TextView titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText(R.string.xinxipt_label);

		newsWebView = (WebView) findViewById(R.id.xinxipt_news_display_webview);
		News news = (News) getIntent().getExtras().get("newsSelected");
		Log.d("news title", news.getTitle());
		newsWebView.loadDataWithBaseURL(null, news.getContent(), "text/html",
				"utf-8", null);

	}

	// 实现标题栏的返回键
	public void btnBackOnClick(View v) {
		this.finish();
	}
}