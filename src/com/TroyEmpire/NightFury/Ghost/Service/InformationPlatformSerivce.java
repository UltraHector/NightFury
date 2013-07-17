package com.TroyEmpire.NightFury.Ghost.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Entity.News;
import com.TroyEmpire.NightFury.Entity.NewsOnServer;
import com.TroyEmpire.NightFury.Enum.NewsType;
import com.TroyEmpire.NightFury.Ghost.DBHelper.NightFuryDBHelper;
import com.TroyEmpire.NightFury.Ghost.DBManager.NewsDBManager;
import com.TroyEmpire.NightFury.Ghost.IService.IInformationPlatformService;
import com.TroyEmpire.NightFury.Util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class InformationPlatformSerivce implements IInformationPlatformService {

	private int campusId;
	private SQLiteDatabase db;
	private NightFuryDBHelper nightFuryDbHelper;
	private NewsDBManager newsDbManager;

	public InformationPlatformSerivce(int campusId, Context context) {
		this.campusId = campusId;
		nightFuryDbHelper = new NightFuryDBHelper(context);
		db = nightFuryDbHelper.getWritableDatabase();
		newsDbManager = new NewsDBManager(this.db);
		cleanNewsCache();
	}

	// 当缓存的信息过多是，清理newstable的缓存。 
	private void cleanNewsCache() {
		newsDbManager.cleanCacheIfTooMuch();
	}

	@Override
	public void updateNews(NewsType type) {
		String url = Constant.NIGHTFURY_SERVER_URL + "/news";
		List<News> newsList = new ArrayList<News>();

		if (type == null)
			url += "/" + NewsType.预制.ordinal() + "/" + getMaxNewsServerId(type); // parse
																					// 2
																					// means
																					// update
		// all types of news
		else
			url += "/" + type.ordinal() + "/" + getMaxNewsServerId(type);
		try {
			InputStream input = new URL(url).openStream();
			Reader reader = new InputStreamReader(input, "UTF-8");
			// Creates the json object which will manage the information
			// received
			GsonBuilder builder = new GsonBuilder();
			// Register an adapter to manage the date types as long values
			builder.registerTypeAdapter(Date.class,
					new JsonDeserializer<Date>() {
						@Override
						public Date deserialize(JsonElement json, Type typeOfT,
								JsonDeserializationContext context)
								throws JsonParseException {
							return new Date(json.getAsJsonPrimitive()
									.getAsLong());
						}
					});
			Gson json = builder.create();
			NewsOnServer[] newsOnServer = json.fromJson(reader,
					NewsOnServer[].class);
			for (NewsOnServer rawNews : newsOnServer) {
				News news = new News();
				news.setContent(rawNews.getContent());
				news.setNewsIdOnServer(rawNews.getId());
				news.setNewsType(rawNews.getNewsType());
				news.setPublishDate(rawNews.getPublishDate());
				news.setTitle(rawNews.getTitle());
				newsList.add(news);
			}
			newsDbManager.saveAll(newsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private long getMaxNewsServerId(NewsType type) {
		String condition = " where ";
		if (type == null)
			condition = "";
		else {
			switch (type) {
			case 教务处:
				condition += " newsType=" + NewsType.教务处.ordinal();
				break;
			case 学生处:
				condition += " newsType=" + NewsType.学生处.ordinal();
				break;
			}
		}
		condition += " order by id desc limit 1 ";
		News news = newsDbManager.findOne(condition);
		if (news == null)
			return 0;
		else
			return news.getNewsIdOnServer();
	}

	// if the newstype is passed as null, all news will be updated
	public List<News> getNewsFromStorage(NewsType type, Date startDate, int limit) {
		String condition = null;
		if (type != null) {
			condition = " where " + DBConstant.TABLE_NEWS_FIELD_NEWS_TYPE + "="
					+ type.ordinal() + " ";
		}
		if (startDate == null) {
			if (condition == null)
				condition = "";
			condition += " order by publishDate desc limit " + limit;
			return newsDbManager.findMany(condition);
		}
		// need to set it to avoid null + "";
		if (condition == null) {
			condition = " where ";
			condition += " publishDate < " + Util.DATA_FORMATER.format(startDate);
		} else {
			condition += " and publishDate < " + Util.DATA_FORMATER.format(startDate);
		}
		condition += " order by publishDate desc limit " + limit;
		return newsDbManager.findMany(condition);
	}

	@Override
	public News getNewsByid(long id) {
		String condition = DBConstant.TABLE_NEWS_FIELD_ID + "=" + id;
		return newsDbManager.findOne(condition);
	}
}
