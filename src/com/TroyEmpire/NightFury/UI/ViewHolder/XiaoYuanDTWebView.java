package com.TroyEmpire.NightFury.UI.ViewHolder;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Entity.Building;
import com.TroyEmpire.NightFury.Entity.PathDot;
import com.TroyEmpire.NightFury.Ghost.IService.IBuildingService;
import com.TroyEmpire.NightFury.Ghost.IService.IMapService;
import com.TroyEmpire.NightFury.Ghost.Service.BuildingService;
import com.TroyEmpire.NightFury.Ghost.Service.MapService;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

public class XiaoYuanDTWebView extends WebView {

	private String TAG = "XiaoYuanDTWebView";
	private IMapService mapService;
	private IBuildingService buildingService;
	private Handler handler;

	int test = 0;

	private int campusId;

	private Activity activity;

	public XiaoYuanDTWebView(Context context) {
		super(context);
	}

	public XiaoYuanDTWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public XiaoYuanDTWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// choose campus and open database
	public void initiate(int campusId, Activity activity) {
		this.campusId = campusId;
		this.activity = activity;

		// call java from js

		// save in campusId sharePreferences
		SharedPreferences sp = activity.getSharedPreferences(
				Constant.SHARED_PREFERENCE_MAP_CAMPUS_ID_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(Constant.SHARED_PREFERENCE_MAP_CMAPUS_ID_KEY,
				String.valueOf(campusId));
		editor.commit();

		// in mapService it will get campusId sharePreferences
		mapService = new MapService(activity);
		buildingService = new BuildingService(campusId);

	}

	public void setJavaScriptEnabled(boolean on) {
		super.getSettings().setJavaScriptEnabled(on);
		super.addJavascriptInterface(new AndroidBridge(), "android");

	}

	public void loadUrl(String url) {
		super.loadUrl(url);
		super.requestFocus();// this method cannot not Override,so I put it here
	}

	public void disPlayShortestPath(int sourceId, int destId) {
		// find pathId from dataBase
		String stringPath = mapService.getShortestPath(sourceId, destId);

		String[] res = stringPath.split("#");

		double[] latitude = new double[res.length];
		double[] longitude = new double[res.length];

		for (int i = 0; i < res.length; i++) {
			PathDot pathDot = mapService.getPathDotById(Integer
					.parseInt(res[i]));
			if (pathDot != null) {
				latitude[i] = pathDot.getLatitude();
				longitude[i] = pathDot.getLongitude();
			}
		}
		for (int i = 1; i < res.length; i++) {
			String line = String.valueOf(latitude[i - 1]) + "#"
					+ String.valueOf(longitude[i - 1]) + "#"
					+ String.valueOf(latitude[i]) + "#"
					+ String.valueOf(longitude[i]);
			this.printLine(line);
		}

	}

	/**
	 * @param buildingId
	 * 
	 * */
	public void addMarkerForSearch(Building building) {
		double latitude = building.getLatitude();
		double longitude = building.getLongitude();
		String description = building.getDescription();
		String tohere = "search and display activity";
		String more = "info activity";
		String str = String.valueOf(latitude) + "#" + String.valueOf(longitude)
				+ "#" + description + "#" + tohere + "#" + more;
		String arg = "1";

		arg = "javascript:addMarkerForSearch(\'" + str + "\')";

		super.loadUrl(arg);
	}

	public void addMarkerForPath(Building buildingFrom, Building buildingTo) {
		double latitudeFrom = buildingFrom.getLatitude();
		double longitudeFrom = buildingFrom.getLongitude();
		String descriptionFrom = buildingFrom.getDescription();
		String tohereFrom = "search and display activity";
		String moreFrom = "info activity";
		String strFrom = String.valueOf(latitudeFrom) + "#"
				+ String.valueOf(longitudeFrom) + "#" + descriptionFrom + "#"
				+ tohereFrom + "#" + moreFrom;

		double latitudeTo = buildingTo.getLatitude();
		double longitudeTo = buildingTo.getLongitude();
		String descriptionTo = buildingTo.getDescription();
		String tohereTo = "search and display activity";
		String moreTo = "info activity";
		String strTo = String.valueOf(latitudeTo) + "#"
				+ String.valueOf(longitudeTo) + "#" + descriptionTo + "#"
				+ tohereTo + "#" + moreTo;

		String arg = new String();

		arg = "javascript:addMarkerForPath(\'" + strFrom + "#" + strTo + "\')";

		super.loadUrl(arg);
	}

	// ---------private -------------
	// The parameter of function printLine is a String which is contain 4 double
	// numbers that imply
	// the location source and location destination

	public void printLine(String line) {
		String arg = "javascript:printLine(\'" + line + "\')";
		super.loadUrl(arg);
	}

	private class AndroidBridge {
		public void callAndroid(final String arg) throws InterruptedException { // must
																				// be
																				// final
			Thread child = new Thread() {
				public void run() {
					String[] str = arg.split("#");
					double latitude = Double.parseDouble(str[0]);
					double longitude = Double.parseDouble(str[1]);
					int isInAddMarkerForPathMode = Integer.parseInt(str[2]);

					Building building = buildingService.getBuildingByLocation(
							latitude, longitude);
					if (building != null) {
						test = (int) building.getId();
						// building.getId() == 1 ����ʲô��û����
						if ((isInAddMarkerForPathMode == 0)
								&& (building.getId() != 0))
							addMarkerForSearch(building);
					}
				}
			};
			child.start();
			child.join();
			Log.d("id", "" + test);
		}
	}

}
