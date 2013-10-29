package ua.org.rent.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import ua.org.rent.R;
import ua.org.rent.controller.Result;
import ua.org.rent.entities.Apartment;
import ua.org.rent.library.DB;
import ua.org.rent.models.ResultModel;
import ua.org.rent.settings.Consts;
import ua.org.rent.utils.Images;
import ua.org.rent.widgets.FlowLayout;

public class ListApartmentAdapter extends SimpleCursorAdapter {

	private Context context;
	private ArrayList<Apartment> jresult;
	private Cursor features;
	public Cursor apartments;
	private int layout;
	private Result a;
	public String imageUri = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9_62PtouMa8v1rNw-qa1C8MDFeRiic_mZWORsPqP6n2fipoEijg";
	private ResultModel resultModel;

	public ListApartmentAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, ResultModel resultModel) {

		super(context, layout, c, from, to);
		this.layout = layout;
		this.context = context;
		this.features = resultModel.features;
		this.apartments = c;
		this.a = (Result) context;
		this.resultModel = resultModel;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, this.layout, null);
		}

		if (apartments.moveToPosition(position)) {

			FlowLayout ll = (FlowLayout) convertView.findViewById(R.id.apartment_feature);
			LinearLayout apartement_ll = (LinearLayout) convertView.findViewById(R.id.apartement_ll);
			ll.removeAllViews();
			TextView apartment_txt = (TextView) convertView.findViewById(R.id.address);
			TextView apartment_price = (TextView) convertView.findViewById(R.id.price);
			ImageView call = (ImageView) convertView.findViewById(R.id.apartement_icon_call);
			call.setTag(Consts.TAG_CALL);
			call.setTag(R.id.apartment_position, position);
			call.setOnClickListener(a);
			ImageView apartmentMiniFoto = (ImageView) convertView.findViewById(R.id.apartement_icon);
			apartmentMiniFoto.setTag(Consts.TAG_MINI);
			apartmentMiniFoto.setOnClickListener(a);
			ImageLoader.getInstance().displayImage(imageUri, apartmentMiniFoto);

			apartment_txt.setText(apartments.getString(apartments.getColumnIndex(DB.TABLE_APARTMENT_CITY_NAME)) + ","
					+ apartments.getString(apartments.getColumnIndex(DB.TABLE_APARTMENT_STREET_ADDRESS)));
			apartment_price.setText(String.valueOf(apartments.getInt(apartments.getColumnIndex(DB.TABLE_APARTMENT_PRICE))));
			int isHistory = apartments.getInt(apartments.getColumnIndex(DB.TABLE_CALL_HISTORY_ID_ALIAS));
			if (isHistory != 0) {
				int answerType1 = apartments.getInt(apartments.getColumnIndex(DB.TABLE_CALL_HISTORY_IS_POSITIVE));
				if (answerType1 == 0) {
					apartement_ll.setBackgroundColor(Color.RED);
				} else {
					apartement_ll.setBackgroundColor(Color.GREEN);
				}
			} else {
				apartement_ll.setBackgroundColor(Color.BLACK);
			}

			LinearLayout answerType = (LinearLayout) convertView.findViewById(R.id.ll_answer_type);
			if (resultModel.apartmentIdFromCall == apartments.getInt(apartments.getColumnIndex(DB.TABLE_APARTMENT_ID))) {
				call.setVisibility(View.INVISIBLE);
				answerType.setVisibility(View.VISIBLE);
				ImageView answerPositive = (ImageView) convertView.findViewById(R.id.answer_positive);
				answerPositive.setOnClickListener(a);
				ImageView answerNegative = (ImageView) convertView.findViewById(R.id.answer_negative);
				answerNegative.setOnClickListener(a);
				answerNegative.setTag(Consts.TAG_AT);
				answerNegative.setTag(R.id.apartment_position, position);
				answerNegative.setTag(R.id.answer_type, 0);
				answerPositive.setTag(Consts.TAG_AT);
				answerPositive.setTag(R.id.apartment_position, position);
				answerPositive.setTag(R.id.answer_type, 1);

				answerPositive.setTag(R.id.is_history, isHistory);
				answerNegative.setTag(R.id.is_history, isHistory);
			} else {
				answerType.setVisibility(View.INVISIBLE);
				call.setVisibility(View.VISIBLE);
			}

			//make list feature
			String fetureList = apartments.getString(apartments.getColumnIndex(DB.TABLE_APARTMENT_FL));
	
			if (fetureList == null ||  fetureList.isEmpty()) {
				TextView feature_empty = new TextView(convertView.getContext());
				feature_empty.setText(context.getResources().getString(R.string.feature_list_empty));
			} else {
				String[] f_array = fetureList.split(",");
				for (String o : f_array) {
					ImageView featureImg = new ImageView(parent.getContext());
					featureImg.setImageBitmap(Images.getBitmapFromAsset("feature/" + o + ".png"));
					ll.addView(featureImg);
				}
			}
		}
		return convertView;
	}
}
