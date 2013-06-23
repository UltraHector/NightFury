package com.TroyEmpire.NightFury.Ghost.IService;

public interface IAndroidServiceManagerService {

	/**
	 * check whether the service is running
	 * 
	 * @param the
	 *            complete classname including the package names
	 * @return true is the service is running now, false if it is not running
	 */
	boolean checkServiceRunningByName(String className);
}
