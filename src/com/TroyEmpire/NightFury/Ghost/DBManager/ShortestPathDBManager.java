package com.TroyEmpire.NightFury.Ghost.DBManager;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Entity.ShortestPath;
import com.TroyEmpire.NightFury.Entity.ShortestPath;
import com.TroyEmpire.NightFury.Ghost.DBHelper.DAO;

public class ShortestPathDBManager extends DAO<ShortestPath> {

	private SQLiteDatabase db;

	public ShortestPathDBManager(SQLiteDatabase db) {
		super(db);
		this.db = db;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(ShortestPath entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAll(List<ShortestPath> entityList) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ShortestPath> findAll() {
		// TODO Auto-generated method stub
		Cursor c = getQueryCursor(null, DBConstant.TABLE_SHORTEST_PATH);
		List<ShortestPath> shortestPath = loadListEntityFromCursor(c);
		c.close();
		return shortestPath;
	}

	@Override
	public ShortestPath findOne(String condition) {
		// TODO Auto-generated method stub
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_SHORTEST_PATH);
		if (c.getCount() == 0)
			return null;
		c.moveToFirst();
		ShortestPath shortestPath = loadSingleEntityFromCursor(c);
		c.close();
		return shortestPath;
	}

	@Override
	public List<ShortestPath> findMany(String condition) {
		// TODO Auto-generated method stub
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_SHORTEST_PATH);
		List<ShortestPath> shortestPath = loadListEntityFromCursor(c);
		c.close();
		return shortestPath;
	}

	@Override
	public ShortestPath loadSingleEntityFromCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		ShortestPath shortestPath = new ShortestPath();

		shortestPath.setId(cursor.getInt(cursor
				.getColumnIndex(DBConstant.TABLE_SHORTEST_PATH_FIELD_ID)));

		shortestPath
				.setSourceId(cursor.getInt(cursor
						.getColumnIndex(DBConstant.TABLE_SHORTEST_PATH_FIELD_SOURCE_ID)));

		shortestPath.setDestId(cursor.getInt(cursor
				.getColumnIndex(DBConstant.TABLE_SHORTEST_PATH_FIELD_DEST_ID)));

		shortestPath
				.setShortestPath(cursor.getString(cursor
						.getColumnIndex(DBConstant.TABLE_SHORTEST_PATH_FIELD_SHORTEST_PATH)));

		return shortestPath;

	}

	@Override
	public List<ShortestPath> loadListEntityFromCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		return null;
	}

}
