/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.controller;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import ua.org.rent.R;
import ua.org.rent.library.*;

/**
 * 
 * @author petroff
 */
public class Search extends Activity {

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		Cursor city = DB.getAllCity();
		startManagingCursor(city);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, city,
				new String[] { DB.TABLE_CITY_TITLE },
				new int[] { android.R.id.text1 });
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner SpinnerCity = (Spinner) findViewById(R.id.city_list);
		SpinnerCity.setAdapter(adapter);
	}
}
