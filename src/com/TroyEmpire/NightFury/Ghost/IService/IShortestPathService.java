package com.TroyEmpire.NightFury.Ghost.IService;

import com.TroyEmpire.NightFury.Entity.Building;

public interface IShortestPathService {
	// get the shortest path
	public String getShortestPath(Building sourceId, Building destId);
}