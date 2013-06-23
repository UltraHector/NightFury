package com.TroyEmpire.NightFury.Ghost.DBManager;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Entity.Cell;
import com.TroyEmpire.NightFury.Entity.PathDot;
import com.TroyEmpire.NightFury.Ghost.DBHelper.DAO;

public class CellDBManager extends DAO<Cell>{

	private SQLiteDatabase db;
	
	
	public CellDBManager(SQLiteDatabase db) {
		super(db);
		this.db = db;
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void save(Cell entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveAll(List<Cell> entityList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Cell> findAll() {
		// TODO Auto-generated method stub
		Cursor c = getQueryCursor(null, DBConstant.TABLE_CELL);
		List<Cell> cells = loadListEntityFromCursor(c);
		c.close();
		return cells;
	}

	@Override
	public Cell findOne(String condition) {
		// TODO Auto-generated method stub
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_CELL);
		if (c.getCount() == 0)
			return null;
		c.moveToFirst();
		Cell cell = loadSingleEntityFromCursor(c);
		c.close();
		return cell;
	}

	@Override
	public List<Cell> findMany(String condition) {
		// TODO Auto-generated method stub
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_CELL);
		List<Cell> cells = loadListEntityFromCursor(c);
		c.close();
		return cells;
	}

	@Override
	public Cell loadSingleEntityFromCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		Cell cell = new Cell();

		cell.setId(cursor.getInt(cursor.getColumnIndex(DBConstant.TABLE_CELL_FIELD_ID)));
		
		cell.setName(cursor.getString(cursor.getColumnIndex(DBConstant.TABLE_CELL_FIELD_NAME)));
		
		cell.setBuildingId(cursor.getInt(cursor.getColumnIndex(DBConstant.TABLE_CELL_FIELD_BUILDINGID)));
		
		return cell;
	}

	@Override
	public List<Cell> loadListEntityFromCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		Cursor c = getQueryCursor(null, DBConstant.TABLE_CELL);
		List<Cell> cells = loadListEntityFromCursor(c);
		c.close();
		return cells;
	}

}
