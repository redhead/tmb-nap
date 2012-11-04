package model;

import java.io.Serializable;

public class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String state;
	private String description;
	private double price;
	private double distance; // bude muset byt predelano na position a pocitano
								// z
								// mapy

	public Item(String title, String state, String description, double price,
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
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