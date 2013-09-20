package ua.org.rent.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import ua.org.rent.settings.RentAppState;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

	public static String convertDataToString(long time){
		return SimpleDateFormat.getDateTimeInstance().format(new Date(time));
	}

	/**
	 * 
	 * @ param targetURL
	 * @ param urlParameters The urlParameters is a URL encoded string.
		<br/>
 		String urlParameters =
        "username=" + URLEncoder.encode("???", "UTF-8") +
        "&password=" + URLEncoder.encode("???", "UTF-8")
	 * @return
	 * @throws AppNetworkException 
	 */

	public static long getBeginningDay() {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.HOUR, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		cl.set(Calendar.MILLISECOND, 0);
		return cl.getTimeInMillis();
	}
	public static boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) RentAppState.getAppInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo currentNetwork=cm.getActiveNetworkInfo();
		if(currentNetwork==null) return false;
		return currentNetwork.isConnectedOrConnecting();
	}

    public static boolean isWiFi()
    {
        ConnectivityManager cm = (ConnectivityManager) RentAppState.getAppInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo currentNetwork=cm.getActiveNetworkInfo();
        if(currentNetwork==null) return false;
        Log.d("NETWORKING", "->" + currentNetwork.getTypeName());
        if (currentNetwork.getTypeName().equalsIgnoreCase("WIFI")) return true;
        return false;
    }
	public static String readFileAsString(String file, Context cntxt) {
		try {
			InputStream is = cntxt.getAssets().open(file);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			String text = new String(buffer);
			return text;
		} catch (IOException e) {
			// Should never happen!
			throw new RuntimeException(e);
		}
	}

	public static void appendLog(String text, Context cntxt){
		Date date = new Date(System.currentTimeMillis());

		final String LOGSTRING = date.toString()+": ---------------------------\n"+text+"\n=================================\n\n";


		FileOutputStream fOut;
		try {
			File dir = cntxt.getExternalCacheDir();
			File log = new File(dir, "log.txt");
			fOut = new FileOutputStream(log,false);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write(LOGSTRING);
			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int getTimeZoneShifting(){
		return TimeZone.getDefault().getRawOffset()/(1000*60*60);
	}
	
	public static long convertToLocalTimeZone(long pGmtTime){
		return pGmtTime + getTimeZoneShifting();
		
	}
	
	public static boolean hasImageCaptureBug() {
	    // list of known devices that have the bug
	    ArrayList<String> devices = new ArrayList<String>();
	    devices.add("android-devphone1/dream_devphone/dream");
	    devices.add("generic/sdk/generic");
	    devices.add("vodafone/vfpioneer/sapphire");
	    devices.add("tmobile/kila/dream");
	    devices.add("verizon/voles/sholes");
	    devices.add("google_ion/google_ion/sapphire");
	    return devices.contains(android.os.Build.BRAND + "/" + android.os.Build.PRODUCT + "/"
	            + android.os.Build.DEVICE);
	}
	
	public static boolean isIntentFromHistory(Intent intent) {

		return (intent.getFlags()& Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY)== Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY;
	}
}
