package com.TroyEmpire.NightFury.Ghost.IService;

import java.util.List;

import com.TroyEmpire.NightFury.Entity.PhoneModeTimeUnit;

public interface ISmartPhoneViberateService {

	/**
	 * 启动智能振机服务
	 */
	void startSmartPhoneViberateService(
			List<PhoneModeTimeUnit> phoneModeTimeUnits);

	/**
	 * 关闭智能振机服务
	 * @param numberOfIntents the number of created pending intents
	 */
	void stopSmartPhoneViberateService();
	
	/**
	 * set the local flag which indicates whether smart vibrating system is on
	 * @param flag the status to which the phone mode will turn to 
	 * if flag==true, the SVS will be turned on, other wise will be turned on
	 */
	
	void setWhetherSVSIsOn(boolean flag);
	
	/**
	 * @return boolean whether the SVS is on
	 */
	boolean isSVSIsOn();

}
