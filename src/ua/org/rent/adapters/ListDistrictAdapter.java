package ua.org.rent.adapters;

import ua.org.rent.library.DB;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ListDistrictAdapter extends SimpleCursorAdapter {

	Cursor c;
	Context context;
	int layout;

	public ListDistrictAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.c = c;
		this.context = context;
		this.layout = layout;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = View.inflate(context, layout,null);
		if (c.moveToPosition(position)) {
			TextView city = (TextView) convertView.findViewById(android.R.id.text1);
			city.setText(c.getString(c.getColumnIndexOrThrow(DB.TABLE_CITY_TITLE)));

		}
		return convertView;
	}

}
