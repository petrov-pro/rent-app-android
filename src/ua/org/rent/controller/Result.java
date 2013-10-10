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

/**
 *
 * @author petroff
 */
public class Result extends Activity {

	private TabActivity ta;

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
	}
}
