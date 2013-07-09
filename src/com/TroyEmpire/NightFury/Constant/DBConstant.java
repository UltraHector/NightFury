package com.TroyEmpire.NightFury.Constant;



/**
 * Contains constants used by the SQLite db tables
 */
public class DBConstant {
	
		//nightfury db name, this db doesn't includes restaurant and map.db
		public static final String NIGHTFURY_DB_NAME = "nightfury.db";
	
	
		//地图table name
	    public static final String TABLE_CAMPUS = "CAMPUS";
		public static final String TABLE_BUILDING="BUILDING";
		public static final String TABLE_CELL = "CELL";
		public static final String TABLE_PATH_DOT = "PATH_DOT";
		public static final String TABLE_SHORTEST_PATH = "SHORTEST_PATH";
		public static final String TABLE_FREQUENT_PLACE="FREQUENT_PLACE";//校园最常被访问地点
		public static final String TABLE_NEWS = "NEWS";
		public static final String TABLE_YELLOW_PAGE_UNIT = "YellowPageUnit";
		
		
		//地图building‘s fields	
		public static final String TABLE_BUILDING_FIELD_ID ="id";
		public static final String TABLE_BUILDING_FIELD_NAME = "name";
		public static final String TABLE_BUILDING_FIELD_LATITUDE = "latitude";
		public static final String TABLE_BUILDING_FIELD_LONGITUDE = "longitude";
		public static final String TABLE_BUILDING_FIELD_DESCRIPTION = "description";
		public static final String TABLE_BULIDING_FIELD_PATHDOTID = "pathDotId";
		public static final String TABLE_BUILDING_FIELD_MINLATITUDE = "minLatitude";
		public static final String TABLE_BUILDING_FIELD_MAXLATITUDE = "maxLatitude";
		public static final String TABLE_BUILDING_FIELD_MINLONGITUDE = "minLongitude";
		public static final String TABLE_BUILDING_FIELD_MAXLONGITUDE = "maxLongitude";
		
		//校区Campus's field
		public static final String TABLE_CAMPUS_FIELD_ID = "id";
		public static final String TABLE_CAMPUS_FIELD_NAME = "name";
		public static final String TABLE_CAMPUS_FIELD_RESTAURANT_VERSION = "restaurantDbVersion";
		public static final String TABLE_CAMPUS_FIELD_MAP_VERSION = "mapDbVersion";
		
		
		//地图cell 's fields
		public static final String TABLE_CELL_FIELD_ID = "id";
		public static final String TABLE_CELL_FIELD_NAME = "name";
		public static final String TABLE_CELL_FIELD_BUILDINGID = "buildingId";
		
		//地图的pathDot
		public static final String TABLE_PATH_DOT_FIELD_ID = "id";
		public static final String TABLE_PATH_DOT_FIELD_LATITUDE = "latitude";
		public static final String TABLE_PATH_DOT_FIELD_LONGITUDE = "longitude";
		
		//地图shortestpath
		public static final String TABLE_SHORTEST_PATH_FIELD_ID = "id";
		public static final String TABLE_SHORTEST_PATH_FIELD_SOURCE_ID = "sourceId";
		public static final String TABLE_SHORTEST_PATH_FIELD_DEST_ID = "destId";
		public static final String TABLE_SHORTEST_PATH_FIELD_SHORTEST_PATH = "shortestPath";
		
		//地图frequent place
		public static final String TABLE_FREQUENT_PLACE_FIELD_ID = "id";
		public static final String TABLE_FREQUENT_PLACE_FIELD_CELL_ID="cellId";
		public static final String TABLE_FREQUENT_PLACE_FIELD_HIT_COUNT="hitCount";
		
		
		//餐馆tables
		public static final String TABLE_RESTAURANT = "RESTAURANT";
		public static final String TABLE_MEAL = "MEAL";
		//餐馆fields
		public static final String TABLE_RESTAURANT_FIELD_ID = "id";
		public static final String TABLE_RESTAURANT_FIELD_PHONE_NUMBER = "phoneNumber";
		public static final String TABLE_RESTAURANT_FIELD_DESCRIPTION = "description";
		public static final String TABLE_RESTAURANT_FIELD_MANAGER_NAME = "managerName";
		public static final String TABLE_RESTAURANT_FIELD_TRANSPORTER_NAME = "transporterName";
		public static final String TABLE_RESTAURANT_FIELD_NAME = "name";
		public static final String TABLE_RESTAURANT_FIELD_TYPE = "type";
		public static final String TABLE_RESTAURANT_FIELD_BOOKMARKED = "bookmarked";
		public static final String TABLE_RESTAURANT_FIELD_MINIMUM_ORDER = "minimumOrder";
		public static final String TABLE_RESTAURANT_FIELD_DELIVERTIME = "deliverTime";
		//餐馆提供的meal
		public static final String TABLE_MEAL_FIELD_ID = "id";
		public static final String TABLE_MEAL_FIELD_RESTAURANT_ID = "restaurantId";
		public static final String TABLE_MEAL_FIELD_NAME = "name";
		public static final String TABLE_MEAL_FIELD_PRICE = "price";
		public static final String TABLE_MEAL_FIELD_DESCRIPTION = "description";
		// 信息平台News fields
		public static final String TABLE_NEWS_FIELD_ID = "id";
		public static final String TABLE_NEWS_FIELD_TITLE = "title";
		public static final String TABLE_NEWS_FIELD_CONTENT = "content";
		public static final String TABLE_NEWS_FIELD_NEWS_TYPE = "newsType";
		public static final String TABLE_NEWS_FIELD_NEWS_ID_ON_SERVER = "newIdOnServer";
		public static final String TABLE_NEWS_FIELD_PUBLISH_DATE = "publishDate";
		// 校园黄页YellowPageUnit fields
		public static final String TABLE_YELLOW_PAGE_UNIT_FIELD_ID = "id";
		public static final String TABLE_YELLOW_PAGE_UNIT_FIELD_NAME = "name";
		public static final String TABLE_YELLOW_PAGE_UNIT_FIELD_PHONENUMBER = "phoneNumber";
		public static final String TABLE_YELLOW_PAGE_UNIT_FIELD_MANAGERNAME = "managerName";
		public static final String TABLE_YELLOW_PAGE_UNIT_FIELD_DESCRIPTION = "description";
		


	
}
