package ua.org.rent.controller;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import ua.org.rent.R;

public class Tab extends TabActivity {

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// получаем TabHost
		TabHost tabHost = getTabHost();

		// инициализация была выполнена в getTabHost
		// метод setup вызывать не нужно

		TabHost.TabSpec tabSpec;

		tabSpec = tabHost.newTabSpec("search");
		tabSpec.setIndicator(getString(R.string.search), getResources().getDrawable(R.drawable.tab_icon_search));
		tabSpec.setContent(new Intent(this, Search.class));
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec("result");
		tabSpec.setIndicator(getString(R.string.result), getResources().getDrawable(R.drawable.tab_icon_result));
		tabSpec.setContent(new Intent(this, Result.class));
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec("history");
		tabSpec.setIndicator(getString(R.string.history), getResources().getDrawable(R.drawable.tab_icon_history));
		tabSpec.setContent(new Intent(this, History.class));
		tabHost.addTab(tabSpec);
	}
}
