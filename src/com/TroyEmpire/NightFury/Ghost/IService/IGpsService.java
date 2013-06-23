package com.TroyEmpire.NightFury.Ghost.IService;

import com.TroyEmpire.NightFury.Entity.LocationPosition;

import android.location.Location;

public interface IGpsService {
	public double getCurrentLatitude();

	public double getCurrentLongitude();

	public Location getCurrentLocation();

	public LocationPosition getLocationPosition();

	public String getProvider();
}
