/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.models;

import android.app.Activity;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import java.util.ArrayList;
import ua.org.rent.R;
import ua.org.rent.adapters.ListDistrictAdapter;
import ua.org.rent.library.DB;
import ua.org.rent.settings.Settings;
import ua.org.rent.utils.SearchData;

/**
 *
 * @author petroff
 */
public class SearchModel {

	public SearchData searchData;
	Activity a;

	public SearchModel(Activity a) {
		searchData = new SearchData(Settings.DEFAULT_CITY_ID, Settings.DEFAULT_DISTRICT_ID, Settings.getDEFAULT_CITY_NAME(), Settings.getDEFAULT_DISTRICT_NAME());
		this.a = a;
	}

	public String setTextOnButtonDistrict() {
		String res_str = "";
		if (searchData.district_id.size() == 0) {
			return a.getText(R.string.all).toString();
		} else {
			
			for ( String value : searchData.district_name.values()){
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
	}

	public void setSelectionDistrict(int id, int position, ListDistrictAdapter adapterDistrict) {
		if (searchData.district_id.contains(id)) {
			searchData.district_id.remove(position);
			searchData.district_name.remove(position);
		} else {
			searchData.district_id.add(id);
			Cursor district = (Cursor)adapterDistrict.getItem(position);
			searchData.district_name.put(id, district.getString(district.getColumnIndexOrThrow(DB.TABLE_DISTRICT_TITLE)));
		}
	}
}
