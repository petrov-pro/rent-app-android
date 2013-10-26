package ua.org.rent.adapters;

import android.content.Context;
import android.database.Cursor;
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
import ua.org.rent.widgets.FlowLayout;

public class ListApartmentAdapter extends SimpleCursorAdapter {

    private Context context;
    private ArrayList<Apartment> jresult;
    private Cursor features;
    private Cursor apartments;
    private int layout;
    private Result a;
    private String imageUri = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9_62PtouMa8v1rNw-qa1C8MDFeRiic_mZWORsPqP6n2fipoEijg";
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

            apartment_txt.setText(apartments.getString(apartments.getColumnIndex(DB.TABLE_APARTMENT_CITY_NAME)) + ","
                    + apartments.getString(apartments.getColumnIndex(DB.TABLE_APARTMENT_STREET_ADDRESS)));
            apartment_price.setText(String.valueOf(apartments.getInt(apartments.getColumnIndex(DB.TABLE_APARTMENT_PRICE))));
            LinearLayout answerType = (LinearLayout) convertView.findViewById(R.id.ll_answer_type);
            if (resultModel.apartmentIdFromCall == apartments.getInt(apartments.getColumnIndex(DB.TABLE_APARTMENT_ID))) {
                call.setVisibility(View.INVISIBLE);
                answerType.setVisibility(View.VISIBLE);
                ImageView answerPositive = (ImageView) convertView.findViewById(R.id.answer_positive);
                answerPositive.setOnClickListener(a);
                ImageView answerNegative = (ImageView) convertView.findViewById(R.id.answer_negative);
                answerNegative.setOnClickListener(a);
                answerNegative.setTag("answer_type");
                answerNegative.setTag(R.id.apartment_position, position);
                answerPositive.setTag("answer_type");
                answerPositive.setTag(R.id.apartment_position, position);
            } else {
                answerType.setVisibility(View.INVISIBLE);
                call.setVisibility(View.VISIBLE);
            }

            //make list feature
//			if (apartment.getFeaturesList().isEmpty()) {
//				TextView feature_empty = new TextView(convertView.getContext());
//				feature_empty.setText(context.getResources().getString(R.string.feature_list_empty));
//			} else {
//				String[] f_array = apartment.getFeaturesList().split(",");
//				if (feature.moveToFirst()) {
//					do {
//						int id = feature.getInt(feature.getColumnIndex(DB.TABLE_FEATURE_ID));
//						String ico = feature.getString(feature.getColumnIndexOrThrow(DB.TABLE_FEATURE_ICO));
//						for (String o : f_array) {
//							int AFId = Integer.parseInt(o);
//							if (id == AFId) {
//								ImageView featureImg = new ImageView(parent.getContext());
//								featureImg.setImageBitmap(Images.getBitmapFromAsset("feature/" + ico + ".png"));
//								ll.addView(featureImg);
//								break;
//							}
//						}
//						// do what ever you want here
//					} while (feature.moveToNext());
//				}
//			}
        }
        return convertView;
    }
}
