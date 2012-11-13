package cz.cvut.localtrade.helper;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;

import cz.cvut.localtrade.model.Item;
import cz.cvut.localtrade.model.Item.State;

public class ItemListHelper {

	public static List<Item> items = new ArrayList<Item>();

	static {
		items.add(new Item("Red chair", State.NEW, "Description of red chair",
				982, new GeoPoint((int) (50 * 1E6), (int) (14 * 1E6))));
		items.add(new Item("Blue chair", State.USED,
				"Description of blue chair", 999, new GeoPoint(
						(int) (50.01 * 1E6), (int) (14.01 * 1E6))));
		items.add(new Item("Chair red", State.NEW, "Description of red chair",
				890, new GeoPoint((int) (50.02 * 1E6), (int) (14.02 * 1E6))));
	}

}
