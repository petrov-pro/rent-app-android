package ua.org.rent.entities;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import ua.org.rent.settings.Consts;

public class SearchData implements Parcelable {

	public Integer city_id;
	public ArrayList<District> districts;
	public ArrayList<Feature> features;
	public String city_name;
	public Integer countRoom;
	public Integer countBed;
	public Integer priceFrom;
	public Integer priceTo;

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(city_id);
		out.writeString(city_name);
		out.writeInt(countRoom);
		out.writeInt(countBed);
		out.writeInt(priceFrom);
		out.writeInt(priceTo);
		out.writeList(features);
		out.writeList(districts);
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
		city_name = in.readString();
		countRoom = in.readInt();
		countBed = in.readInt();
		priceFrom = in.readInt();
		priceTo = in.readInt();
		in.readList(features, null);
		in.readList(districts, null);
	}

	public SearchData() {
		//default init
		this.city_id = Consts.DEFAULT_CITY_ID;
		this.city_name = Consts.getDefaultCityName();
		this.districts = new ArrayList<District>() {
			{
				add(Consts.getDefaultDistrict());
			}
		};
		this.features = new ArrayList<Feature>() {
			{
				add(Consts.getDefaultFeature());
			}
		};
		this.countRoom = Consts.DEFAULT_ROOM_ID;
		this.countBed = Consts.DEFAULT_BEDS_ID;
		this.priceFrom = Consts.PRICE_FROM;
		this.priceTo = Consts.PRICE_TO;
	}
}