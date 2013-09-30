/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import ua.org.rent.R;
import ua.org.rent.adapters.ListDistrictAdapter;
import ua.org.rent.library.*;
import ua.org.rent.models.SearchModel;
import ua.org.rent.settings.Settings;
import ua.org.rent.widgets.RangeSeekBar;
import ua.org.rent.widgets.RangeSeekBar.OnRangeSeekBarChangeListener;

/**
 *
 * @author petroff
 */
@TargetApi(3)
public class Search extends Activity {

	SearchModel searchModel;
	Button btDistrict;
	private boolean firstInit = true;
	private TextView tRSfrom;
	private TextView tRSto;

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
		//create rangeseek widget
		createRangeSeek();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return searchModel;
	}

	private void createRangeSeek() {
		// create RangeSeekBar as Integer range between 20 and 75
		tRSfrom = (TextView) findViewById(R.id.tRSfrom);
		tRSto = (TextView) findViewById(R.id.tRSto);
		tRSfrom.setText(Settings.PRICE_FROM.toString());
		tRSto.setText(Settings.PRICE_TO.toString());
		RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(Settings.PRICE_FROM, Settings.PRICE_TO, this);
		seekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
				// handle changed range values
				tRSfrom.setText(minValue.toString());
				tRSto.setText(maxValue.toString());
			}
		});

		LinearLayout layout = (LinearLayout) findViewById(R.id.lRS);
		layout.addView(seekBar);
	}

	public void showListDistrict(View v) {
		// listview district
		Cursor district = searchModel.getDistrictById();
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

	public void showQuantityRoom(View v) {
		Integer[] data = {1, 2, 3, 4};
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle(R.string.quantity_room);
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
				android.R.layout.select_dialog_singlechoice, data);
		adb.setSingleChoiceItems(adapter, -1, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
	}
}