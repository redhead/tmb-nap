package cz.cvut.localtrade.helper;

import cz.cvut.localtrade.model.Item;
import cz.cvut.localtrade.model.Item.State;

public class Filter implements Cloneable {
	
	public static Filter currentFilter = null;

	public String query;

	public int maxDistance = -1;
	public double distanceHighBound;
	public double distanceLowBound;

	public double minPrice = -1;
	public double maxPrice = -1;
	public double priceLowBound;
	public double priceHighBound;

	public boolean stateNew = true;
	public boolean stateUsed = true;
	public boolean stateDysfunctional = true;
	public boolean stateBroken = true;

	public Filter clone() throws CloneNotSupportedException {
		return (Filter) super.clone();
	}

	public boolean filter(Item item) {
		boolean filter = false;

		double price = item.getPrice();
		if (price < minPrice || price > maxPrice) {
			filter = true;
		}
		float dist = MapUtils.distanceBetween(MapUtils.getUserGeoPoint(),
				item.getLocation());
		if (dist > maxDistance) {
			filter = true;
		}
		if(item.getState() == State.NEW && !stateNew) {
			filter = true;
		}
		if(item.getState() == State.USED && !stateUsed) {
			filter = true;
		}
		if(item.getState() == State.DYSFUNCTIONAL && !stateDysfunctional) {
			filter = true;
		}
		if(item.getState() == State.BROKEN && !stateBroken) {
			filter = true;
		}

		return filter;
	}

}
