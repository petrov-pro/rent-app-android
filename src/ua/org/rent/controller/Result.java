/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import ua.org.rent.R;
import ua.org.rent.adapters.ListApartmentAdapter;
import ua.org.rent.entities.SearchData;
import ua.org.rent.models.ResultModel;
import ua.org.rent.settings.Consts;
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
	private ListView result_list;
	private ListApartmentAdapter t;

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
			resultModel.getApartmentAndFeature();
			startManagingCursor(resultModel.features);
			startManagingCursor(resultModel.apartments);
			t = new ListApartmentAdapter(this, R.layout.apartament_item, resultModel.apartments, new String[]{}, new int[]{}, resultModel);
			result_list = (ListView) findViewById(R.id.result_list);
			result_list.setAdapter(t);
			result_list.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> a, View view, int position, long id) {
				}
			});
		}

	}

	@Override
	public void onClick(View v) {
		//click on item in list apartment, call or mini ico
		Integer position = (Integer) v.getTag(R.id.apartment_position);
		if (v.getTag().equals(Consts.TAG_CALL)) {
			String phoneNumber = resultModel.getPhoneByPositionAndSetInPref(position);
			PhoneCall.phoneCall(phoneNumber);
		} else if (v.getTag().equals(Consts.TAG_AT)) {
			int answerType = (Integer) v.getTag(R.id.answer_type);
			int isHistory = (Integer) v.getTag(R.id.is_history);
			resultModel.updateCallHistory(position, answerType, isHistory);
			getSetCoordinateLV();
			t.apartments.requery();
			t.notifyDataSetChanged();
			moveScrollLV();
		} else if (v.getTag().equals(Consts.TAG_MINI)) {
			loadPhoto(t.imageUri);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		resultModel.setApartmentIdFromPref();
		if (t != null) {
			result_list.setSelectionFromTop(resultModel.getIndexM(), resultModel.getTopM());
			t.notifyDataSetChanged();
			moveScrollLV();
		}
	}

	public void getSetCoordinateLV() {
		// save index and top position
		int index = result_list.getFirstVisiblePosition();
		View v = result_list.getChildAt(0);
		int top = (v == null) ? 0 : v.getTop();
		resultModel.setIndexM(index);
		resultModel.setTopM(top);
	}

	private void moveScrollLV() {
		result_list.setSelectionFromTop(resultModel.getIndexM(), resultModel.getTopM());
	}

	private void loadPhoto(String url) {

		AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

		View layout = inflater.inflate(R.layout.custom_fullimage_dialog,
				(ViewGroup) findViewById(R.id.layout_root));
		ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
		ImageLoader.getInstance().displayImage(url, image);
		imageDialog.setView(layout);
		imageDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});


		imageDialog.create();
		imageDialog.show();
	}
}
