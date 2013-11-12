/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.rent.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 *
 * @author petroff
 */
public class District implements Parcelable{

	private String title;
	private int _id;

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
	public static final Parcelable.Creator<District> CREATOR = new Parcelable.Creator<District>() {
		public District createFromParcel(Parcel in) {
			return new District(in);
		}

		public District[] newArray(int size) {
			return new District[size];
		}
	};

	private District(Parcel in) {
		title = in.readString();
		_id = in.readInt();
	}

	public District(String title, Integer id) {
		this.title = title;
		this._id = (int) id;
	}

	@Override
	public boolean equals(Object v) {
		boolean retVal = false;
		if (v instanceof District) {
			District ptr = (District) v;
			retVal = ptr._id == this._id;
		}
		return retVal;
	}
}
