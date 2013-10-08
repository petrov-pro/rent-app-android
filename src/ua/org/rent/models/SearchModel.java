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
import ua.org.rent.entities.District;
import ua.org.rent.library.DB;
import ua.org.rent.settings.Consts;
import ua.org.rent.entities.SearchData;

/**
 *
 * @author petroff
 */
public class SearchModel {

	public SearchData searchData;
	Activity a;

	public SearchModel(Activity a) {

		searchData = new SearchData();
		this.a = a;
	}

	public String setTextOnButtonDistrict() {
		String res_str = "";
		if (searchData.districts.isEmpty()) {
			return a.getText(R.string.all).toString();
		} else {

			for (Object obj : searchData.districts.toArray()) {
				District district = (District) obj;
				res_str = res_str + " " + district.getTitle();
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
		return Consts.DEFAULT_CITY_ID;
	}

	public void setSelectionCity(long id) {
		searchData.city_id = (int) id;
		searchData.districts.clear();
		searchData.districts.add(Consts.getDefaultDistrict());

	}

	public void setSelectionDistrict(int id, int position, ListDistrictAdapter adapterDistrict) {
		Cursor district = (Cursor) adapterDistrict.getItem(position);
		String title = district.getString(district.getColumnIndexOrThrow(DB.TABLE_DISTRICT_TITLE));
		District choose_district = new District(title, id);
		if (searchData.districts.contains(choose_district)) {
			searchData.districts.remove(choose_district);
		} else {
			if (id == Consts.DEFAULT_DISTRICT_ID) {
				searchData.districts.clear();
			} else if (searchData.districts.contains(Consts.getDefaultDistrict())) {
				searchData.districts.remove(Consts.getDefaultDistrict());
			}
			searchData.districts.add(choose_district);
		}
	}

	public Cursor getDistrictById() {
		Cursor district = DB.getDistrictById(searchData.city_id);
		MatrixCursor extras = new MatrixCursor(new String[]{DB.TABLE_DISTRICT_ID, DB.TABLE_DISTRICT_TITLE});
		extras.addRow(new String[]{Consts.DEFAULT_DISTRICT_ID.toString(), a.getText(R.string.all).toString()});
		extras.addRow(new String[]{Consts.DEFAULT_DISTRICT_ALL_ID.toString(), a.getText(R.string.center).toString()});
		Cursor[] cursors = {extras, district};
		district = new MergeCursor(cursors);
		return district;
	}

	public Integer getPriceFrom() {
		return Consts.PRICE_FROM;
	}

	public Integer getPriceTo() {
		return Consts.PRICE_TO;
	}

	public Integer[] getQuantityRoom() {
		return Consts.DEFAULT_QuantityRoom;
	}

	public Integer getQuantityRoom(int pos) {
		return Consts.DEFAULT_QuantityRoom[pos];
	}

	public Integer[] getQuantityBeds() {
		return Consts.DEFAULT_QuantityBeds;
	}

	public Integer getQuantityBeds(int pos) {
		return Consts.DEFAULT_QuantityBeds[pos];
	}

	public Cursor getFeatureAll() {
		return DB.getFeatureAll();
	}

	public Cursor getAllCity() {
		return DB.getAllCity();
	}
}
