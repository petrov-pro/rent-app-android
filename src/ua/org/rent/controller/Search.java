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
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import ua.org.rent.R;
import ua.org.rent.adapters.ListDistrictAdapter;
import ua.org.rent.library.*;
import ua.org.rent.models.SearchModel;
import ua.org.rent.settings.Settings;

/**
 *
 * @author petroff
 */
@TargetApi(3)
public class Search extends Activity {
	
	SearchModel searchModel;
	Button btDistrict;
	private boolean firstInit = true;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		searchModel = (SearchModel) getLastNonConfigurationInstance();
		if (searchModel == null) {
			searchModel = new SearchModel(this);
		}
		btDistrict = (Button) findViewById(R.id.btD);
		btDistrict.setText(searchModel.setTextOnButtonDistrict());
		// spinner city
		Cursor city = DB.getAllCity();
		startManagingCursor(city);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, city,
				new String[]{DB.TABLE_CITY_TITLE},
				new int[]{android.R.id.text1});
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner SpinnerCity = (Spinner) findViewById(R.id.city_list);
		SpinnerCity.setAdapter(adapter);
		
		SpinnerCity.setSelection(searchModel.returnPosition(adapter,
				searchModel.searchData.city_id));
		SpinnerCity.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (!firstInit) {
					searchModel.setSelectionCity(id);
					btDistrict.setText(searchModel.setTextOnButtonDistrict());
				} else {
					firstInit = false;
				}
			}
			
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		return searchModel;
	}
	
	public void showListDistrict(View v) {
		// listview district
		Cursor district = DB.getDistrictById(searchModel.searchData.city_id);
		startManagingCursor(district);
		final ListDistrictAdapter adapterDistrict = new ListDistrictAdapter(
				Search.this, android.R.layout.simple_list_item_1, district,
				new String[]{DB.TABLE_DISTRICT_TITLE},
				new int[]{android.R.id.text1}, searchModel.searchData.district_id);
		ListView lv = new ListView(this);
		lv.setAdapter(adapterDistrict);
		lv.setChoiceMode(lv.CHOICE_MODE_MULTIPLE);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View v, int position,
					long id) {
				// do something on click
				v.setSelected(true);
				searchModel.setSelectionDistrict((int) id, position, adapterDistrict);
				adapterDistrict.notifyDataSetChanged();
			}
		});
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(Search.this);
		builderSingle.setTitle(getText(R.string.select_district));
		builderSingle.setView(lv);
		builderSingle.setPositiveButton(getText(R.string.ok),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				btDistrict.setText(searchModel.setTextOnButtonDistrict());
				dialog.dismiss();
			}
		});
		
		builderSingle.show();
	}
}
