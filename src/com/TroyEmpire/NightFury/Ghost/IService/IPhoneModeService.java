package com.TroyEmpire.NightFury.Ghost.IService;

public interface IPhoneModeService {
	int getPhoneMode();
	void setPhoneSilentMode();
	void recoverPhoneOriginalMode(int mode);
}
