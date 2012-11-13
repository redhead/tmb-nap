package cz.cvut.localtrade.model;

import java.io.Serializable;

import com.google.android.maps.GeoPoint;

public class Item implements Serializable {

	public enum State {
		NEW("New"), USED("Used"), DYSFUNCTIONAL("Dysfunctional"), BROKEN(
				"Broken");

		String label;

		State(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return label;
		}

	}

	private static final long serialVersionUID = 1L;
	private long id;
	private String title;
	private State state;
	private String description;
	private double price;
	private GeoPoint location;

	public Item() {

	}

	public Item(String title, State state, String description, double price,
			GeoPoint location) {
		super();
		this.title = title;
		this.state = state;
		this.description = description;
		this.price = price;
		this.location = location;
	}

	@Override
	public String toString() {
		return "Item [title=" + title + ", state=" + state + ", description="
				+ description + ", price=" + price + ", lat="
				+ location.getLatitudeE6() + ", lon="
				+ location.getLongitudeE6() + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public GeoPoint getLocation() {
		return location;
	}

	public void setLocation(GeoPoint location) {
		this.location = location;
	}

}