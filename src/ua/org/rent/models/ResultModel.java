/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.models;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.org.rent.R;
import ua.org.rent.entities.Apartment;
import ua.org.rent.entities.SearchData;
import ua.org.rent.library.DB;
import ua.org.rent.settings.Consts;
import ua.org.rent.settings.RentAppState;
import ua.org.rent.utils.Http;
import ua.org.rent.utils.TaskPreperDate;

/**
 *
 * @author petroff
 */
public class ResultModel {

	private SearchData searchData;
	private String message = "";
	private TaskPreperDate task;
	private String response;
	private boolean resultOperation;
	private JSONObject jresult;
	private ArrayList<Apartment> apartamentList;
	public Cursor features;
	public Cursor apartments;
	

	public JSONObject getJresult() {
		return jresult;
	}

	public ResultModel(SearchData searchData) {
		this.searchData = searchData;
	}

	public String getMessage() {
		return message;
	}

	public boolean isResultOperation() {
		return resultOperation;
	}

	public String getPhoneByPosition(int position) {
		String phoneNum = apartments.getString(apartments.getColumnIndex(DB.TABLE_APARTMENT_PHONE_NUM));
		return phoneNum.replaceAll("\\s+", "");
	}

	synchronized public void preparationAndProcessing() {
		if (Http.hasConnection(RentAppState.getContext())) {
			processingData();
		} else {
			message = RentAppState.getContext().getText(R.string.hasnt_connect).toString();
			resultOperation = false;
		}

	}

	private void processingData() {
		response = Http.connect(Consts.HOST_API);
		if (response == null || response.isEmpty()) {
			message = RentAppState.getContext().getText(R.string.server_problem__connect).toString();
			resultOperation = false;
		} else {
			try {
				jresult = new JSONObject(response);
				//parse structure

				try {
					JSONArray apartamentList = jresult.getJSONArray(Consts.DEFAULT_JSON_TAG_APARTMENT_LIST);
					prepareApartamentList(apartamentList);
				} catch (JSONException e) {
					message = RentAppState.getContext().getText(R.string.json_bad_strucure).toString();
					resultOperation = false;
				}

			} catch (JSONException e) {
				message = RentAppState.getContext().getText(R.string.json_parser_problem).toString();
				resultOperation = false;
			}

		}
	}
	
	private void prepareApartamentList(JSONArray apartamentList) {
		this.apartamentList = new ArrayList<Apartment>();
		DB.getDb().beginTransaction();
		DB.deleteAll(DB.DB_TABLE_APARTMENT);
		DB.deleteAll(DB.DB_TABLE_FEATURES_APARTMENTS);
		int apartment_id;
		for (int i = 0; i < apartamentList.length(); i++) {
			try {
				JSONObject apartmentJ = (JSONObject) apartamentList.get(i);

				Apartment apartmentT = new Apartment();
				this.apartamentList.add(apartmentT);
				ContentValues apartment = new ContentValues();
				try {
					apartment_id = apartmentJ.getInt(Consts.DEFAULT_JSON_TAG_APARTMENT_ID);
					apartment.put(DB.TABLE_APARTMENT_ID, apartment_id);
					apartment.put(DB.TABLE_APARTMENT_CITY_ID, apartmentJ.getInt(Consts.DEFAULT_JSON_TAG_CITY_ID));
					apartment.put(DB.TABLE_APARTMENT_DISTRICT_ID, apartmentJ.getInt(Consts.DEFAULT_JSON_TAG_DISTRICT_ID));
					apartment.put(DB.TABLE_APARTMENT_EXPIREDATE, apartmentJ.getString(Consts.DEFAULT_JSON_TAG_EXPIREDATE));
					apartment.put(DB.TABLE_APARTMENT_BEDS, apartmentJ.getInt(Consts.DEFAULT_JSON_TAG_BEDS));
					apartment.put(DB.TABLE_APARTMENT_ROOMS, apartmentJ.getInt(Consts.DEFAULT_JSON_TAG_ROOMS));
					apartment.put(DB.TABLE_APARTMENT_STREET_ADDRESS, apartmentJ.getString(Consts.DEFAULT_JSON_TAG_STREET_ADDRESS));
					apartment.put(DB.TABLE_APARTMENT_HOUSE_NUM, apartmentJ.getString(Consts.DEFAULT_JSON_TAG_HOUSE_NUM));
					apartment.put(DB.TABLE_APARTMENT_RATING, apartmentJ.getInt(Consts.DEFAULT_JSON_TAG_RATING));
					apartment.put(DB.TABLE_APARTMENT_PHONE_NUM, apartmentJ.getString(Consts.DEFAULT_JSON_TAG_PHONE_NUM));
					apartment.put(DB.TABLE_APARTMENT_CONTACT_NAME, apartmentJ.getString(Consts.DEFAULT_JSON_TAG_CONTACT_NAME));
					apartment.put(DB.TABLE_APARTMENT_PRICE, apartmentJ.getInt(Consts.DEFAULT_JSON_TAG_PRICE));
					JSONArray fl = apartmentJ.getJSONArray(Consts.DEFAULT_JSON_TAG_APARTMENT_FEATURES_LIST);
					for (int ii = 0; ii < fl.length(); ii++) {
						int feature_id = (Integer) fl.get(ii);
						ContentValues feature = new ContentValues();
						feature.put(DB.TABLE_FEATURES_APARTMENTS_APARTMENT_ID, apartment_id);
						feature.put(DB.TABLE_FEATURES_APARTMENTS_FEATURE_ID, feature_id);
						DB.getDb().insert(DB.DB_TABLE_FEATURES_APARTMENTS, null, feature);
					}

				} catch (JSONException e) {
					message = RentAppState.getContext().getText(R.string.json_bad_strucure3).toString();
					resultOperation = false;
				}

				DB.getDb().insert(DB.DB_TABLE_APARTMENT, null, apartment);

			} catch (JSONException e) {
				message = RentAppState.getContext().getText(R.string.json_bad_strucure2).toString();
				resultOperation = false;
			}
		}
		DB.getDb().setTransactionSuccessful();
		DB.getDb().endTransaction();
		resultOperation = true;
	}

	public void getApartmentAndFeature() {
		features = DB.getFeatureAll();
		apartments = DB.getApartmentsAll();
	}
}
