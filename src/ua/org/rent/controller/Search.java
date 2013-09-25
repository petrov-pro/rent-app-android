/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.controller;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import ua.org.rent.R;
import ua.org.rent.adapters.ListDistrictAdapter;
import ua.org.rent.helpers.SearchHelper;
import ua.org.rent.library.*;
import ua.org.rent.settings.Settings;
import ua.org.rent.utils.SearchData;

/**
 * 
 * @author petroff
 */
@TargetApi(3)
public class Search extends Activity {
	SearchData searchData;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		searchData = (SearchData) getLastNonConfigurationInstance();
		if (searchData == null) {
			searchData = new SearchData(Settings.DEFAULT_CITY_ID, Settings.DEFAULT_DISTRICT_ID);
		}

		// spinner city
		Cursor city = DB.getAllCity();
		startManagingCursor(city);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, city,
				new String[] { DB.TABLE_CITY_TITLE },
				new int[] { android.R.id.text1 });
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner SpinnerCity = (Spinner) findViewById(R.id.city_list);
		SpinnerCity.setAdapter(adapter);
		SpinnerCity.setSelection(SearchHelper.returnPosition(adapter, searchData.city_id));
		SpinnerCity.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView text = (TextView) view;
				text.getText();

			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		// listview district
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(Search.this);
		// builderSingle.setIcon(R.drawable.ic_launcher);
		builderSingle.setTitle(getText(R.string.select_district));

		final ListDistrictAdapter adapterDistrict = new ListDistrictAdapter(
				Search.this, android.R.layout.simple_list_item_1, city,
				new String[] { DB.TABLE_CITY_TITLE },
				new int[] { android.R.id.text1 });
		ListView lv = new ListView(this);
		lv.setAdapter(adapterDistrict);
		lv.setChoiceMode(lv.CHOICE_MODE_MULTIPLE);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View v, int position,
					long id) {
				// do something on click
				v.setSelected(true);
				adapterDistrict.notifyDataSetChanged();
			}
		});
		builderSingle.setView(lv);
		builderSingle.setPositiveButton(getText(R.string.ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builderSingle.show();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return searchData;
	}
}
