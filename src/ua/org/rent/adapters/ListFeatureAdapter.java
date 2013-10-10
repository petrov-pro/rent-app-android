package ua.org.rent.adapters;

import java.util.ArrayList;

import ua.org.rent.library.DB;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import ua.org.rent.R;
import ua.org.rent.entities.Feature;

public class ListFeatureAdapter extends SimpleCursorAdapter {

	Cursor c;
	Context context;
	int layout;
	ArrayList<Feature> features;
	int[] to;
	AssetManager am;

	public ListFeatureAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, ArrayList<Feature> features) {
		super(context, layout, c, from, to);
		this.c = c;
		this.context = context;
		this.layout = layout;
		this.features = features;
		this.to = to;
		am = context.getAssets();
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, layout, null);
		}
		if (c.moveToPosition(position)) {
			String title = c.getString(c.getColumnIndexOrThrow(DB.TABLE_FEATURE_TITLE));
			String ico = c.getString(c.getColumnIndexOrThrow(DB.TABLE_FEATURE_ICO));
			int feature_id = c.getInt(c.getColumnIndexOrThrow(DB.TABLE_FEATURE_ID));
			TextView feature_title = (TextView) convertView.findViewById(to[0]);
			ImageView feature_ico = (ImageView) convertView.findViewById(to[1]);
			feature_ico.setImageBitmap(getBitmapFromAsset("feature/" + ico + ".png"));
			LinearLayout feature_ll = (LinearLayout) convertView.findViewById(R.id.feature_ll);
			feature_title.setText(title);

			if (this.features.contains(new Feature(title, feature_id))) {
				feature_ll.setBackgroundColor(Color.GRAY);
			} else {
				feature_ll.setBackgroundColor(Color.BLACK);
			}
		}
		return convertView;
	}

	public Bitmap getBitmapFromAsset(String strName) {

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
