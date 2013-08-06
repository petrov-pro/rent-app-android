/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.controller;

/**
 *
 * @author petroff
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import ua.org.rent.library.Http;

public class ConnectReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		if (Http.hasConnection(context)) {
			
		} else {
			
		}

	}
}
