package com.example.user.teastadapter;

import com.example.user.teastadapter.dummy.ItemWrapper;

public interface ItemClickListener {
  void onError();
  void onItem(ItemWrapper itemWrapper);
}
