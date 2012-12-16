package cz.cvut.localtrade.helper;

import android.location.Location;

import com.google.android.maps.GeoPoint;

public class MapUtils {

	public static Location actualLocation;

	public static float distanceBetween(GeoPoint loc1, GeoPoint loc2) {
		float[] results = new float[1];
		Location.distanceBetween(loc1.getLatitudeE6() / 1E6,
				loc1.getLongitudeE6() / 1E6, loc2.getLatitudeE6() / 1E6,
				loc2.getLongitudeE6() / 1E6, results);
		return results[0] / 1000;
	}

	public static GeoPoint getUserGeoPoint() {
		if (actualLocation == null) {
			return new GeoPoint(0, 0);
		}
		return new GeoPoint((int) (actualLocation.getLatitude() * 1E6),
				(int) (actualLocation.getLongitude() * 1E6));
	}

}
