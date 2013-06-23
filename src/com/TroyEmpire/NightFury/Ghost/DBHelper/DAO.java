package com.TroyEmpire.NightFury.Ghost.DBHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAO<T> implements IDAO<T> {

	private SQLiteDatabase db;

	public DAO(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * get the select query cursor
	 * 
	 * @param condition
	 *            the condition in the SQL where clause
	 * @param tableName
	 *            the table which will be queried
	 */
	public Cursor getQueryCursor(String condition, String tableName) {
		Cursor c = null;
		if (condition != null) {
			c = db.rawQuery("SELECT * FROM " + tableName + condition + ";",
					null);
		} else {
			c = db.rawQuery("SELECT * FROM " + tableName + ";", null);
		}
		return c;
	}

}
