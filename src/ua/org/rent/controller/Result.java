/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.controller;

import android.app.Activity;
import android.app.TabActivity;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import ua.org.rent.R;
import ua.org.rent.adapters.ListApartmentAdapter;
import ua.org.rent.entities.Apartment;
import ua.org.rent.entities.SearchData;
import ua.org.rent.models.ResultModel;
import ua.org.rent.utils.CProgressBar;
import ua.org.rent.utils.PhoneCall;
import ua.org.rent.utils.TaskPreperDate;

/**
 *
 * @author petroff
 */
public class Result extends Activity implements OnClickListener {

	private TabActivity ta;
	private ResultModel resultModel;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
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
		if (!resultModel.isResultOperation()) {
			setContentView(R.layout.result_error);
			TextView txtError = (TextView) findViewById(R.id.result_message);
			txtError.setText(resultModel.getMessage());
		} else {
			ListApartmentAdapter t = new ListApartmentAdapter(this, resultModel.getApartamentList());
			ListView result_list = (ListView) findViewById(R.id.result_list);
			result_list.setAdapter(t);
			result_list.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> a, View view, int position, long id) {
					Apartment apartment = (Apartment) a.getItemAtPosition(position);

				}
			});
		}

	}

	@Override
	public void onClick(View v) {
		//click on item in list apartment, call or mini ico
		Integer position = (Integer) v.getTag(R.id.apartment_position);
		if (v.getTag().equals("call")) {
			String phoneNumber = resultModel.getPhoneByPosition(position);
			PhoneCall.phoneCall(phoneNumber);
		} else {
		}
	}
}
