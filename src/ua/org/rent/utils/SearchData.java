package ua.org.rent.utils;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

public class SearchData implements Parcelable {

	public int city_id;
	public ArrayList<Integer> district_id;
	public String city_name;
	public Map<Integer, String> district_name;
	public Integer countRoom;
	public Integer countBed;

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(city_id);
		out.writeList(district_id);
		out.writeString(city_name);
		out.writeMap(district_name);
		out.writeInt(countRoom);
		out.writeInt(countBed);

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
		city_name = in.readString();
		in.readMap(district_name, null);
		countRoom = in.readInt();
		countBed = in.readInt();

	}

	public SearchData(int city_id, ArrayList<Integer> district_id, String city_name, Map<Integer, String> district_name, int countRoom, int countBed) {
		this.city_id = city_id;
		this.district_id = district_id;
		this.city_name = city_name;
		this.district_name = district_name;
		this.countRoom = countRoom;
		this.countBed = countBed;
	}
}