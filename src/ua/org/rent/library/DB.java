package ua.org.rent.library;

import android.content.ContentValues;
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
	public static final String TABLE_CITY_ID = "_id";
	//district table
	public static final String DB_TABLE_DISTRICT = "districts";
	public static final String TABLE_DISTRICT_TITLE = "title";
	public static final String TABLE_DISTRICT_ID = "_id";
	//feature table
	public static final String DB_TABLE_FEATURE = "features_list";
	public static final String TABLE_FEATURE_TITLE = "title";
	public static final String TABLE_FEATURE_ID = "_id";
	public static final String TABLE_FEATURE_ICO = "ico";
	//aprtment
	public static final String DB_TABLE_APARTMENT = "apartments";
	public static final String TABLE_APARTMENT_ID = "_id";
	public static final String TABLE_APARTMENT_CITY_ID = "city_id";
	public static final String TABLE_APARTMENT_DISTRICT_ID = "district_id";
	public static final String TABLE_APARTMENT_EXPIREDATE = "expiredate";
	public static final String TABLE_APARTMENT_TITLE = "title";
	public static final String TABLE_APARTMENT_BEDS = "beds";
	public static final String TABLE_APARTMENT_ROOMS = "rooms";
	public static final String TABLE_APARTMENT_STREET_ADDRESS = "street_address";
	public static final String TABLE_APARTMENT_HOUSE_NUM = "house_num";
	public static final String TABLE_APARTMENT_APT_NUM = "apt_num";
	public static final String TABLE_APARTMENT_RATING = "rating";
	public static final String TABLE_APARTMENT_PHONE_NUM = "phone_num";
	public static final String TABLE_APARTMENT_CONTACT_NAME = "contact_name";
	public static final String TABLE_APARTMENT_PRICE = "price";
	public static final String TABLE_APARTMENT_DESCRIPTION = "description";
	public static final String TABLE_APARTMENT_CITY_NAME = "city_name";
	public static final String TABLE_APARTMENT_FL = "fl";
	public static final String TABLE_APARTMENT_HASH = "hash";
	//features_apartments
	public static final String DB_TABLE_FEATURES_APARTMENTS = "features_apartments";
	public static final String TABLE_FEATURES_APARTMENTS_APARTMENT_ID = "apartment_id";
	public static final String TABLE_FEATURES_APARTMENTS_FEATURE_ID = "feature_id";
	//call history
	public static final String DB_TABLE_CALL_HISTORY = "call_history";
	public static final String TABLE_CALL_HISTORY_APARTMENT_ID = "apartment_id";
	public static final String TABLE_CALL_HISTORY_IS_POSITIVE = "is_positive";
	public static final String TABLE_CALL_HISTORY_ID = "_id";
	public static final String TABLE_CALL_HISTORY_ID_ALIAS = "history_id";
	private static final int DATABASE_VERSION = 24;
	private volatile static DB sInstance;
	private final Context mContext;
	String[] items;

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
				R.array.TABLES);
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
		Cursor cursor = db.query("sqlite_master", new String[]{"name"},
				"type = ? AND NOT name = ? AND NOT name = ?", new String[]{
			"table", "sqlite_sequence", "android_metadata",},
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


	public static final int getInt(final String sql, int columnIndex) {
		final Cursor c = getDb().rawQuery(sql, null);
		try {
			if (!c.moveToFirst()) {
				return -1;
			}
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

	public static final Cursor getAllCity() {
		return getDb().query(DB_TABLE_CITY, null, null, null, null, null, null);
	}

	public static final Cursor getDistrictById(Integer city_id) {
		return getDb().query(DB_TABLE_DISTRICT, null, "city_id = ?", new String[]{city_id.toString()}, null, null, null);
	}

	public static final Cursor getFeatureAll() {
		return getDb().query(DB_TABLE_FEATURE, null, null, null, null, null, null);
	}

	public static final String getCityById(Integer city_id) {
		Cursor c = getDb().query(DB_TABLE_CITY, null, "_id = ?", new String[]{city_id.toString()}, null, null, null);
		try {
			c.moveToFirst();
			return c.getString(c.getColumnIndexOrThrow(DB.TABLE_CITY_TITLE));
		} finally {
			c.close();
		}
	}

	public static void deleteAll(String table) {
		getDb().delete(table, null, null);
	}

	public static Cursor getApartmentsAll() {
		String query = "SELECT apartments.*, cities.title as " + TABLE_APARTMENT_CITY_NAME + ", call_history._id as history_id, call_history.is_positive, "
				+ "(SELECT GROUP_CONCAT(fl.ico) FROM features_apartments as fa, features_list as fl WHERE fa.apartment_id = apartments._id and fa.feature_id = fl._id) as " + DB.TABLE_APARTMENT_FL + "  "
				+ "FROM apartments, cities, districts "
				+ "LEFT JOIN call_history ON apartments._id = call_history.apartment_id "
				+ "WHERE apartments.city_id = cities._id and  apartments.district_id = districts._id ORDER BY apartments._id;";
		return getDb().rawQuery(query, null);
	}

	public static void deleteAllApartment(String hash) {
		getDb().delete(DB_TABLE_FEATURES_APARTMENTS, DB_TABLE_FEATURES_APARTMENTS + "._id NOT IN (SELECT _id FROM " + DB_TABLE_CALL_HISTORY + " as ch) AND "
				+ DB_TABLE_FEATURES_APARTMENTS + "._id != ? ", new String[]{hash});
		getDb().delete(DB_TABLE_APARTMENT, DB_TABLE_APARTMENT + "._id NOT IN (SELECT _id FROM " + DB_TABLE_CALL_HISTORY + " as ch) AND "
				+ DB_TABLE_APARTMENT + "._id != ? ", new String[]{hash});
	}
}
//sqlite3 /data/data/ua.org.rent/databases/rentapp.sqlite
//SELECT apartments.*, cities.title as title, call_history._id as history_id, call_history.is_positive FROM apartments, cities, districts LEFT JOIN call_history ON apartments._id = call_history.apartment_id WHERE apartments.city_id = cities._id and  apartments.district_id = districts._id ORDER BY apartments._id;
//SELECT * FROM apartments;