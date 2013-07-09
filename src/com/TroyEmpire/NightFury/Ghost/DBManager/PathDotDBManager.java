package com.TroyEmpire.NightFury.Ghost.DBManager;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Entity.Restaurant;
import com.TroyEmpire.NightFury.Entity.PathDot;
import com.TroyEmpire.NightFury.Entity.PathDot;
import com.TroyEmpire.NightFury.Ghost.DBHelper.DAO;

public class PathDotDBManager extends DAO<PathDot> {

	private SQLiteDatabase db;

	public PathDotDBManager(SQLiteDatabase db) {
		super(db);
		this.db = db;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(PathDot entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAll(List<PathDot> entityList) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<PathDot> findAll() {
		// TODO Auto-generated method stub
		Cursor c = getQueryCursor(null, DBConstant.TABLE_PATH_DOT);
		List<PathDot> pathDots = loadListEntityFromCursor(c);
		c.close();
		return pathDots;
	}

	@Override
	public PathDot findOne(String condition) {
		// TODO Auto-generated method stub
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_PATH_DOT);
		if (c.getCount() == 0)
			return null;
		c.moveToFirst();
		PathDot pathDot = loadSingleEntityFromCursor(c);
		c.close();
		return pathDot;
	}

	@Override
	public List<PathDot> findMany(String condition) {
		// TODO Auto-generated method stub
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_PATH_DOT);
		List<PathDot> pathDots = loadListEntityFromCursor(c);
		c.close();
		return pathDots;
	}

	@Override
	public PathDot loadSingleEntityFromCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		PathDot pathDot = new PathDot();

		pathDot.setLatitude(cursor.getDouble(cursor
				.getColumnIndex(DBConstant.TABLE_PATH_DOT_FIELD_LATITUDE)));

		pathDot.setLongitude(cursor.getDouble(cursor
				.getColumnIndex(DBConstant.TABLE_PATH_DOT_FIELD_LONGITUDE)));

		pathDot.setId(cursor.getInt(cursor
				.getColumnIndex(DBConstant.TABLE_PATH_DOT_FIELD_ID)));

		return pathDot;

	}

	@Override
	public List<PathDot> loadListEntityFromCursor(Cursor cursor) {
		// TODO Auto-generated method stub

		Cursor c = getQueryCursor(null, DBConstant.TABLE_PATH_DOT);
		List<PathDot> pathDots = loadListEntityFromCursor(c);
		c.close();
		return pathDots;
	}

}
