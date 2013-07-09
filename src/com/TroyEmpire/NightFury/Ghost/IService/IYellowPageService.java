package com.TroyEmpire.NightFury.Ghost.IService;

import java.util.List;

import com.TroyEmpire.NightFury.Entity.YellowPageUnit;

public interface IYellowPageService {
	
	public List<YellowPageUnit> getAllYellowPageUnits();
	public YellowPageUnit getYellowPageUnitById(int id);
}
