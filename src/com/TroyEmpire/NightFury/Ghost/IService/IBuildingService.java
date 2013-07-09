package com.TroyEmpire.NightFury.Ghost.IService;

import com.TroyEmpire.NightFury.Entity.Building;


public interface IBuildingService {
	
	/**
	 * @param cellName is the name of a in a smaller place building
	 */
	public Building getBuildingByCellName(String cellName);
	
	
	/***
	 * 
	 * @param id of Building
	 * @return specified Buiiding
	 */
	public Building getBuildingByBuildingId(int id);
	
	/***
	 * 
	 * @param id of Building
	 * @return specified Cell
	 */
	public Building getBuildingByCellId(int id);
	

	
	/**
	 * @param latitude the input latitude click by the user
	 * @param longitude the input longitude click by the user
	 */
	public Building getBuildingByLocation(double latitude, double longitude);
	
}
