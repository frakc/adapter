package com.example.user.teastadapter.dummy;

import android.annotation.TargetApi;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.Objects;

@Entity
public class ItemWrapper {
  @PrimaryKey
  @NonNull
  public int uid;
  @ColumnInfo(name = "content")
  public String content;
  @ColumnInfo(name = "details")
  public String details;
  @ColumnInfo(name = "selected")
  public boolean isSelected;

  public ItemWrapper() {

  }

  public ItemWrapper(@NonNull int id, String content, String details) {
    this.uid = id;
    this.content = content;
    this.details = details;
  }

  public ItemWrapper(@NonNull int id, String content, String details, boolean isSelected) {
    this.uid = id;
    this.content = content;
    this.details = details;
    this.isSelected = isSelected;
  }

  @TargetApi(Build.VERSION_CODES.KITKAT)
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ItemWrapper wrapper = (ItemWrapper) o;
    return
        String.valueOf(isSelected).equals(String.valueOf(wrapper.isSelected)) &&
        Objects.equals(uid, wrapper.uid) &&
        Objects.equals(content, wrapper.content) &&
        Objects.equals(details, wrapper.details);
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @Override
  public int hashCode() {
    return Objects.hash(uid, content, details, isSelected);
  }

  @Override
  public String toString() {
    return "ItemWrapper{" +
        "uid='" + uid + '\'' +
        ", isSelected=" + isSelected +
        '}';
  }
}
