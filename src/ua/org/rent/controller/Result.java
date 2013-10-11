/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.controller;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import ua.org.rent.R;
import ua.org.rent.entities.SearchData;
import ua.org.rent.models.ResultModel;
import ua.org.rent.utils.CProgressBar;
import ua.org.rent.utils.TaskPreperDate;

/**
 *
 * @author petroff
 */
public class Result extends Activity {

	private TabActivity ta;
	private ResultModel resultModel;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cap);
		ta = (TabActivity) Result.this.getParent();
		Intent intent = ta.getIntent();
		SearchData searchData = intent.getParcelableExtra(SearchData.class.getCanonicalName());
		resultModel = (ResultModel) getLastNonConfigurationInstance();
		if (resultModel == null) {
			resultModel = new ResultModel(searchData);
		}
		TaskPreperDate t = new TaskPreperDate(this);

		String statusTask = t.getStatus().toString();
		if (statusTask.equals("RUNNING")) {
			CProgressBar.finish();
			CProgressBar.onCreateDialog(1, this);
			CProgressBar.setProgress();
		} else {
			t.execute();
		}
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return resultModel;
	}

	//From TaskPreperDate
	public void onPreExecute() {
		CProgressBar.onCreateDialog(1, this);
		CProgressBar.setProgress();
	}

	public void doInBackground() {
		resultModel.preparationAndProcessing();
	}

	public void onPostExecute() {
		CProgressBar.finishProgress();
		if (resultModel.isResultOperation()) {
			setContentView(R.layout.cap);
		} else {
			setContentView(R.layout.result);
		}

	}
}
