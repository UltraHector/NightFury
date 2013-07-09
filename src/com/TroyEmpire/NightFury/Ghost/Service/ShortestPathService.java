package com.TroyEmpire.NightFury.Ghost.Service;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Entity.ShortestPath;
import com.TroyEmpire.NightFury.Ghost.DBManager.ShortestPathDBManager;
import com.TroyEmpire.NightFury.Ghost.IService.IShortestPathService;

public class ShortestPathService implements IShortestPathService {

	private int campusId;
	private String dbFile;
	private SQLiteDatabase db;
	private Activity activity;
	private ShortestPathDBManager shortestPathDBManager;

	public ShortestPathService(int campusId) {
		this.campusId = campusId;
		this.activity = activity;
		this.dbFile = Constant.NIGHTFURY_STORAGE_ROOT + "/Map/Campus_"
				+ campusId + "_Map/MapDB/Map.db";
		this.db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
		shortestPathDBManager = new ShortestPathDBManager(db);
	}

	@Override
	public String getShortestPath(int sourceId, int destId) {
		if (sourceId > destId) {
			int temp = sourceId;
			sourceId = destId;
			destId = temp;
		}

		String condition = " where "
				+ DBConstant.TABLE_SHORTEST_PATH_FIELD_SOURCE_ID + " = "
				+ sourceId + " and "
				+ DBConstant.TABLE_SHORTEST_PATH_FIELD_DEST_ID + " = " + destId;

		ShortestPath shortestPath = shortestPathDBManager.findOne(condition);

		return shortestPath.getShortestPath();
	}

}
