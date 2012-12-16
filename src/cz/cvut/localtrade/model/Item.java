package cz.cvut.localtrade.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;

public class Item implements Serializable {

	public enum State {
		NEW("New"), USED("Used"), DYSFUNCTIONAL("Dysfunctional"), BROKEN(
				"Damaged");

		String label;

		State(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return label;
		}

		public static State fromLabel(String value) {
			if (value == null)
				throw new IllegalArgumentException();
			for (State v : values()) {
				if (value.equalsIgnoreCase(v.label)) {
					return v;
				}
			}
			throw new IllegalArgumentException();
		}

	}

	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private State state;
	private String description;
	private double price;
	private int lon;
	private int lat;

	public Item() {
	}

	public Item(String title, State state, String description, double price,
			int lat, int lon) {
		super();
		this.title = title;
		this.state = state;
		this.description = description;
		this.price = price;
		this.lat = lat;
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "Item [title=" + title + ", state=" + state + ", description="
				+ description + ", price=" + price + ", lat=" + lat + ", lon="
				+ lon + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getLon() {
		return lon;
	}

	public void setLon(int lon) {
		this.lon = lon;
	}

	public int getLat() {
		return lat;
	}

	public void setLat(int lat) {
		this.lat = lat;
	}

	public List<NameValuePair> toParams() {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("item[title]", title));
		pairs.add(new BasicNameValuePair("item[state]", state.toString()));
		pairs.add(new BasicNameValuePair("item[description]", description));
		pairs.add(new BasicNameValuePair("item[price]", price + ""));
		pairs.add(new BasicNameValuePair("item[lon]", lon + ""));
		pairs.add(new BasicNameValuePair("item[lat]", lat + ""));
		return pairs;
	}

	public GeoPoint getLocation() {
		return new GeoPoint(lat, lon);
	}

	public static Item fromJSON(JSONObject object) throws JSONException {
		Item item = new Item();
		item.id = object.getInt("id");
		item.title = object.getString("title");
		item.price = object.getDouble("price");
		item.description = object.getString("description");
		item.state = State.fromLabel(object.getString("state"));
		item.lat = object.getInt("lat");
		item.lon = object.getInt("lon");
		return item;
	}

}