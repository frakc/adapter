package com.example.user.teastadapter.dummy;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ItemDao {


  @Query("SELECT * FROM itemWrapper ORDER BY uid ASC")
  DataSource.Factory<Integer, ItemWrapper> getAll();

  @Insert
  void insertAll(List<ItemWrapper> list);

  @Update
  void updateItem(ItemWrapper itemWrapper);

  @Query("DELETE FROM itemwrapper WHERE content =''")
  void deleteStatus();
}
