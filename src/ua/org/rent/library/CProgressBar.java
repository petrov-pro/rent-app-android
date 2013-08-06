/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package currency.online;

import android.app.ProgressDialog;
import android.app.Dialog;
import android.app.Activity;

/**
 *
 * @author petroff
 */
public final class CProgressBar {

	static ProgressDialog mProgressDialog;
	static final int IDD__HORIZONTAL_PROGRESS = 0;
	static final int IDD_WHEEL_PROGRESS = 1;
	private static final String TAG = "MyTask";
	private static int dMax = 10000;

	static public Dialog onCreateDialog(int id, Activity activity) {
		switch (id) {
			case IDD__HORIZONTAL_PROGRESS:
				mProgressDialog = new ProgressDialog(
						activity);
				mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // устанавливаем стиль
				mProgressDialog.setMessage("Load. Wait...");  // задаем текст
				mProgressDialog.setMax(dMax);
				mProgressDialog.setCancelable(false);
				return mProgressDialog;

			case IDD_WHEEL_PROGRESS:
				mProgressDialog = new ProgressDialog(
						activity);
				mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				mProgressDialog.setMessage("Load. Wait...");
				mProgressDialog.setCancelable(false);
				return mProgressDialog;

			default:
				return null;
		}
	}

	static public void setProgress(int i) {
		try {
			if (!mProgressDialog.isShowing()) {
				mProgressDialog.show();
			}
			mProgressDialog.setProgress(i);
		} catch (Exception e) {
			/* cannot happen */
		}
	}

	static public void setProgress() {
		try {
			if (!mProgressDialog.isShowing()) {
				mProgressDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static public void finishProgress() {
		try {
			mProgressDialog.setProgress(dMax);
			mProgressDialog.cancel();
			mProgressDialog.dismiss();
		} catch (Exception e) {
			/* cannot happen */
		}
	}

	static public void finish() {
		try {
			if (mProgressDialog != null) {
				mProgressDialog.dismiss();
			}
		} catch (Exception e) {
		}
	}
}
