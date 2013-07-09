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

}
