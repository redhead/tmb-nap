package cz.cvut.localtrade.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cz.cvut.localtrade.helper.MySQLiteHelper;
import cz.cvut.localtrade.model.User;

public class UsersDAO {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_USERNAME, MySQLiteHelper.COLUMN_FIRSTNAME,
			MySQLiteHelper.COLUMN_LASTNAME, MySQLiteHelper.COLUMN_EMAIL,
			MySQLiteHelper.COLUMN_PASSWORD };

	public UsersDAO(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public User createUser(String username, String firstname, String lastname,
			String email, String password) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_USERNAME, username);
		values.put(MySQLiteHelper.COLUMN_FIRSTNAME, firstname);
		values.put(MySQLiteHelper.COLUMN_LASTNAME, lastname);
		values.put(MySQLiteHelper.COLUMN_EMAIL, email);
		values.put(MySQLiteHelper.COLUMN_PASSWORD, password);
		long insertId = database.insert(MySQLiteHelper.TABLE_USERS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS, allColumns,
				MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null,
				null);
		cursor.moveToFirst();
		User newUser = cursorToUser(cursor);
		cursor.close();
		return newUser;
	}

	public void deleteUser(User user) {
		long id = user.getId();
		System.out.println("User deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_USERS, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User user = cursorToUser(cursor);
			users.add(user);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return users;
	}

	private User cursorToUser(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getLong(0));
		user.setUsername(cursor.getString(1));
		user.setFirstName(cursor.getString(2));
		user.setLastName(cursor.getString(3));
		user.setEmail(cursor.getString(4));
		user.setPassword(cursor.getString(5));
		return user;
	}

}
