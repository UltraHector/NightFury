package com.TroyEmpire.NightFury.Ghost.DBHelper;

import com.TroyEmpire.NightFury.Constant.DBConstant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NightFuryDBHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;

	public NightFuryDBHelper(Context context) {
		// CursorFactory设置为null,使用默认值
		super(context, DBConstant.NIGHTFURY_DB_NAME, null, DATABASE_VERSION);
	}

	// 数据库第一次被创建时onCreate会被调用
	@Override
	public void onCreate(SQLiteDatabase db) {
		// news table
		db.execSQL("CREATE TABLE IF NOT EXISTS " + DBConstant.TABLE_NEWS + "("
				+ DBConstant.TABLE_NEWS_FIELD_ID
				+ " INTEGER primary key autoincrement, "
				+ DBConstant.TABLE_NEWS_FIELD_TITLE + " text, "
				+ DBConstant.TABLE_NEWS_FIELD_NEWS_TYPE + " INTEGER, "
				+ DBConstant.TABLE_NEWS_FIELD_NEWS_ID_ON_SERVER + " INTEGER,"
				+ DBConstant.TABLE_NEWS_FIELD_PUBLISH_DATE + " text, "
				+ DBConstant.TABLE_NEWS_FIELD_CONTENT + " text)");
	}

	// 如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// db.execSQL("ALTER TABLE person ADD COLUMN other STRING");

	}
}