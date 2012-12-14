package cz.cvut.localtrade.helper;

import cz.cvut.localtrade.model.Item;

public class Filter implements Cloneable {

	public String query;

	public int maxDistance = -1;
	public double distanceHighBound;
	public double distanceLowBound;

	public double minPrice = -1;
	public double maxPrice = -1;
	public double priceLowBound;
	public double priceHighBound;

	public boolean stateNew;
	public boolean stateUsed;
	public boolean stateDysfunctional;
	public boolean stateBroken;

	public Filter clone() throws CloneNotSupportedException {
		return (Filter) super.clone();
	}

	public boolean filter(Item item) {
		boolean filter = false;

		double price = item.getPrice();
		if (price < minPrice || price > maxPrice) {
			filter = true;
		}
		float dist = MapUtils.distanceBetween(MapUtils.actualLocation,
				item.getLocation());
		if (dist > maxDistance) {
			filter = true;
		}

		return filter;
	}

}
