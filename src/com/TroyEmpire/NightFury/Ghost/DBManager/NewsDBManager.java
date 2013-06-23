package com.TroyEmpire.NightFury.Ghost.DBManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Entity.News;
import com.TroyEmpire.NightFury.Enum.NewsType;
import com.TroyEmpire.NightFury.Ghost.DBHelper.DAO;
import com.TroyEmpire.NightFury.Util.Util;

public class NewsDBManager extends DAO<News> {

	private SQLiteDatabase db;

	public NewsDBManager(SQLiteDatabase db) {
		super(db);
		this.db = db;
	}

	public News loadSingleEntityFromCursor(Cursor cursor) {
		News news = new News();
		NewsType type = null;
		int newsType = cursor.getInt(cursor
				.getColumnIndex(DBConstant.TABLE_NEWS_FIELD_NEWS_TYPE));
		if (newsType == NewsType.教务处.ordinal())
			type = NewsType.教务处;
		else if (newsType == NewsType.学生处.ordinal())
			type = NewsType.学生处;
		else
			type = NewsType.预制;
		news.setId(cursor.getInt(cursor
				.getColumnIndex(DBConstant.TABLE_NEWS_FIELD_ID)));

		news.setNewsIdOnServer(cursor.getInt(cursor
				.getColumnIndex(DBConstant.TABLE_NEWS_FIELD_NEWS_ID_ON_SERVER)));
		news.setContent(cursor.getString(cursor
				.getColumnIndex(DBConstant.TABLE_NEWS_FIELD_CONTENT)));
		news.setTitle(cursor.getString(cursor
				.getColumnIndex(DBConstant.TABLE_NEWS_FIELD_TITLE)));
		try {
			news.setPublishDate(Util.DATA_FORMATER.parse(cursor.getString(cursor
					.getColumnIndex(DBConstant.TABLE_NEWS_FIELD_PUBLISH_DATE))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		news.setNewsType(type);
		return news;
	}

	/**
	 * add News
	 * 
	 * @param News
	 */
	@Override
	public void save(News news) {

		db.execSQL(
				"INSERT INTO " + DBConstant.TABLE_NEWS
						+ " VALUES(null, ?, ?, ?, ?, ?)",
				new Object[] { news.getTitle(), news.getNewsType().ordinal(),
						news.getNewsIdOnServer(),
						Util.DATA_FORMATER.format(news.getPublishDate()),
						news.getContent() });

	}

	@Override
	public void saveAll(List<News> newsList) {
		for (News news : newsList) {
			save(news);
		}

	}

	@Override
	public List<News> findAll() {
		Cursor c = getQueryCursor(null, DBConstant.TABLE_NEWS);
		List<News> news = loadListEntityFromCursor(c);
		c.close();
		return news;
	}

	@Override
	public News findOne(String condition) {
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_NEWS);
		if (c.getCount() == 0)
			return null;
		c.moveToFirst();
		News news = loadSingleEntityFromCursor(c);
		c.close();
		return news;
	}

	@Override
	public List<News> findMany(String condition) {
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_NEWS);
		List<News> news = loadListEntityFromCursor(c);
		c.close();
		return news;
	}

	@Override
	public List<News> loadListEntityFromCursor(Cursor c) {
		ArrayList<News> news = new ArrayList<News>();
		while (c.moveToNext()) {
			news.add(loadSingleEntityFromCursor(c));
		}
		return news;
	}

	/**
	 * this is the legacy method to handle database, this method is unique to
	 * this class the better method is let the service judge condition, the db
	 * handle execution
	 */
	public void cleanCacheIfTooMuch() {
		Cursor cursor = (Cursor) db.query(DBConstant.TABLE_NEWS,
				new String[] { DBConstant.TABLE_NEWS_FIELD_ID }, null, null,
				null, null, " id desc ",
				String.valueOf(Constant.NEWS_CACHE_LIMIT));
		if (cursor.moveToLast()) {
			int markId = cursor.getInt(cursor
					.getColumnIndex(DBConstant.TABLE_NEWS_FIELD_ID));
			String query = "delete from " + DBConstant.TABLE_NEWS
					+ " where id<" + markId;
			db.execSQL(query);
		}
	}
}
