package com.TroyEmpire.NightFury.Ghost.DBManager;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Entity.Building;
import com.TroyEmpire.NightFury.Entity.Restaurant;
import com.TroyEmpire.NightFury.Entity.Building;
import com.TroyEmpire.NightFury.Ghost.DBHelper.DAO;

public class BuildingDBManager extends DAO<Building> {

	private SQLiteDatabase db;

	public BuildingDBManager(SQLiteDatabase db) {

		super(db);
		this.db = db;

	}

	@Override
	public void save(Building entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAll(List<Building> entityList) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Building> findAll() {
		Cursor c = getQueryCursor(null, DBConstant.TABLE_BUILDING);
		List<Building> buildings = loadListEntityFromCursor(c);
		c.close();
		return buildings;
	}

	@Override
	public Building findOne(String condition) {
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_BUILDING);
		if (c.getCount() == 0)
			return null;
		c.moveToFirst();
		Building building = loadSingleEntityFromCursor(c);
		c.close();
		return building;
	}

	@Override
	public List<Building> findMany(String condition) {
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_BUILDING);
		List<Building> buildings = loadListEntityFromCursor(c);
		c.close();
		return buildings;
	}

	@Override
	public Building loadSingleEntityFromCursor(Cursor cursor) {

		Building building = new Building();

		building.setId(cursor.getInt(cursor
				.getColumnIndex(DBConstant.TABLE_BUILDING_FIELD_ID)));

		building.setName(cursor.getString(cursor
				.getColumnIndex(DBConstant.TABLE_BUILDING_FIELD_NAME)));

		building.setLatitude(cursor.getDouble(cursor
				.getColumnIndex(DBConstant.TABLE_BUILDING_FIELD_LATITUDE)));

		building.setLongitude(cursor.getDouble(cursor
				.getColumnIndex(DBConstant.TABLE_BUILDING_FIELD_LONGITUDE)));

		building.setDescription(cursor.getString(cursor
				.getColumnIndex(DBConstant.TABLE_BUILDING_FIELD_DESCRIPTION)));

		building.setPathDotId(cursor.getInt(cursor
				.getColumnIndex(DBConstant.TABLE_BULIDING_FIELD_PATHDOTID)));

		building.setMiniLatitude(cursor.getDouble(cursor
				.getColumnIndex(DBConstant.TABLE_BUILDING_FIELD_MINLATITUDE)));

		building.setMaxLatitude(cursor.getDouble(cursor
				.getColumnIndex(DBConstant.TABLE_BUILDING_FIELD_MAXLATITUDE)));

		building.setMiniLongitude(cursor.getDouble(cursor
				.getColumnIndex(DBConstant.TABLE_BUILDING_FIELD_MINLONGITUDE)));

		building.setMaxLongitude(cursor.getDouble(cursor
				.getColumnIndex(DBConstant.TABLE_BUILDING_FIELD_MAXLONGITUDE)));

		return building;
	
	}

	@Override
	public List<Building> loadListEntityFromCursor(Cursor cursor) {
		ArrayList<Building> buildings = new ArrayList<Building>();
		while (cursor.moveToNext()) {
			buildings.add(loadSingleEntityFromCursor(cursor));
		}
		return buildings;
		
	}

}
