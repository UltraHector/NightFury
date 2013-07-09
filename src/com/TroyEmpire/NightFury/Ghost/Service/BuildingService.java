package com.TroyEmpire.NightFury.Ghost.Service;

import android.database.sqlite.SQLiteDatabase;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Entity.Building;
import com.TroyEmpire.NightFury.Entity.Cell;
import com.TroyEmpire.NightFury.Ghost.DBManager.BuildingDBManager;
import com.TroyEmpire.NightFury.Ghost.DBManager.CellDBManager;
import com.TroyEmpire.NightFury.Ghost.DBManager.MealDBManager;
import com.TroyEmpire.NightFury.Ghost.DBManager.RestaurantDBManager;
import com.TroyEmpire.NightFury.Ghost.IService.IBuildingService;

public class BuildingService implements IBuildingService {

	private int campusId;
	private String dbFile;
	private SQLiteDatabase db;
	private BuildingDBManager buildingDBManager;
	private CellDBManager cellDBManager;// 由cellName找Building时用到

	public BuildingService(int campusId) {
		this.campusId = campusId;
		this.dbFile = Constant.NIGHTFURY_STORAGE_ROOT + "/Map/Campus_"
				+ campusId + "_Map/MapDB/Map.db";
		this.db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
		buildingDBManager = new BuildingDBManager(db);
		cellDBManager = new CellDBManager(db);
	}

	@Override
	public Building getBuildingByCellName(String cellName) {
		String condition = " where " + DBConstant.TABLE_CELL_FIELD_NAME + " = "
				+ " \"" + cellName + "\"";

		Cell cell = cellDBManager.findOne(condition);

		long buildingId = cell.getBuildingId();
		condition = " where " + DBConstant.TABLE_BUILDING_FIELD_ID + " = "
				+ buildingId;

		Building building = buildingDBManager.findOne(condition);
		return building;
	}

	@Override
	public Building getBuildingByBuildingId(int id) {
		String condition = " where " + DBConstant.TABLE_BUILDING_FIELD_ID + "="
				+ id;

		Building building = buildingDBManager.findOne(condition);
		return building;
	}

	@Override
	public Building getBuildingByLocation(double latitude, double longitude) {
		String condition = " where "
				+ DBConstant.TABLE_BUILDING_FIELD_MINLATITUDE + " < "
				+ latitude + " and " + latitude + " < "
				+ DBConstant.TABLE_BUILDING_FIELD_MAXLATITUDE + " and "
				+ DBConstant.TABLE_BUILDING_FIELD_MINLONGITUDE + " < "
				+ longitude + " and " + longitude + " < "
				+ DBConstant.TABLE_BUILDING_FIELD_MAXLONGITUDE;

		Building building = buildingDBManager.findOne(condition);
		return building;
	}

	@Override
	public Building getBuildingByCellId(int id) {
		String condition = " where " + DBConstant.TABLE_CELL_FIELD_ID + " = "
				+ id;

		Cell cell = cellDBManager.findOne(condition);
		long buildingId = cell.getBuildingId();

		condition = " where " + DBConstant.TABLE_BUILDING_FIELD_ID + " = "
				+ buildingId;
		Building building = buildingDBManager.findOne(condition);

		return building;
	}
}
