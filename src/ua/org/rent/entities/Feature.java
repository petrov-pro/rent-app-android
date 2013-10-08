/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author petroff
 */
public class Feature {

	private String title;
	private int _id;
	private String ico;

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public String getTitle() {
		return title;
	}

	public Integer getId() {
		return _id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setId(Integer _id) {
		this._id = _id;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeString(title);
		out.writeInt(_id);
	}
	public static final Parcelable.Creator<Feature> CREATOR = new Parcelable.Creator<Feature>() {
		public Feature createFromParcel(Parcel in) {
			return new Feature(in);
		}

		public Feature[] newArray(int size) {
			return new Feature[size];
		}
	};

	private Feature(Parcel in) {
		title = in.readString();
		_id = in.readInt();
	}

	public Feature(String title, Integer id) {
		this.title = title;
		this._id = (int) id;
		this.ico = "all";
	}

	public Feature(String title, Integer id, String ico) {
		this.title = title;
		this._id = (int) id;
		this.ico = ico;
	}

	@Override
	public boolean equals(Object v) {
		boolean retVal = false;
		if (v instanceof Feature) {
			Feature ptr = (Feature) v;
			retVal = ptr._id == this._id;
		}
		return retVal;
	}
}
