package ua.org.rent.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import ua.org.rent.R;
import ua.org.rent.controller.Result;
import ua.org.rent.entities.Apartment;
import ua.org.rent.library.DB;
import ua.org.rent.utils.Images;
import ua.org.rent.widgets.FlowLayout;

public class ListApartmentAdapter extends ArrayAdapter<Apartment> {

	private Context context;
	private ArrayList<Apartment> jresult;
	private Cursor feature;
	private Result a;
	private String imageUri = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9_62PtouMa8v1rNw-qa1C8MDFeRiic_mZWORsPqP6n2fipoEijg";
	
	public ListApartmentAdapter(Context context, ArrayList<Apartment> jresult) {
		super(context, R.layout.apartament_item, jresult);

		this.context = context;
		this.jresult = jresult;
		this.feature = DB.getFeatureAll();
		a = (Result) context;
		a.startManagingCursor(feature);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.apartament_item, null);
		}
		Apartment apartment = jresult.get(position);
		FlowLayout ll = (FlowLayout) convertView.findViewById(R.id.apartment_feature);
		ll.removeAllViews();
		TextView apartment_txt = (TextView) convertView.findViewById(R.id.address);
		TextView apartment_price = (TextView) convertView.findViewById(R.id.price);
		ImageView call = (ImageView) convertView.findViewById(R.id.apartement_icon_call);
		call.setTag("call");
		call.setTag(R.id.apartment_position, position);
		call.setOnClickListener(a);
		ImageView apartmentMiniFoto = (ImageView) convertView.findViewById(R.id.apartement_icon);
		apartmentMiniFoto.setTag("mini");
		apartmentMiniFoto.setOnClickListener(a);
		ImageLoader.getInstance().displayImage(imageUri, apartmentMiniFoto);
		
		apartment_txt.setText(apartment.getCity() + "," + apartment.getStreetAddress());
		apartment_price.setText(String.valueOf(apartment.getPrice()));

		//make list feature
		if (apartment.getFeaturesList().isEmpty()) {
			TextView feature_empty = new TextView(convertView.getContext());
			feature_empty.setText(context.getResources().getString(R.string.feature_list_empty));
		} else {
			String[] f_array = apartment.getFeaturesList().split(",");

			if (feature.moveToFirst()) {
				do {
					int id = feature.getInt(feature.getColumnIndex(DB.TABLE_FEATURE_ID));
					String ico = feature.getString(feature.getColumnIndexOrThrow(DB.TABLE_FEATURE_ICO));
					for (String o : f_array) {
						int AFId = Integer.parseInt(o);
						if (id == AFId) {
							ImageView featureImg = new ImageView(parent.getContext());
							featureImg.setImageBitmap(Images.getBitmapFromAsset("feature/" + ico + ".png"));
							ll.addView(featureImg);
							break;
						}
					}
					// do what ever you want here
				} while (feature.moveToNext());
			}

		}
		return convertView;
	}
}
