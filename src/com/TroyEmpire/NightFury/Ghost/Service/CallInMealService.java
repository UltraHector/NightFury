package com.TroyEmpire.NightFury.Ghost.Service;

import java.util.List;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Constant.DBConstant;
import com.TroyEmpire.NightFury.Entity.Meal;
import com.TroyEmpire.NightFury.Entity.Restaurant;
import com.TroyEmpire.NightFury.Ghost.DBManager.MealDBManager;
import com.TroyEmpire.NightFury.Ghost.DBManager.RestaurantDBManager;
import com.TroyEmpire.NightFury.Ghost.IService.ICallInMealService;

import android.database.sqlite.SQLiteDatabase;

public class CallInMealService implements ICallInMealService {

	private int campusId;
	private String dbFile;
	private SQLiteDatabase db;
	private MealDBManager mealDbManager;
	private RestaurantDBManager restaurantDBManager;

	public CallInMealService(int campusId) {
		this.campusId = campusId;
		this.dbFile = Constant.NIGHTFURY_STORAGE_ROOT + "/Restaurant/Campus_"
				+ campusId + "_Restaurant/RestaurantDB/Restaurant.db";
		this.db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
		mealDbManager = new MealDBManager(db);
		restaurantDBManager = new RestaurantDBManager(db);
	}

	@Override
	public int getNumberOfRestaurant() {
		return restaurantDBManager.findAll().size();
	}

	@Override
	public List<Restaurant> getRestaurantsBookmarked() {
		String condition = " where " + DBConstant.TABLE_RESTAURANT_FIELD_BOOKMARKED + "=1";
		return getRestaurants(condition);
	}

	@Override
	public List<Restaurant> getAllRestaurants() {
		String condition = null;
		return getRestaurants(condition);
	}

	@Override
	public Restaurant getRestaurantById(long id) {
		String condition = " where " + DBConstant.TABLE_RESTAURANT_FIELD_ID + "=" + id;
		return restaurantDBManager.findOne(condition);
	}

	@Override
	public Restaurant getRestaurantByName(String name) {
		String condition = " where " + DBConstant.TABLE_RESTAURANT_FIELD_NAME + "=" + name;
		return restaurantDBManager.findOne(condition);
	}

	@Override
	public List<Meal> getMealsByRestaurantId(long restId) {
		String condition = " where " + DBConstant.TABLE_MEAL_FIELD_RESTAURANT_ID + "="
				+ restId;
		return mealDbManager.findMany(condition);
	}

	@Override
	public List<Meal> getMealsByRestaurantName(String name) {
		long restid = getRestaurantByName(name).getId();
		String condition = " where " + DBConstant.TABLE_MEAL_FIELD_RESTAURANT_ID + "="
				+ restid;
		return mealDbManager.findMany(condition);
	}

	@Override
	public void setRestautantToBookmarked(long id) {
		String query = "update " + DBConstant.TABLE_RESTAURANT + " set "
				+ DBConstant.TABLE_RESTAURANT_FIELD_BOOKMARKED + "=1 where "
				+ DBConstant.TABLE_RESTAURANT_FIELD_ID + "=" + id;
		db.execSQL(query);
	}

	@Override
	public void unsetRestaurantBookmarked(long id) {
		String query = "update " + DBConstant.TABLE_RESTAURANT + " set "
				+ DBConstant.TABLE_RESTAURANT_FIELD_BOOKMARKED + "=0 where "
				+ DBConstant.TABLE_RESTAURANT_FIELD_ID + "=" + id;
		db.execSQL(query);
	}

	@Override
	public List<Restaurant> getRestaurants(String condition) {
		return restaurantDBManager.findMany(condition);
	}

	@Override
	public String getRestaurantLogoPath(long id) {
		return Constant.NIGHTFURY_STORAGE_ROOT + "/Restaurant/Campus_" + this.campusId
				+ "_Restaurant/RestaurantLogo/" + id + ".jpg";
	}

}
