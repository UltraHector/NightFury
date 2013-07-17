package com.TroyEmpire.NightFury.UI.Activity;


import com.TroyEmpire.NightFury.Entity.Building;
import com.TroyEmpire.NightFury.UI.ViewHolder.XiaoYuanDTWebView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class XiaoYuanDTDisplayPathActivity extends Activity {

	private XiaoYuanDTWebView xiaoYuanDTWebView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiaoyuandt_display_path);
		TextView tvTitleBar = (TextView) findViewById(R.id.title_text);
		tvTitleBar.setText("建议路径");
		Building sourceBuilding = (Building)getIntent().getExtras().get("sourceBuilding");
		Building destBuilding = (Building)getIntent().getExtras().get("destBuilding");
		xiaoYuanDTWebView = (XiaoYuanDTWebView) findViewById(R.id.xiaoyuandt_display_path_webview);
		xiaoYuanDTWebView.initiate(1, this);
		xiaoYuanDTWebView.setJavaScriptEnabled(true);
		xiaoYuanDTWebView.loadUrl("file:///android_asset/map.html");
		xiaoYuanDTWebView.addMarkerForPath(sourceBuilding, destBuilding);
		xiaoYuanDTWebView.disPlayShortestPath(sourceBuilding.getPathDotId(), destBuilding.getPathDotId());
	}
	
	public void btnHomeOnClick(View v) {
		startActivity(new Intent(this, MainActivity.class));
	}

	public void btnBackOnClick(View v) {
		finish();
	}
}
