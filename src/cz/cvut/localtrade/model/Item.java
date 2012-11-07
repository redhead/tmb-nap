package cz.cvut.localtrade.model;

import java.io.Serializable;

public class Item implements Serializable {

	public enum State {
		NEW("New"),
		USED("Used"),
		DYSFUNCTIONAL("Dysfunctional"),
		BROKEN("Broken");

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
	private String title;
	private State state;
	private String description;
	private double price;
	private double distance; // bude muset byt predelano na position a pocitano
								// z
								// mapy

	public Item() {

	}

	public Item(String title, State state, String description, double price,
			double distance) {
		super();
		this.title = title;
		this.state = state;
		this.description = description;
		this.price = price;
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Item [title=" + title + ", state=" + state + ", description="
				+ description + ", price=" + price + ", distance=" + distance
				+ "]";
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

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}