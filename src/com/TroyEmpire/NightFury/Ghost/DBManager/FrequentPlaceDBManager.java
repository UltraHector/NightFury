package com.TroyEmpire.NightFury.Ghost.DBManager;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Entity.Cell;
import com.TroyEmpire.NightFury.Entity.FrequentPlace;
import com.TroyEmpire.NightFury.Ghost.DBHelper.DAO;

public class FrequentPlaceDBManager extends DAO<FrequentPlace> {

	private SQLiteDatabase db;

	public FrequentPlaceDBManager(SQLiteDatabase db) {
		super(db);
		this.db = db;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(FrequentPlace entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAll(List<FrequentPlace> entityList) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<FrequentPlace> findAll() {
		// TODO Auto-generated method stub
		Cursor c = getQueryCursor(null, DBConstant.TABLE_FREQUENT_PLACE);
		List<FrequentPlace> frequentPlaces = loadListEntityFromCursor(c);
		c.close();
		return frequentPlaces;
	}

	@Override
	public FrequentPlace findOne(String condition) {
		// TODO Auto-generated method stub
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_FREQUENT_PLACE);
		if (c.getCount() == 0)
			return null;
		c.moveToFirst();
		FrequentPlace frequentPlace = loadSingleEntityFromCursor(c);
		c.close();
		return frequentPlace;
	}

	@Override
	public List<FrequentPlace> findMany(String condition) {
		// TODO Auto-generated method stub
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_FREQUENT_PLACE);
		List<FrequentPlace> frequentPlaces = loadListEntityFromCursor(c);
		c.close();
		return frequentPlaces;
	}

	@Override
	public FrequentPlace loadSingleEntityFromCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		FrequentPlace frequentPlace = new FrequentPlace();

		frequentPlace.setId(cursor.getInt(cursor
				.getColumnIndex(DBConstant.TABLE_FREQUENT_PLACE_FIELD_ID)));

		frequentPlace.setCellId(cursor.getInt(cursor
				.getColumnIndex(DBConstant.TABLE_FREQUENT_PLACE_FIELD_CELL_ID)));

		frequentPlace.setHitCount(cursor.getInt(cursor
				.getColumnIndex(DBConstant.TABLE_FREQUENT_PLACE_FIELD_HIT_COUNT)));

		return frequentPlace;
	}

	@Override
	public List<FrequentPlace> loadListEntityFromCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		Cursor c = getQueryCursor(null, DBConstant.TABLE_FREQUENT_PLACE);
		List<FrequentPlace> frequentPlace = loadListEntityFromCursor(c);
		c.close();
		return frequentPlace;
	}

}
