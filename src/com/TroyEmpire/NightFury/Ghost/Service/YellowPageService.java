package com.TroyEmpire.NightFury.Ghost.Service;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Entity.YellowPageUnit;
import com.TroyEmpire.NightFury.Ghost.DBManager.YellowPageUnitDBManager;
import com.TroyEmpire.NightFury.Ghost.IService.IYellowPageService;

public class YellowPageService implements IYellowPageService {
	
	private int campusId;
	private String dbFile;
	private SQLiteDatabase db;
	private YellowPageUnitDBManager yellowPageUnitDBManager;
	
	public YellowPageService(int campusId){
		this.campusId = campusId;
		this.dbFile = Constant.NIGHTFURY_STORAGE_ROOT + "/YellowPage/Campus_"
				+ this.campusId + "_YellowPage/YellowPageDB/" + Constant.YELLOWPAGE_DB_FILE_NAME;
		this.db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
		yellowPageUnitDBManager = new YellowPageUnitDBManager(this.db);
	}

	@Override
	public List<YellowPageUnit> getAllYellowPageUnits() {
		return yellowPageUnitDBManager.findAll();
	}

	@Override
	public YellowPageUnit getYellowPageUnitById(int id) {
		String condition = " where " + DBConstant.TABLE_YELLOW_PAGE_UNIT_FIELD_ID + "=" + id;
		return yellowPageUnitDBManager.findOne(condition);
	}

}
