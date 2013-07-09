package com.TroyEmpire.NightFury.UI.Activity;

import com.TroyEmpire.NightFury.Enum.JwcAction;
import com.TroyEmpire.NightFury.UI.Fragment.UserJwcInfoDialogFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class TestActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_test);
		UserJwcInfoDialogFragment userInfoDialog;
		userInfoDialog = new UserJwcInfoDialogFragment(this,
				JwcAction.UPDATE_SCHEDULE);
		userInfoDialog.show(getSupportFragmentManager(), "getUserJwcInfo");
	}

}
