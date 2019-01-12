package com.example.user.teastadapter.dummy;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ItemWrapper.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
  public abstract ItemDao itemDao();
}
