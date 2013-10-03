/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.models;

import android.app.Activity;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.widget.SimpleCursorAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import ua.org.rent.R;
import ua.org.rent.adapters.ListDistrictAdapter;
import ua.org.rent.library.DB;
import ua.org.rent.settings.Settings;
import static ua.org.rent.settings.Settings.DEFAULT_DISTRICT_ID;
import ua.org.rent.utils.SearchData;

/**
 *
 * @author petroff
 */
public class SearchModel {

	public SearchData searchData;
	Activity a;

	public SearchModel(Activity a) {
		searchData = new SearchData(Settings.DEFAULT_CITY_ID, (ArrayList<Integer>)Settings.DEFAULT_DISTRICT_ID.clone() , Settings.getDEFAULT_CITY_NAME(), Settings.getDEFAULT_DISTRICT_NAME(),
				Settings.DEFAULT_QuantityRoom[0], Settings.DEFAULT_QuantityBeds[0], Settings.PRICE_FROM, Settings.PRICE_TO);
		this.a = a;
	}

	public String setTextOnButtonDistrict() {
		String res_str = "";
		if (searchData.district_id.isEmpty()) {
			return a.getText(R.string.all).toString();
		} else {

			for (String value : searchData.district_name.values()) {
				res_str = res_str + " " + value;
			}
			return res_str;
		}
	}

	public int returnPosition(SimpleCursorAdapter sAdapter, int id) {
		int count = sAdapter.getCount();
		for (int i = 0; i <= count; i++) {
			Cursor o = (Cursor) sAdapter.getItem(i);
			if (o.getInt(o.getColumnIndexOrThrow(DB.TABLE_CITY_ID)) == id) {
				return i;
			}
		}
		return Settings.DEFAULT_CITY_ID;
	}

	public void setSelectionCity(long id) {
		searchData.city_id = (int) id;
		searchData.district_id.clear();
		searchData.district_name.clear();
		searchData.district_id.add(Settings.DEFAULT_DISTRICT_ID.get(0));
		searchData.district_name.put(DEFAULT_DISTRICT_ID.get(0), a.getText(R.string.all).toString());
		
	}

	public void setSelectionDistrict(int id, int position, ListDistrictAdapter adapterDistrict) {
		if (searchData.district_id.contains(id)) {
			searchData.district_id.remove(searchData.district_id.indexOf(id));
			searchData.district_name.remove(id);
		} else {
			if (id == Settings.DEFAULT_DISTRICT_ID.get(0)) {
				searchData.district_id.clear();
				searchData.district_name.clear();
			} else if (searchData.district_id.contains(Settings.DEFAULT_DISTRICT_ID.get(0))) {
				searchData.district_id.remove(searchData.district_id.indexOf(Settings.DEFAULT_DISTRICT_ID.get(0)));
				searchData.district_name.remove(Settings.DEFAULT_DISTRICT_ID.get(0));
			}
			searchData.district_id.add(id);
			Cursor district = (Cursor) adapterDistrict.getItem(position);
			searchData.district_name.put(id, district.getString(district.getColumnIndexOrThrow(DB.TABLE_DISTRICT_TITLE)));

		}
	}

	public Cursor getDistrictById() {
		Cursor district = DB.getDistrictById(searchData.city_id);
		MatrixCursor extras = new MatrixCursor(new String[]{"_id", "title"});
		extras.addRow(new String[]{Settings.DEFAULT_DISTRICT_ID.get(0).toString(), a.getText(R.string.all).toString()});
		extras.addRow(new String[]{"9999", a.getText(R.string.center).toString()});
		Cursor[] cursors = {extras, district};
		district = new MergeCursor(cursors);
		return district;
	}

	public Integer getPriceFrom() {
		return Settings.PRICE_FROM;
	}

	public Integer getPriceTo() {
		return Settings.PRICE_TO;
	}

	public Integer[] getQuantityRoom() {
		return Settings.DEFAULT_QuantityRoom;
	}

	public Integer getQuantityRoom(int pos) {
		return Settings.DEFAULT_QuantityRoom[pos];
	}

	public Integer[] getQuantityBeds() {
		return Settings.DEFAULT_QuantityBeds;
	}

	public Integer getQuantityBeds(int pos) {
		return Settings.DEFAULT_QuantityBeds[pos];
	}
}
