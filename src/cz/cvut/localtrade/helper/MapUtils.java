package cz.cvut.localtrade.helper;

import android.location.Location;

import com.google.android.maps.GeoPoint;

public class MapUtils {

	public static GeoPoint actualLocation = new GeoPoint((int) (50 * 1E6), (int) (14 * 1E6));

	public static float distanceBetween(GeoPoint loc1, GeoPoint loc2) {
		float[] results = new float[1];
		Location.distanceBetween(loc1.getLatitudeE6() / 1E6, loc1.getLongitudeE6() / 1E6,
				loc2.getLatitudeE6() / 1E6, loc2.getLongitudeE6() / 1E6, results);
		return results[0] / 1000;
	}

}
