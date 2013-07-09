package com.TroyEmpire.NightFury.Util;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Entity.LocationPosition;
import com.TroyEmpire.NightFury.Entity.Rectangle;

public class MapUtil {

	/***
	 * generate 4 parameters for 2 equations: UTIL_LINEAR_EQUATION_PARAMETER_A4X
	 * UTIL_LINEAR_EQUATION_PARAMETER_B4X UTIL_LINEAR_EQUATION_PARAMETER_A4Y
	 * UTIL_LINEAR_EQUATION_PARAMETER_B4Y after generating 4 parameters
	 * above,copying them and paste to file Constant.java
	 */
	public static void generateEquationParameter() {

		final double GPS_JNU_FROM_GOOGLE_MAP_LEFT_TOP_LATITUDE = 23.137094;
		final double GPS_JNU_FROM_GOOGLE_MAP_LEFT_TOP_LONGITUDE = 113.342053;
		final double GPS_JNU_FROM_GOOGLE_MAP_RIGHT_BOTTOM_LATITUDE = 23.125057;
		final double GPS_JNU_FROM_GOOGLE_MAP_RIGHT_BOTTOM_LONGITUDE = 113.352084;
		final double GPS_JNU_FROM_CAMPUS_PICTURE_LEFT_TOP_LATITUDE = 85.004559;
		final double GPS_JNU_FROM_CAMPUS_PICTURE_LEFT_TOP_LONGITUDE = -130.029851;
		final double GPS_JNU_FROM_CAMPUS_PICTURE_RIGHT_BOTTOM_LATITUDE = -85;
		final double GPS_JNU_FROM_CAMPUS_PICTURE_RIGHT_BOTTOM_LONGITUDE = 128.955224;

		Rectangle googleMapRectangle = new Rectangle();
		Rectangle campusPictureRectangle = new Rectangle();

		LocationPosition googleMapLeftTop = new LocationPosition();
		LocationPosition googleMapRightBottom = new LocationPosition();
		LocationPosition campusPictureLeftTop = new LocationPosition();
		LocationPosition campusPictureRightBottom = new LocationPosition();

		googleMapLeftTop.setLatitude(GPS_JNU_FROM_GOOGLE_MAP_LEFT_TOP_LATITUDE);
		googleMapLeftTop
				.setLongitude(GPS_JNU_FROM_GOOGLE_MAP_LEFT_TOP_LONGITUDE);
		googleMapRightBottom
				.setLatitude(GPS_JNU_FROM_GOOGLE_MAP_RIGHT_BOTTOM_LATITUDE);
		googleMapRightBottom
				.setLongitude(GPS_JNU_FROM_GOOGLE_MAP_RIGHT_BOTTOM_LONGITUDE);
		campusPictureLeftTop
				.setLatitude(GPS_JNU_FROM_CAMPUS_PICTURE_LEFT_TOP_LATITUDE);
		campusPictureLeftTop
				.setLongitude(GPS_JNU_FROM_CAMPUS_PICTURE_LEFT_TOP_LONGITUDE);
		campusPictureRightBottom
				.setLatitude(GPS_JNU_FROM_CAMPUS_PICTURE_RIGHT_BOTTOM_LATITUDE);
		campusPictureRightBottom
				.setLongitude(GPS_JNU_FROM_CAMPUS_PICTURE_RIGHT_BOTTOM_LONGITUDE);

		googleMapRectangle.setLeftTop(googleMapLeftTop);
		googleMapRectangle.setRightBottom(googleMapRightBottom);
		campusPictureRectangle.setLeftTop(campusPictureLeftTop);
		campusPictureRectangle.setRightBottom(campusPictureRightBottom);

		/**
		 * 产生方程参数，以后getTransFormLocationPosition()会用到。
		 */
		getLinearEquationParameter(googleMapRectangle, campusPictureRectangle);

	}

	/***
	 * 
	 * @param from
	 *            ：googleMap的矩形
	 * @param to
	 *            ：campusPicture的矩形
	 *            产生线性方程参数，放在a4Latitude,b4Latitude,a4Longitude,
	 *            b4Longitude中，以后getTransFormLocationPosition ()会有用的。
	 * 
	 *            统一写成X = b*x + a的形式，写成两个方程即: latitudeTo =
	 *            (b4Latitude)*latitudeFrom +(a4Latitude); longitudeTo=
	 *            (b4Longitude)*longitudeFrom + (a4Longitude);
	 */

	private static void getLinearEquationParameter(Rectangle from, Rectangle to) {
		double temp;

		double a4Latitude;
		double b4Latitude;
		double b4Longitude;
		double a4Longitude;

		temp = (to.getRightBottom().getLatitude() - to.getLeftTop()
				.getLatitude())
				/ (from.getRightBottom().getLatitude() - from.getLeftTop()
						.getLatitude());
		b4Latitude = temp;
		a4Latitude = to.getLeftTop().getLatitude() - temp
				* from.getLeftTop().getLatitude();

		temp = (to.getRightBottom().getLongitude() - to.getLeftTop()
				.getLongitude())
				/ (from.getRightBottom().getLongitude() - from.getLeftTop()
						.getLongitude());
		b4Longitude = temp;
		a4Longitude = to.getLeftTop().getLongitude() - temp
				* from.getLeftTop().getLongitude();

		System.out.println("UTIL_LINEAR_EQUATION_PARAMETER_A4LATITUDE= "
				+ a4Latitude);
		System.out.println("UTIL_LINEAR_EQUATION_PARAMETER_B4LATITUDE= "
				+ b4Latitude);
		System.out.println("UTIL_LINEAR_EQUATION_PARAMETER_A4LONGITUDE= "
				+ a4Longitude);
		System.out.println("UTIL_LINEAR_EQUATION_PARAMETER_B4LONGITUDE= "
				+ b4Longitude);

	}

	/**
	 * 
	 * @param from
	 *            which is the location(LocationPosition) in googleMap
	 * @return the location(LocationPosition) in campusPicture
	 */
	public static LocationPosition getTransFormLocation(LocationPosition from) {
		LocationPosition to = new LocationPosition();
		to.setLatitude(Constant.UTIL_LINEAR_EQUATION_PARAMETER_B4LATITUDE
				* from.getLatitude()
				+ Constant.UTIL_LINEAR_EQUATION_PARAMETER_A4LATITUDE);
		to.setLongitude(Constant.UTIL_LINEAR_EQUATION_PARAMETER_B4LONGITUDE
				* from.getLongitude()
				+ Constant.UTIL_LINEAR_EQUATION_PARAMETER_A4LONGITUDE);
		return to;
	}

	/**
	 * 
	 * @param latitude in GoogleMap
	 * @return latitude in campusPicture
	 */
	public static double getCurrentLatitute(double latitude) {
		return Constant.UTIL_LINEAR_EQUATION_PARAMETER_B4LATITUDE*latitude + Constant.UTIL_LINEAR_EQUATION_PARAMETER_A4LATITUDE ;
	}

	/**
	 * 
	 * @param longitude in GoogleMap
	 * @return longitude in campusPicture
	 */
	public static double getCurrentLongitude(double longitude) {
		return Constant.UTIL_LINEAR_EQUATION_PARAMETER_B4LONGITUDE * longitude + Constant.UTIL_LINEAR_EQUATION_PARAMETER_A4LONGITUDE;
	}

}
