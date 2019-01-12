package com.example.user.teastadapter.dummy;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * A dummy item representing a piece of content.
 */
@Entity
public class Item implements Parcelable {
  @PrimaryKey
  public  String id;
  @ColumnInfo(name= "content")
  public  String content;
  @ColumnInfo(name = "details")
  public  String details;
  @ColumnInfo(name = "selected")
  public  boolean isSelected;
  public Item(String id, String content, String details) {
    this.id = id;
    this.content = content;
    this.details = details;
  }

  @Override
  public String toString() {
    return content;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.content);
    dest.writeString(this.details);
  }

  protected Item(Parcel in) {
    this.id = in.readString();
    this.content = in.readString();
    this.details = in.readString();
  }

  public static final Creator<Item> CREATOR = new Creator<Item>() {
    @Override
    public Item createFromParcel(Parcel source) {
      return new Item(source);
    }

    @Override
    public Item[] newArray(int size) {
      return new Item[size];
    }
  };
}
