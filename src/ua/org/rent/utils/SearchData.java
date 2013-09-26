package ua.org.rent.utils;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchData implements Parcelable {

	public int city_id;
	public ArrayList<Integer> district_id;

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(city_id);
		out.writeList(district_id);

	}

	public static final Parcelable.Creator<SearchData> CREATOR = new Parcelable.Creator<SearchData>() {
		public SearchData createFromParcel(Parcel in) {
			return new SearchData(in);
		}

		public SearchData[] newArray(int size) {
			return new SearchData[size];
		}
	};

	private SearchData(Parcel in) {
		city_id = in.readInt();
		 in.readList(district_id, Integer.class.getClassLoader());

	}

	public SearchData(int city_id, ArrayList<Integer> district_id) {
		this.city_id = city_id;
		this.district_id = district_id;
	}
}