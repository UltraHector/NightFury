package com.TroyEmpire.NightFury.Ghost.Service;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Entity.LocationPosition;
import com.TroyEmpire.NightFury.Entity.Rectangle;
import com.TroyEmpire.NightFury.Ghost.IService.IGpsService;
import com.TroyEmpire.NightFury.Util.MapUtil;

public class GpsService implements IGpsService, LocationListener {

	private LocationManager locationManager;
	private Location location;
	private Criteria criteria;
	private String provider;

	public GpsService(LocationManager locationManager) {
		this.locationManager = locationManager;
		this.criteria = new Criteria();
		this.criteria.setAccuracy(Criteria.ACCURACY_FINE); // Use a finer
															// accuracy
		provider = locationManager.getBestProvider(this.criteria, true);
		if (provider != null)
			this.location = locationManager.getLastKnownLocation(provider);
		else
			this.location = null;

	}

	public String getProvider() {
		return provider;
	}

	@Override
	/***
	 * return current latitude in GoogleMap
	 */
	public double getCurrentLatitude() {
		if (this.location != null)
			return location.getLatitude();
		else
			return 0;
	}

	@Override
	/**
	 * return current longitude in GoogleMap
	 */
	public double getCurrentLongitude() {
		if (this.location != null)
			return location.getLongitude();
		else
			return 0;
	}

	@Override
	/**
	 * return inner type location,real location in googleMap
	 */
	public Location getCurrentLocation() {
		return this.location;
	}

	@Override
	/***
	 * return Loaction Position in GoogleMap
	 */
	public LocationPosition getLocationPosition() {
		LocationPosition position = new LocationPosition();
		if (this.location != null) {
			position.setLatitude(location.getLatitude());
			position.setLongitude(location.getLongitude());
		} else {
			position.setLongitude(0);
			position.setLongitude(0);
		}
		return position;
	}

	@Override
	public void onLocationChanged(Location location) {
		this.location = location;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

}
