package ua.org.rent.settings;

import android.app.Application;
import ua.org.rent.R;
import ua.org.rent.entities.District;
import ua.org.rent.entities.Feature;
import ua.org.rent.library.DB;

/**
 * Created with IntelliJ IDEA. User: admin Date: 20.09.13 Time: 05:18 To change
 * this template use File | Settings | File Templates.
 */
public class Consts extends Application {
	//Stub urls

	public static final String URL_LOCAL_LOG = "http://192.168.0.102";
	//valid url
	public static final String HOST_API = "http://pos.bth.in.ua/rent/";
	public final static Integer DEFAULT_CITY_ID = 1;
	public final static Integer DEFAULT_DISTRICT_ALL_ID = 9999;
	public static Integer PRICE_FROM = 0;
	public static Integer PRICE_TO = 1000;
	public static Integer[] DEFAULT_QuantityRoom = {1, 2, 3, 4};
	public static Integer DEFAULT_ROOM_ID = DEFAULT_QuantityRoom[0];
	public static Integer[] DEFAULT_QuantityBeds = {1, 2, 3, 4};
	public static Integer DEFAULT_BEDS_ID = DEFAULT_QuantityBeds[0];
	public static String DEFAULT_DISTRICT_NAME = "";
	public static Integer DEFAULT_DISTRICT_ID = 0;
	public static Integer DEFAULT_FEATURE_ID = 0;

	public static  String getDefaultCityName() {
		return DB.getCityById(DEFAULT_CITY_ID);
	}

	public static  String getDefaultDistrictName() {
		return RentAppState.getAppInstance().getApplicationContext().getText(R.string.all).toString();
	}

	public static  District getDefaultDistrict() {
		return new District(getDefaultDistrictName(), DEFAULT_DISTRICT_ID);
	}

	public static  String getDefaultFeatureName() {
		return RentAppState.getAppInstance().getApplicationContext().getText(R.string.all).toString();
	}

	public static  Feature getDefaultFeature() {
		return new Feature(getDefaultFeatureName(), DEFAULT_FEATURE_ID);
	}
}
