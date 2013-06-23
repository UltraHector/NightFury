package com.TroyEmpire.NightFury.Ghost.IService;

import java.util.List;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Entity.Building;
import com.TroyEmpire.NightFury.Entity.Cell;
import com.TroyEmpire.NightFury.Entity.PathDot;

public interface IMapService {
	public void addToSearchHistory(int cellId);

	public List<Cell> getSearchHistory();

	/**
	 * 
	 * @return a list of cell order by hitcount,the size of list equals to
	 *         Constant.MAP_LIST_FREQUENT_PLACE_SIZE.
	 * */
	public List<Cell> getFrequentPlace();

	/**
	 * 
	 * @param pattern
	 * @return a list of String which is the name of Cells we search for,the
	 *         size of list equals to Constant.MAP_LIST_SUGGESTION_SIZE.
	 */
	public List<String> getSuggestPlaceName(String pattern);

	// public Building getBuildingById(int id);
	public Cell getCellById(int id);

	/**
	 * @param id
	 *            of pathDot
	 * @return pathDot
	 * */
	public PathDot getPathDotById(int id);

	/**
	 * @param id
	 *            of sourcePathDot , id of destPathDot
	 * @return a String of id which consist a shortest path.
	 * */
	public String getShortestPath(int sourceId, int destId);

}
