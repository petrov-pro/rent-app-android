package ua.org.rent.library;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import ua.org.rent.R;
import ua.org.rent.settings.RentAppState;

public class DB extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "rentapp.sqlite";

	public static final String TABLE_IMAGES = "image_cache";
	
	//city table
	public static final String DB_TABLE_CITY = "cities";
	public static final String TABLE_CITY_TITLE = "title";

	private static final int DATABASE_VERSION = 3;
	private volatile static DB sInstance;
	private final Context mContext;

	public static SQLiteDatabase getDb() {
		if (sInstance == null) {
			sInstance = new DB(RentAppState.getAppInstance());
		}
		return sInstance.getWritableDatabase();
	}

	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	private DB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTables(db);
	}

	private void createTables(SQLiteDatabase db) {
		 String[] items = mContext.getResources().getStringArray(
				R.array.DDL);
		for (String ddl : items) {
			db.execSQL(ddl);
		}
		
		//start data
		items = mContext.getResources().getStringArray(
				R.array.START_DATA);
		for (String ddl : items) {
			db.execSQL(ddl);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Context context = RentAppState.getAppInstance();
		Editor editor = context.getSharedPreferences(context.getPackageName(),
				Context.MODE_PRIVATE).edit();
		editor.putString("access_token", null);
		editor.putBoolean("isBuildDbFromLocalAssets", true);
		editor.commit();

		deleteTables(db);
		createTables(db);
	}

	private void deleteTables(SQLiteDatabase db) {
		Cursor cursor = db.query("sqlite_master", new String[] { "name" },
				"type = ? AND NOT name = ? AND NOT name = ?", new String[] {
						"table", "sqlite_sequence", "android_metadata", },
				null, null, null);
		while (cursor.moveToNext()) {
			Log.i("DELETED", "Table: " + cursor.getString(0));
			db.execSQL("DROP TABLE " + cursor.getString(0));
		}
		cursor.close();

	}

	public static void cleanAllEvents() {
		final SQLiteDatabase db = getDb();
		final Cursor c = db.rawQuery(
				"SELECT name FROM sqlite_master WHERE type = 'table' "
						+ "AND name LIKE('event%') ORDER BY name DESC", null);
		try {
			while (c.moveToNext()) {
				db.delete(c.getString(0), null, null);
			}
		} finally {
			c.close();
		}
	}

	public static void removeDeliveredEvents() {

		if (1 == 1)
			return;
		/*
		 * Cursor cursor = DB.getDb().query(Event.TABLE_NAME, new
		 * String[]{"max (timestamp)"}, "is_delivered = 1 AND type = ?", new
		 * String[]{String.valueOf(Const.BOX_BOLUS)}, null, null, null);
		 * Log.d("Cleaning delivered items","type "+Event.TABLE_NAME);
		 * if(cursor.moveToFirst()) { long maxtTimestamp = cursor.getLong(0);
		 * if(maxtTimestamp > 0) DB.getDb().delete("event",
		 * "is_delivered = 1 AND timestamp <= ? AND type <> ? ", new
		 * String[]{String
		 * .valueOf(maxtTimestamp),String.valueOf(Const.BOX_BOLUS)}); long
		 * bolusMaxTmp = System.currentTimeMillis() -
		 * Format.hoursToMilliseconds(
		 * Settings.get().getSyncSettings().getUserProfile
		 * ().getInt(UserValues.ai_time)) - Format.hoursToMilliseconds(3);
		 * DB.getDb().delete("event",
		 * "is_delivered = 1 AND type = ? AND timestamp < ?", new
		 * String[]{String.valueOf(Const.BOX_BOLUS),
		 * String.valueOf(bolusMaxTmp)}); }
		 * 
		 * cursor.close();
		 */
	}

	public static final int getInt(final String sql, int columnIndex) {
		final Cursor c = getDb().rawQuery(sql, null);
		try {
			if (!c.moveToFirst())
				return -1;
			return c.getInt(columnIndex);
		} finally {
			c.close();
		}
	}

	public static final String getString(final Cursor c, final String fieldName) {
		return c.getString(c.getColumnIndex(fieldName));
	}

	public static final int getInt(final Cursor c, final String fieldName) {
		return c.getInt(c.getColumnIndex(fieldName));
	}

	public static final long getLong(final Cursor c, final String fieldName) {
		return c.getLong(c.getColumnIndex(fieldName));
	}

	public static final float getFloat(final Cursor c, final String fieldName) {
		return c.getFloat(c.getColumnIndex(fieldName));
	}

	public static final Cursor  getAllCity() {
         return getDb().query(DB_TABLE_CITY, null, null, null, null, null, null);
    }
}
