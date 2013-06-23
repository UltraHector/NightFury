package com.TroyEmpire.NightFury.Ghost.DBHelper;

import java.util.List;

import android.database.Cursor;

public interface IDAO<T> {
	/**
	 * get the cursor for a query with a specific condition
	 * 
	 * @param condition
	 *            which is the clause after where, include limit etc.
	 * @param tableName
	 *            the name of the table which will be queried
	 */
	public Cursor getQueryCursor(String condition, String tableName);

	/**
	 * save an entity to the database
	 * 
	 * @param <T>
	 */
	public void save(T entity);

	/**
	 * save a list of entities to the database
	 */
	public void saveAll(List<T> entityList);

	/**
	 * get all the entities
	 */
	public List<T> findAll();

	/**
	 * get on specific entity according to a condition
	 */
	public T findOne(String condition);

	/**
	 * get a list of entities according to a condition
	 */
	public List<T> findMany(String condition);

	/**
	 * load an entity from cursor
	 */
	public T loadSingleEntityFromCursor(Cursor cursor);

	/**
	 * load a list of entity from cursor
	 */
	public List<T> loadListEntityFromCursor(Cursor cursor);

}
