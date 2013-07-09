package com.TroyEmpire.NightFury.Ghost.Service;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Entity.Building;
import com.TroyEmpire.NightFury.Entity.Cell;
import com.TroyEmpire.NightFury.Entity.PathDot;
import com.TroyEmpire.NightFury.Ghost.DBManager.BuildingDBManager;
import com.TroyEmpire.NightFury.Ghost.DBManager.CellDBManager;
import com.TroyEmpire.NightFury.Ghost.DBManager.PathDotDBManager;
import com.TroyEmpire.NightFury.Ghost.DBManager.ShortestPathDBManager;
import com.TroyEmpire.NightFury.Ghost.IService.IMapService;

public class MapService implements IMapService {

	private String campusId;
	private String dbFile;
	private SQLiteDatabase db;
	private PathDotService pathDotService;
	private CellService cellService;
	private BuildingService buildingService;
	private ShortestPathService shortestPathService;

	final String TAG = "MAP";
	public Activity activity;

	public MapService(Activity activity) {
		this.activity = activity;
		// campusId must be first stored in XiaoYuanDTWebView.java
		// open database
		SharedPreferences sp = activity.getSharedPreferences(
				Constant.SHARED_PREFERENCE_MAP_CAMPUS_ID_FILE,
				Context.MODE_PRIVATE);
		campusId = sp.getString(Constant.SHARED_PREFERENCE_MAP_CMAPUS_ID_KEY,
				"");
		if (campusId.equals("")) {
			Log.e(TAG, "error: can not found campus ID");
		} else {

			this.dbFile = Constant.NIGHTFURY_STORAGE_ROOT + "/Map/Campus_"
					+ campusId + "_Map/MapDB/Map.db";

			db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);

			pathDotService = new PathDotService(Integer.parseInt(campusId));
			cellService = new CellService(Integer.parseInt(campusId));
			buildingService = new BuildingService(Integer.parseInt(campusId));
			shortestPathService = new ShortestPathService(
					Integer.parseInt(campusId));
		}

	}

	@Override
	public void addToSearchHistory(int cellId) {
		SharedPreferences sp = activity.getSharedPreferences(
				Constant.SHARED_PREFERENCE_MAP_HISTORY_FILE,
				Context.MODE_PRIVATE);
		String cellIdString = sp.getString(
				Constant.SHARED_PREFERENCE_MAP_HSITORY_KEY, "");

		if (cellIdString.equals("")) {
			cellIdString = String.valueOf(cellId);
			SharedPreferences.Editor editor = sp.edit();
			editor.putString(Constant.SHARED_PREFERENCE_MAP_HSITORY_KEY,
					cellIdString);
			editor.commit();
			return;
		}

		String[] id = cellIdString.split("#");

		int newCellIdStringLength = id.length;
		boolean cellIdInId = false;// cellId is in id set
		for (int i = 0; i < id.length; i++) {
			if (Integer.parseInt(id[i]) == cellId) {
				int j = i - 1;
				for (; j >= 0; j--) {
					id[j + 1] = id[j];
				}
				id[0] = String.valueOf(cellId);
				newCellIdStringLength = id.length;
				cellIdInId = true;
				break;
			}
		}
		// cellId is not in id set,add it to set ,the size of set is at most
		// Constant.MAP_LIST_HISTORY_SIZE
		if (cellIdInId == false) {
			int n = Integer.parseInt(Constant.MAP_LIST_HISTORY_SIZE);
			if (n == id.length) {
				for (int i = n - 1; i > 0; i--) {
					id[i] = id[i - 1];
				}
				id[0] = String.valueOf(cellId);
				newCellIdStringLength = id.length;
			} else {
				for (int i = id.length; i > 0; i--) {
					id[i] = id[i - 1];
				}
				id[0] = String.valueOf(cellId);
				newCellIdStringLength = id.length + 1;
			}
		}

		String newCellIdString = id[0];
		for (int i = 1; i < newCellIdStringLength; i++) {
			newCellIdString = newCellIdString + "#" + id[i];
		}

		Log.i(TAG, "in addTOSearchHistory[newCellIdString]:" + newCellIdString);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(Constant.SHARED_PREFERENCE_MAP_HSITORY_KEY,
				newCellIdString);
		editor.commit();
	}

	@Override
	public List<Cell> getSearchHistory() {
		List<Cell> cellList = new ArrayList<Cell>();

		SharedPreferences sp = activity.getSharedPreferences(
				Constant.SHARED_PREFERENCE_MAP_HISTORY_FILE,
				Context.MODE_PRIVATE);
		String cellIdString = sp.getString(
				Constant.SHARED_PREFERENCE_MAP_HSITORY_KEY, "");

		if (cellIdString.equals(""))
			return cellList;
		String[] cellId = cellIdString.split("#");

		cellList.clear();

		for (int i = 0; i < cellId.length; i++) {
			Cell cell = cellService.getCellById(Integer.parseInt(cellId[i]));
			cellList.add(cell);
		}
		return cellList;
	}

	@Override
	public List<Cell> getFrequentPlace() {
		int n = Integer.parseInt(Constant.MAP_LIST_FREQUENT_PLACE_SIZE);

		Cursor cursor = db.rawQuery(" select "
				+ DBConstant.TABLE_FREQUENT_PLACE_FIELD_CELL_ID + " from "
				+ DBConstant.TABLE_FREQUENT_PLACE + "," + DBConstant.TABLE_CELL
				+ " where " + DBConstant.TABLE_FREQUENT_PLACE + "."
				+ DBConstant.TABLE_FREQUENT_PLACE_FIELD_CELL_ID + "="
				+ DBConstant.TABLE_CELL + "." + DBConstant.TABLE_CELL_FIELD_ID
				+ " order by "
				+ DBConstant.TABLE_FREQUENT_PLACE_FIELD_HIT_COUNT
				+ " desc limit " + String.valueOf(n), null);
		activity.startManagingCursor(cursor);

		List<Cell> cellList = new ArrayList<Cell>();
		cellList.clear();

		// Log.i(TAG,cursor.getColumnName(0));

		while (cursor.moveToNext()) {
			int id = cursor
					.getInt(cursor
							.getColumnIndex(DBConstant.TABLE_FREQUENT_PLACE_FIELD_CELL_ID));
			Cell cell = this.getCellById(id);
			// System.out.println(cell.getName());

			cellList.add(cell);
		}

		cursor.close();
		return cellList;
	}

	@Override
	public List<String> getSuggestPlaceName(String pattern) {

		String[] columns = { DBConstant.TABLE_CELL_FIELD_ID,
				DBConstant.TABLE_CELL_FIELD_NAME,
				DBConstant.TABLE_CELL_FIELD_BUILDINGID };
		String selection = "name like \'" + pattern + "%\'";
		Cursor cursor = db.query(DBConstant.TABLE_CELL, columns, selection,
				null, null, null, null);

		List<String> cellList = new ArrayList<String>();
		
		int n = Integer.parseInt(Constant.MAP_LIST_SUGGESTION_SIZE);

		
		int i = 0;
	
		while (cursor.moveToNext()) {
			// int id =
			// cursor.getInt(cursor.getColumnIndex(DBConstant.TABLE_CELL_FIELD_ID));
			String name = cursor.getString(cursor
					.getColumnIndex(DBConstant.TABLE_CELL_FIELD_NAME));
			// int buildingId =
			// cursor.getInt(cursor.getColumnIndex(DBConstant.TABLE_CELL_FIELD_BUILDINGID));

			cellList.add(name);

			i++;

			if (i > n) {
				break;
			}
		}
		cursor.close();

		return cellList;
	}

	@Override
	public Cell getCellById(int id) {
		return cellService.getCellById(id);
	}

	@Override
	public PathDot getPathDotById(int id) {
		return pathDotService.getPathDotById(id);
	}

	@Override
	public String getShortestPath(int sourceId, int destId) {
		return shortestPathService.getShortestPath(sourceId, destId);
	}

}
