package com.TroyEmpire.NightFury.Ghost.Service;

import android.database.sqlite.SQLiteDatabase;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Entity.Cell;
import com.TroyEmpire.NightFury.Ghost.DBManager.CellDBManager;
import com.TroyEmpire.NightFury.Ghost.IService.ICellService;

public class CellService implements ICellService {

	private int campusId;
	private String dbFile;
	private SQLiteDatabase db;
	private CellDBManager cellDBManager;

	public CellService(int campusId) {
		this.campusId = campusId;
		this.dbFile = Constant.NIGHTFURY_STORAGE_ROOT + "/Map/Campus_"
				+ campusId + "_Map/MapDB/Map.db";
		this.db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
		cellDBManager = new CellDBManager(db);
	}

	@Override
	public Cell getCellById(int id) {
		String condition = " where " + DBConstant.TABLE_CELL_FIELD_ID + " = "
				+ id;
		return cellDBManager.findOne(condition);

	}

}
