package com.TroyEmpire.NightFury.Ghost.Service;

import com.TroyEmpire.NightFury.Ghost.IService.IPhoneModeService;

import android.content.Context;
import android.media.AudioManager;


public class PhoneModeService implements IPhoneModeService {

	private AudioManager audioManager;

	public PhoneModeService(Context context) {
		audioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
	}

	@Override
	public int getPhoneMode() {
		return audioManager.getRingerMode();
	}

	@Override
	public void setPhoneSilentMode() {
		audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	}

	@Override
	public void recoverPhoneOriginalMode(int mode) {
		audioManager.setRingerMode(mode);
	}

}
