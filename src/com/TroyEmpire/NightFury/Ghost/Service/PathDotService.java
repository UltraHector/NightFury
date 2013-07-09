package com.TroyEmpire.NightFury.Ghost.Service;

import android.database.sqlite.SQLiteDatabase;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Entity.PathDot;
import com.TroyEmpire.NightFury.Ghost.DBManager.BuildingDBManager;
import com.TroyEmpire.NightFury.Ghost.DBManager.CellDBManager;
import com.TroyEmpire.NightFury.Ghost.DBManager.PathDotDBManager;
import com.TroyEmpire.NightFury.Ghost.IService.IPathDotService;

public class PathDotService implements IPathDotService {

	private int campusId;
	private String dbFile;
	private SQLiteDatabase db;
	private PathDotDBManager pathDotDBManager;

	public PathDotService(int campusId) {
		this.campusId = campusId;
		this.dbFile = Constant.NIGHTFURY_STORAGE_ROOT + "/Map/Campus_"
				+ campusId + "_Map/MapDB/Map.db";
		this.db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
		pathDotDBManager = new PathDotDBManager(db);

	}

	@Override
	public PathDot getPathDotById(int id) {
		String condition = " where " + DBConstant.TABLE_PATH_DOT_FIELD_ID + " = " + id;
		return pathDotDBManager.findOne(condition);
	}

}
