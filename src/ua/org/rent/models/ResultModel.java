/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.models;

import android.content.ContentValues;
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
	private boolean resultOperation = true;
	private JSONObject jresult;
	private ArrayList<Apartment> apartamentList;

	public ArrayList<Apartment> getApartamentList() {
		return apartamentList;
	}

	private void prepareApartamentList(JSONArray apartamentList) {
		this.apartamentList = new ArrayList<Apartment>();
		DB.getDb().beginTransaction();
		DB.deleteAll(DB.DB_TABLE_APARTMENT);
		for (int i = 0; i < apartamentList.length(); i++) {
			try {
				JSONObject apartmentJ = (JSONObject) apartamentList.get(i);

				Apartment apartmentT = new Apartment();
				this.apartamentList.add(apartmentT);
				ContentValues apartment = new ContentValues();
				try {

					apartment.put(DB.TABLE_APARTMENT_STREET_ADDRESS, apartmentJ.getString(Consts.DEFAULT_JSON_TAG_APARTMENT_STREET_ADDRESS));
					apartment.put(DB.TABLE_APARTMENT_CITY_ID, apartmentJ.getString(Consts.DEFAULT_JSON_TAG_APARTMENT_CITY));
					apartment.put(DB.TABLE_APARTMENT_PRICE, apartmentJ.getInt(Consts.DEFAULT_JSON_TAG_APARTMENT_PRICE));
					apartment.put(DB.TABLE_APARTMENT_PHONE_NUM, apartmentJ.getString(Consts.DEFAULT_JSON_TAG_PHONE_NUM));

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
	}

	public JSONObject getJresult() {
		return jresult;
	}

	public ResultModel(SearchData searchData) {
		this.searchData = searchData;
	}

	public String getMessage() {
		return message;
	}

	synchronized public void preparationAndProcessing() {
		if (Http.hasConnection(RentAppState.getContext())) {
			getResponseFromServer();
			processingData();
		} else {
			message = RentAppState.getContext().getText(R.string.hasnt_connect).toString();
			resultOperation = false;
		}

	}

	public boolean isResultOperation() {
		return resultOperation;
	}

	private void getResponseFromServer() {
		response = Http.connect(Consts.HOST_API);
	}

	private void processingData() {
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

	public String getPhoneByPosition(int position) {
		Apartment apartment = apartamentList.get(position);
		return apartment.getPhoneNum().replaceAll("\\s+", "");
	}
}
