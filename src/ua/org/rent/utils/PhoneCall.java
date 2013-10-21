/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import ua.org.rent.settings.RentAppState;

/**
 *
 * @author petroff
 */
public class PhoneCall {

	public static void phoneCall(String tel) {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		callIntent.setData(Uri.parse("tel:" + tel));
		RentAppState.getAppInstance().startActivity(callIntent);
	}
}
