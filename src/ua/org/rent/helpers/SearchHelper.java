package ua.org.rent.helpers;

import ua.org.rent.settings.Settings;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

public class SearchHelper {

	static public int returnPosition(SimpleCursorAdapter sAdapter, int id) {
		int count = sAdapter.getCount();
		for (int i = 0; i <= count; i++) {
			Cursor o = (Cursor)sAdapter.getItem(i);
			if(o.getColumnIndexOrThrow("_id") == id){
				return i;
			}
		}
		return Settings.DEFAULT_CITY_ID;
	}

}
