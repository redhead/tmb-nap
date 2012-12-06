package cz.cvut.localtrade.helper;

public class Filter {

	String query;

	double maxDistance;
	double minPrice = -1;
	double maxPrice = -1;

	boolean stateNew;
	boolean stateUsed;
	boolean stateDysfunctional;
	boolean stateBroken;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public double getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(double maxDistance) {
		this.maxDistance = maxDistance;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public boolean isStateNew() {
		return stateNew;
	}

	public void setStateNew(boolean stateNew) {
		this.stateNew = stateNew;
	}

	public boolean isStateUsed() {
		return stateUsed;
	}

	public void setStateUsed(boolean stateUsed) {
		this.stateUsed = stateUsed;
	}

	public boolean isStateDysfunctional() {
		return stateDysfunctional;
	}

	public void setStateDysfunctional(boolean stateDysfunctional) {
		this.stateDysfunctional = stateDysfunctional;
	}

	public boolean isStateBroken() {
		return stateBroken;
	}

	public void setStateBroken(boolean stateBroken) {
		this.stateBroken = stateBroken;
	}

	public boolean hasMinPrice() {
		return minPrice != -1;
	}

	public boolean hasMaxPrice() {
		return maxPrice != -1;
	}
}
