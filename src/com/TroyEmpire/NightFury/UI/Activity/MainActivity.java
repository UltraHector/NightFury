package com.TroyEmpire.NightFury.UI.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Ghost.IService.IInitiateDataService;
import com.TroyEmpire.NightFury.Ghost.Service.InitiateDataService;
import com.TroyEmpire.NightFury.Util.NightFuryCommons;
import com.TroyEmpire.NightFury.Util.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "com.TroyEmpire.NightFury.UI.Activity";
	// private GridView mainFuncGridView;
	private IInitiateDataService initDataService = new InitiateDataService();

	// 用于测试服务是否打开的定时器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate()");
		if (checkIfNecessaryToDownloadData(1))
			startProgress();

		Log.i(TAG, "setView()");
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		// 初始化各个按钮，并设置点击事件
		initViews();
	}

	private void initViews() {
		ImageButton settingBtn = (ImageButton) findViewById(R.id.sys_setting);
		ImageButton aboutBtn = (ImageButton) findViewById(R.id.sys_helper);
		ImageButton exitBtn = (ImageButton) findViewById(R.id.sys_exit);
		View lv_XinXiPT = findViewById(R.id.home_xinxipt_lv);
		View lv_XiaoYuanDT = findViewById(R.id.home_xiaoyuandt_lv);
		View lv_ICourse = findViewById(R.id.home_icourse_lv);
		View lv_WaiMaiXT = findViewById(R.id.home_waimaixt_lv);
		View lv_Labrary = findViewById(R.id.home_labrary_lv);
		View lv_XiaoYuanHY = findViewById(R.id.home_xiaoyuanhy_lv);

		settingBtn.setOnClickListener(this);
		aboutBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);
		lv_XinXiPT.setOnClickListener(this);
		lv_XiaoYuanDT.setOnClickListener(this);
		lv_ICourse.setOnClickListener(this);
		lv_WaiMaiXT.setOnClickListener(this);
		lv_Labrary.setOnClickListener(this);
		lv_XiaoYuanHY.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		// 实现主页的功能点击
		switch (v.getId()) {
		// 信息平台
		case R.id.home_xinxipt_lv:
			startActivity(new Intent(this, XinXiPTActivity.class));
			break;
		// 校园地图
		case R.id.home_xiaoyuandt_lv:
			startActivity(new Intent(this, XiaoYuanDTActivity.class));
			break;
		// i 课程
		case R.id.home_icourse_lv:
			startActivity(new Intent(this, ICourseActivity.class));
			break;
		// 外卖系统
		case R.id.home_waimaixt_lv:
			startActivity(new Intent(this, WaiMaiXTActivity.class));
			break;
		// 图书馆
		case R.id.home_labrary_lv:
			startActivity(new Intent(this,
					InternalAccessLibraryWebViewActivity.class));
			break;
		// 校园黄页
		case R.id.home_xiaoyuanhy_lv:
			startActivity(new Intent(this, XiaoYuanHYActivity.class));
			break;

		// 设置按钮
		case R.id.sys_setting:
			startActivity(new Intent(this, PrefsActivity.class));
			break;
		// 帮助按钮
		case R.id.sys_helper:
			startActivity(new Intent(this, AboutUsActivity.class));
			break;
		// 退出按钮
		case R.id.sys_exit:
			Intent it = new Intent(Intent.ACTION_SEND);
			it.setType("text/plain");
			it.putExtra(Intent.EXTRA_SUBJECT, "分享");
			it.putExtra(Intent.EXTRA_TEXT,
					"掌上暨大无比强大，值得一试哦，下载地址：http://www.baidu.com");
			startActivity(Intent.createChooser(it, "选择分享方式"));
			break;
		}
	}

	/**
	 * @param campusId
	 * @return if the db file not exists, create folder structure and return
	 *         true
	 */
	private boolean checkIfNecessaryToDownloadData(int campusId) {
		boolean yesOrNo = false;
		String dbRestFolderPath = Constant.NIGHTFURY_STORAGE_ROOT
				+ "/Restaurant/Campus_" + campusId + "_Restaurant/RestaurantDB";
		String logoRestFolderPath = Constant.NIGHTFURY_STORAGE_ROOT
				+ "/Restaurant/Campus_" + campusId
				+ "_Restaurant/RestaurantLogo";
		String dbMapFolderPath = Constant.NIGHTFURY_STORAGE_ROOT
				+ "/Map/Campus_" + campusId + "_Map/MapDB";
		String imageMapFolderPath = Constant.NIGHTFURY_STORAGE_ROOT
				+ "/Map/Campus_" + campusId + "_Map/MapImage";
		String dbYellowPagePath = Constant.NIGHTFURY_STORAGE_ROOT
				+ "/YellowPage/Campus_" + campusId
				+ "_YellowPage/YellowPageDB/";
		String restaurantLogoPath = Constant.NIGHTFURY_STORAGE_ROOT
				+ "/Restaurant/Campus_" + campusId
				+ "_Restaurant/RestaurantLogo";

		File dbRestFolderFile = new File(dbRestFolderPath);
		File dbMapFolderFile = new File(dbMapFolderPath);
		File imageMapFolderFile = new File(imageMapFolderPath);
		File logoRestFolderFile = new File(logoRestFolderPath);
		File dbYellowPageFolderFile = new File(dbYellowPagePath);
		File restaurantLogoFolder = new File(restaurantLogoPath);

		if (!dbRestFolderFile.exists()) {
			dbRestFolderFile.mkdirs();
			yesOrNo = true;
		}
		if (!logoRestFolderFile.exists()) {
			logoRestFolderFile.mkdirs();
			yesOrNo = true;
		}
		if (!dbMapFolderFile.exists()) {
			dbMapFolderFile.mkdirs();
			yesOrNo = true;
		}
		if (!imageMapFolderFile.exists()) {
			imageMapFolderFile.mkdirs();
			yesOrNo = true;
		}
		if (!dbYellowPageFolderFile.exists()) {
			dbYellowPageFolderFile.mkdirs();
			yesOrNo = true;
		}
		if (!restaurantLogoFolder.exists()) {
			restaurantLogoFolder.mkdirs();
			yesOrNo = true;
		}
		return yesOrNo;
	}

	private void copyHebeDataFromAssertToSDCard(int campusId) {
		try {
			String dbRestPath = Constant.NIGHTFURY_STORAGE_ROOT
					+ "/Restaurant/Campus_" + campusId
					+ "_Restaurant/RestaurantDB/"
					+ Constant.RESTAURANT_DB_FILE_NAME;
			String dbMapPath = Constant.NIGHTFURY_STORAGE_ROOT + "/Map/Campus_"
					+ campusId + "_Map/MapDB/" + Constant.MAP_DB_FILE_NAME;
			String dbYellowPagePath = Constant.NIGHTFURY_STORAGE_ROOT
					+ "/YellowPage/Campus_" + campusId
					+ "_YellowPage/YellowPageDB/"
					+ Constant.YELLOWPAGE_DB_FILE_NAME;
			String restaurantPath = Constant.NIGHTFURY_STORAGE_ROOT
					+ "/Restaurant/Campus_" + campusId + "_Restaurant";

			File restFile = new File(dbRestPath);
			restFile.createNewFile();
			File mapFile = new File(dbMapPath);
			mapFile.createNewFile();
			File yellowPageFile = new File(dbYellowPagePath);
			File restaurantLogoZip = new File(restaurantPath
					+ "/RestaurantLogo.zip");

			InputStream in_rest = getAssets().open(
					Constant.NIGHTFURY_CLIENT_DB_DATA + "/Restaurant.db");
			InputStream in_map = getAssets().open(
					Constant.NIGHTFURY_CLIENT_DB_DATA + "/Map.db");
			InputStream in_yellowPage = getAssets().open(
					Constant.NIGHTFURY_CLIENT_DB_DATA + "/YellowPage.db");
			InputStream rest_Logo_Zip = getAssets().open(
					Constant.NIGHTFURY_CLIENT_DB_DATA + "/RestaurantLogo.zip");

			OutputStream out_rest_logo = new FileOutputStream(restaurantLogoZip);
			OutputStream out_rest = new FileOutputStream(restFile);
			OutputStream out_map = new FileOutputStream(mapFile);
			OutputStream out_yellowPage = new FileOutputStream(yellowPageFile);
			IOUtils.copy(rest_Logo_Zip, out_rest_logo);
			// Unzip the logo files
			Util.unzipFile(restaurantPath + "/RestaurantLogo.zip",
					restaurantPath);
			restaurantLogoZip.delete();// destroy itself
			
			IOUtils.copy(in_rest, out_rest);
			IOUtils.copy(in_map, out_map);
			IOUtils.copy(in_yellowPage, out_yellowPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class LoadData extends AsyncTask<Void, Void, Void> {
		ProgressDialog progressDialog;
		boolean whetherConnectToServer = false;

		@Override
		protected void onPreExecute() {
			progressDialog = (ProgressDialog) ProgressDialog.show(
					MainActivity.this, "下载数据包", "数据初始化中.....", true);
		};

		@Override
		protected Void doInBackground(Void... params) {
			if (false) {
				// V1.0掩盖了从服务器下载地图和餐馆信息
				// if ((whetherConnectToServer = NightFuryCommons
				// .checkWhetherCouldConnectToServer())) {
				initDataService.initiateMapData(1);
				initDataService.initiateRestaurantData(1);
			} else {
				copyHebeDataFromAssertToSDCard(1);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			if (whetherConnectToServer)
				Toast.makeText(MainActivity.this, "初始化数据成功", Toast.LENGTH_SHORT)
						.show();
			else
				;
			// V1.0掩盖了从服务器下载地图和餐馆信息
			// Toast.makeText(MainActivity.this,
			// "网络连接失败，初始化数据使用内置数据库。请及时手工更新", Toast.LENGTH_LONG)
			// .show();
		};
	}

	public void startProgress() {
		LoadData task = new LoadData();
		task.execute();
	}

}
