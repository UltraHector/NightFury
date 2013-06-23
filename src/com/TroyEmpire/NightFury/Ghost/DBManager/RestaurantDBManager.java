package com.TroyEmpire.NightFury.Ghost.DBManager;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Entity.Meal;
import com.TroyEmpire.NightFury.Entity.Restaurant;
import com.TroyEmpire.NightFury.Ghost.DBHelper.DAO;

public class RestaurantDBManager extends DAO<Restaurant> {

	private SQLiteDatabase db;

	public RestaurantDBManager(SQLiteDatabase db) {
		super(db);
		this.db = db;
	}

	@Override
	public Restaurant loadSingleEntityFromCursor(Cursor cursor) {
		Restaurant restaurant = new Restaurant();
		restaurant.setBookmarked(cursor.getInt(cursor
				.getColumnIndex(DBConstant.TABLE_RESTAURANT_FIELD_BOOKMARKED)));
		restaurant
				.setDeliverTime(cursor.getInt(cursor
						.getColumnIndex(DBConstant.TABLE_RESTAURANT_FIELD_DELIVERTIME)));
		restaurant
				.setDescription(cursor.getString(cursor
						.getColumnIndex(DBConstant.TABLE_RESTAURANT_FIELD_DESCRIPTION)));
		restaurant.setId(cursor.getLong(cursor
				.getColumnIndex(DBConstant.TABLE_RESTAURANT_FIELD_ID)));
		restaurant
				.setManagerName(cursor.getString(cursor
						.getColumnIndex(DBConstant.TABLE_RESTAURANT_FIELD_MANAGER_NAME)));
		restaurant
				.setMinimumOrder(cursor.getInt(cursor
						.getColumnIndex(DBConstant.TABLE_RESTAURANT_FIELD_MINIMUM_ORDER)));
		restaurant.setName(cursor.getString(cursor
				.getColumnIndex(DBConstant.TABLE_RESTAURANT_FIELD_NAME)));
		restaurant
				.setPhoneNumber(cursor.getString(cursor
						.getColumnIndex(DBConstant.TABLE_RESTAURANT_FIELD_PHONE_NUMBER)));
		restaurant
				.setTransporterName(cursor.getString(cursor
						.getColumnIndex(DBConstant.TABLE_RESTAURANT_FIELD_TRANSPORTER_NAME)));
		restaurant.setType(cursor.getString(cursor
				.getColumnIndex(DBConstant.TABLE_RESTAURANT_FIELD_TYPE)));
		return restaurant;
	}

	public List<Restaurant> loadListEntityFromCursor(Cursor c) {
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		while (c.moveToNext()) {
			restaurants.add(loadSingleEntityFromCursor(c));
		}
		return restaurants;
	}

	@Override
	public List<Restaurant> findAll() {
		Cursor c = getQueryCursor(null, DBConstant.TABLE_RESTAURANT);
		List<Restaurant> restaurants = loadListEntityFromCursor(c);
		c.close();
		return restaurants;
	}

	@Override
	public Restaurant findOne(String condition) {
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_RESTAURANT);
		if (c.getCount() == 0)
			return null;
		c.moveToFirst();
		Restaurant restaurant = loadSingleEntityFromCursor(c);
		c.close();
		return restaurant;
	}

	@Override
	public List<Restaurant> findMany(String condition) {
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_RESTAURANT);
		List<Restaurant> restaurants = loadListEntityFromCursor(c);
		c.close();
		return restaurants;
	}

	@Override
	public void save(Restaurant entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAll(List<Restaurant> entityList) {
		// TODO Auto-generated method stub

	}
}
