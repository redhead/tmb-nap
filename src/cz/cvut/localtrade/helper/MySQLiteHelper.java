package cz.cvut.localtrade.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	// // messages
	// public static final String TABLE_MESSAGES = "messages";
	// public static final String COLUMN_ID = "_id";
	// public static final String COLUMN_MESSAGE = "message";
	// public static final String COLUMN_LEFT = "left";
	// users
	public static final String TABLE_USERS = "users";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_FIRSTNAME = "firstname";
	public static final String COLUMN_LASTNAME = "lastname";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_PASSWORD = "password";

	private static final String DATABASE_NAME = "database.db";
	private static final int DATABASE_VERSION = 1;

	// // Database creation sql statement
	// private static final String MESSAGES_TABLE_CREATE = "create table "
	// + TABLE_MESSAGES + "(" + COLUMN_ID
	// + " integer primary key autoincrement, " + COLUMN_MESSAGE
	// + " text not null, " + COLUMN_LEFT + " text not null);";

	private static final String USERS_TABLE_CREATE = "create table "
			+ TABLE_USERS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_USERNAME
			+ " text not null, " + COLUMN_FIRSTNAME + " text, "
			+ COLUMN_LASTNAME + " text, " + COLUMN_EMAIL + " text not null, "
			+ COLUMN_PASSWORD + " text not null);";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// database.execSQL(MESSAGES_TABLE_CREATE);
		database.execSQL(USERS_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		// db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		onCreate(db);
	}

}
