package ua.org.rent.adapters;

import java.util.ArrayList;
import java.util.Arrays;

import ua.org.rent.library.DB;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ListDistrictAdapter extends SimpleCursorAdapter {

	Cursor c;
	Context context;
	int layout;
	ArrayList<Integer> district_id;
	
	public ListDistrictAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, ArrayList<Integer> district_id) {
		super(context, layout, c, from, to);
		this.c = c;
		this.context = context;
		this.layout = layout;
		this.district_id = district_id;

		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = View.inflate(context, layout,null);
		if (c.moveToPosition(position)) {
			TextView district = (TextView) convertView.findViewById(android.R.id.text1);
			district.setText(c.getString(c.getColumnIndexOrThrow(DB.TABLE_DISTRICT_TITLE)));
			int district_id = c.getInt(c.getColumnIndexOrThrow(DB.TABLE_DISTRICT_ID));


		}
		return convertView;
	}

}
