package com.TroyEmpire.NightFury.Ghost.DBManager;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Entity.Meal;
import com.TroyEmpire.NightFury.Ghost.DBHelper.DAO;

public class MealDBManager extends DAO<Meal> {

	private SQLiteDatabase db;

	public MealDBManager(SQLiteDatabase db) {
		super(db);
		this.db = db;
	}

	/**
	 * get a list of meals by restaurant's id
	 * 
	 * @param restaurantId
	 *            the restaurant's id
	 */
	public List<Meal> getMealsByRestaurantId(long restaurantId) {
		String condition = " where " + DBConstant.TABLE_MEAL_FIELD_RESTAURANT_ID + "='"
				+ restaurantId + "'";
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_MEAL);
		List<Meal> ccus = loadListEntityFromCursor(c);
		c.close();
		return ccus;
	}

	public Meal loadSingleEntityFromCursor(Cursor cursor) {
		Meal meal = new Meal();
		meal.setDescription(cursor.getString(cursor
				.getColumnIndex(DBConstant.TABLE_MEAL_FIELD_DESCRIPTION)));
		meal.setId(cursor.getLong(cursor
				.getColumnIndex(DBConstant.TABLE_MEAL_FIELD_ID)));
		meal.setName(cursor.getString(cursor
				.getColumnIndex(DBConstant.TABLE_MEAL_FIELD_NAME)));
		meal.setPrice(cursor.getFloat(cursor
				.getColumnIndex(DBConstant.TABLE_MEAL_FIELD_PRICE)));
		meal.setRestaurantId(cursor.getLong(cursor
				.getColumnIndex(DBConstant.TABLE_MEAL_FIELD_RESTAURANT_ID)));
		return meal;
	}

	public List<Meal> loadListEntityFromCursor(Cursor c) {
		ArrayList<Meal> meals = new ArrayList<Meal>();
		while (c.moveToNext()) {
			meals.add(loadSingleEntityFromCursor(c));
		}
		return meals;
	}

	@Override
	public List<Meal> findAll() {
		Cursor c = getQueryCursor(null, DBConstant.TABLE_MEAL);
		List<Meal> meals = loadListEntityFromCursor(c);
		c.close();
		return meals;
	}

	@Override
	public Meal findOne(String condition) {
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_MEAL);
		if (c.getCount() == 0)
			return null;
		c.moveToFirst();
		Meal meal = loadSingleEntityFromCursor(c);
		c.close();
		return meal;
	}

	@Override
	public List<Meal> findMany(String condition) {
		Cursor c = getQueryCursor(condition, DBConstant.TABLE_MEAL);
		List<Meal> meals = loadListEntityFromCursor(c);
		c.close();
		return meals;
	}

	@Override
	public void save(Meal entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveAll(List<Meal> entityList) {
		// TODO Auto-generated method stub
		
	}
}
