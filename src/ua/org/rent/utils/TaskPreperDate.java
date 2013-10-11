/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.utils;

import android.os.AsyncTask;
import ua.org.rent.controller.Result;

/**
 *
 * @author petroff
 */
public class TaskPreperDate extends AsyncTask<Void, Void, Void> {

	Result result;

	public TaskPreperDate(Result result) {
		super();
		this.result = result;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		result.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		result.doInBackground();
		return null;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		this.result.onPostExecute();
	}
}
