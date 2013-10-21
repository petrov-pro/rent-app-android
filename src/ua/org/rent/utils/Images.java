/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.utils;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;
import ua.org.rent.settings.RentAppState;

/**
 *
 * @author petroff
 */
public class Images {

	public static Bitmap getBitmapFromAsset(String strName) {
		AssetManager am = RentAppState.getContext().getAssets();
		InputStream istr;
		Bitmap bitmap = null;
		try {
			istr = am.open(strName);
			bitmap = BitmapFactory.decodeStream(istr);
		} catch (IOException e) {
			return null;
		}

		return bitmap;
	}
}
