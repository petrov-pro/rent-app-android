package ua.org.rent.settings;

import android.app.Activity;
import android.app.Application;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import ua.org.rent.R;


/**
 * Created with IntelliJ IDEA. User: admin Date: 20.09.13 Time: 15:38 To change
 * this template use File | Settings | File Templates.
 */
public class Settings extends Application {
	
	private static Settings sInstance;
	private final SharedPreferences mPreferences;
	private final static String KEY_ACCESS_TOKEN = "access_token";
	public final static int DEFAULT_CITY_ID = 1;
	public static ArrayList<Integer> DEFAULT_DISTRICT_ID = new ArrayList<Integer>(Arrays.asList(0));
	public static String DEFAULT_CITY_NAME = "";
	public static Map<Integer, String> DEFAULT_DISTRICT_NAME = new HashMap<Integer, String>();
	public static Integer PRICE_FROM = 0;
	public static Integer PRICE_TO = 1000;
	public static Integer[] DEFAULT_QuantityRoom = {1, 2, 3, 4};
	public static Integer[] DEFAULT_QuantityBeds = {1, 2, 3, 4};
	
	private Settings(Context context) {
		mPreferences = context.getSharedPreferences(
				context.getPackageName(),
				Context.MODE_PRIVATE);
	}
	
	public static final Settings get() {
		if (sInstance == null) {
			sInstance = new Settings(RentAppState.getAppInstance());
		}
		
		return sInstance;
	}
	
	public static String getDEFAULT_CITY_NAME() {
		DEFAULT_CITY_NAME = RentAppState.getAppInstance().getText(R.string.default_city_name).toString();
		return DEFAULT_CITY_NAME;
	}
	
	public static Map<Integer, String> getDEFAULT_DISTRICT_NAME() {
		DEFAULT_DISTRICT_NAME.put(DEFAULT_DISTRICT_ID.get(0), RentAppState.getAppInstance().getText(R.string.all).toString());
		return DEFAULT_DISTRICT_NAME;
	}
	
	public synchronized String getAccessToken() {
		return mPreferences.getString(KEY_ACCESS_TOKEN, null);
	}
	
	public synchronized void setAccessToken(String accessToken) {
		SharedPreferences.Editor editor = getEditor();
		editor.putString(KEY_ACCESS_TOKEN, accessToken);
		editor.commit();
	}
	
	private SharedPreferences.Editor getEditor() {
		return mPreferences.edit();
	}
	/*             ContentValues Saving/Loading
	 public synchronized  void setHypoAlarmParams(String s, int i1, int i2)
	 {
	 getEditor().putString(KEY_HYPOALARM_MSG, s).commit();
	 getEditor().putInt(KEY_HYPOALARM_COLOR, i1).commit();
	 getEditor().putInt(KEY_HYPOALARM_ICO, i2).commit();

	 }

	 public synchronized ContentValues getHypoAlarmParams()
	 {
	 ContentValues cv=new ContentValues();
	 cv.put("msg",mPref.getString(KEY_HYPOALARM_MSG, ""));
	 cv.put("color",mPref.getInt(KEY_HYPOALARM_COLOR, 0xFFE63348));
	 cv.put("ico",mPref.getInt(KEY_HYPOALARM_ICO,0));
	 return cv;
	 }
	 */
}
