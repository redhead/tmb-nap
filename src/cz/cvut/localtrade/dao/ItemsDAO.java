package cz.cvut.localtrade.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.maps.GeoPoint;

import cz.cvut.localtrade.helper.MySQLiteHelper;
import cz.cvut.localtrade.model.Item;
import cz.cvut.localtrade.model.Item.State;

public class ItemsDAO {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_STATE,
			MySQLiteHelper.COLUMN_DESCRIPTION, MySQLiteHelper.COLUMN_PRICE,
			MySQLiteHelper.COLUMN_LON, MySQLiteHelper.COLUMN_LAT };

	public ItemsDAO(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Item createItem(String title, State state, String description,
			double price, int lat, int lon) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_TITLE, title);
		values.put(MySQLiteHelper.COLUMN_STATE, state.name());
		values.put(MySQLiteHelper.COLUMN_DESCRIPTION, description);
		values.put(MySQLiteHelper.COLUMN_PRICE, price);
		values.put(MySQLiteHelper.COLUMN_LAT, lat);
		values.put(MySQLiteHelper.COLUMN_LON, lon);
		long insertId = database.insert(MySQLiteHelper.TABLE_ITEMS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_ITEMS, allColumns,
				MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null,
				null);
		cursor.moveToFirst();
		Item newItem = cursorToItem(cursor);
		cursor.close();
		return newItem;
	}

	public Item editItem(long id, String title, State state,
			String description, double price, int lat, int lon) {
		Item item = find(id);
		deleteItem(item);
		return createItem(title, state, description, price, lat, lon);
	}

	public void deleteItem(Item item) {
		long id = item.getId();
		System.out.println("Item deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_ITEMS, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Item> getAllItems() {
		List<Item> items = new ArrayList<Item>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_ITEMS, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Item item = cursorToItem(cursor);
			items.add(item);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return items;
	}

	private Item cursorToItem(Cursor cursor) {
		Item item = new Item();
		item.setId(cursor.getLong(0));
		item.setTitle(cursor.getString(1));
		item.setState(State.valueOf(cursor.getString(2)));
		item.setDescription(cursor.getString(3));
		item.setPrice(cursor.getDouble(4));
		int lat = cursor.getInt(5);
		int lon = cursor.getInt(6);
		item.setLocation(new GeoPoint(lat, lon));
		return item;
	}

	public Item find(long itemId) {
		Cursor cursor = database.query(MySQLiteHelper.TABLE_ITEMS, allColumns,
				MySQLiteHelper.COLUMN_ID + " = " + itemId, null, null, null,
				null);
		cursor.moveToFirst();
		return cursorToItem(cursor);
	}

}
