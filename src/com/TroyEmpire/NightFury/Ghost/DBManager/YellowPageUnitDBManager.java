package com.TroyEmpire.NightFury.Ghost.DBManager;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Entity.YellowPageUnit;
import com.TroyEmpire.NightFury.Ghost.DBHelper.DAO;

public class YellowPageUnitDBManager extends DAO<YellowPageUnit> {
	
	private SQLiteDatabase db;

	public YellowPageUnitDBManager(SQLiteDatabase db) {
		super(db);
		this.db = db;
	}

	@Override
	public void save(YellowPageUnit entity) {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveAll(List<YellowPageUnit> entityList) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<YellowPageUnit> findAll() {
		Cursor c = getQueryCursor(null, DBConstant.TABLE_YELLOW_PAGE_UNIT);
		List<YellowPageUnit> yellowPageUnits = loadListEntityFromCursor(c);
		c.close();
		return yellowPageUnits;
	}

	@Override
	public YellowPageUnit findOne(String condition) {
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_YELLOW_PAGE_UNIT);
		if (c.getCount() == 0)
			return null;
		c.moveToFirst();
		YellowPageUnit yellowPageUnit = loadSingleEntityFromCursor(c);
		c.close();
		return yellowPageUnit;
	}

	@Override
	public List<YellowPageUnit> findMany(String condition) {
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_YELLOW_PAGE_UNIT);
		List<YellowPageUnit> yellowPageUnits = loadListEntityFromCursor(c);
		c.close();
		return yellowPageUnits;
	}

	@Override
	public YellowPageUnit loadSingleEntityFromCursor(Cursor corsor) {
		YellowPageUnit yellowPageUnit = new YellowPageUnit();
		yellowPageUnit.setManagerName(corsor.getString(corsor.getColumnIndex(DBConstant.TABLE_YELLOW_PAGE_UNIT_FIELD_MANAGERNAME)));
		yellowPageUnit.setName(corsor.getString(corsor.getColumnIndex(DBConstant.TABLE_YELLOW_PAGE_UNIT_FIELD_NAME)));
		yellowPageUnit.setPhoneNumber(corsor.getString(corsor.getColumnIndex(DBConstant.TABLE_YELLOW_PAGE_UNIT_FIELD_PHONENUMBER)));
		yellowPageUnit.setDescription(corsor.getString(corsor.getColumnIndex(DBConstant.TABLE_YELLOW_PAGE_UNIT_FIELD_DESCRIPTION)));
		yellowPageUnit.setId(corsor.getInt(corsor.getColumnIndex(DBConstant.TABLE_YELLOW_PAGE_UNIT_FIELD_ID)));
		return yellowPageUnit;
	}

	@Override
	public List<YellowPageUnit> loadListEntityFromCursor(Cursor corsor) {
		ArrayList<YellowPageUnit> yellowPageUnits = new ArrayList<YellowPageUnit>();
		while (corsor.moveToNext()) {
			yellowPageUnits.add(loadSingleEntityFromCursor(corsor));
		}
		return yellowPageUnits;
	}

}
