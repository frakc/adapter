package com.example.user.teastadapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.user.teastadapter.dummy.Item;
import com.example.user.teastadapter.dummy.ItemWrapper;

import java.util.zip.Inflater;

public class ItemWrapperAdapter extends PagedListAdapter<ItemWrapper, RecyclerView.ViewHolder> {
  public static int STATUS = 0;
  public static int ITEM = 1;
  private ItemClickListener listener;

  protected ItemWrapperAdapter(ItemClickListener listener) {
    super(DIFF_CALLBACK);
    this.listener = listener;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    if (i == ITEM) {
      View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_content, viewGroup,
          false);
      return new ItemHolder(v);
    } else {
      View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_error, viewGroup,
          false);
      return new StatusHolder(v);
    }

  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
    if (viewHolder instanceof ItemHolder) {
      ((ItemHolder) viewHolder).bind(getItem(i));
    } else {
      ((StatusHolder) viewHolder).bind(getItem(i));
    }
  }

  @Override
  public int getItemViewType(int position) {

    if (TextUtils.isEmpty(getItem(position).content)) {
      return STATUS;
    } else return ITEM;

  }

  class ItemHolder extends RecyclerView.ViewHolder {
    final TextView mIdView;
    final TextView mContentView;
    final CheckBox mCheckbox;
    ItemWrapper itemWrapper;

    ItemHolder(View view) {
      super(view);
      mIdView = (TextView) view.findViewById(R.id.id_text);
      mContentView = (TextView) view.findViewById(R.id.content);
      mCheckbox = (CheckBox) view.findViewById(R.id.checkbox);

    }

    void bind(final ItemWrapper itemWrapper) {
      this.itemWrapper = itemWrapper;
      mIdView.setText(String.valueOf(itemWrapper.uid));
      mContentView.setText(itemWrapper.content);
      mCheckbox.setChecked(itemWrapper.isSelected);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.onItem(itemWrapper);
        }
      });
    }
  }

  class StatusHolder extends RecyclerView.ViewHolder {
    StatusHolder(View view) {
      super(view);

    }

    void bind(ItemWrapper itemWrapper) {

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.onError();
        }
      });
    }
  }

  public static final DiffUtil.ItemCallback<ItemWrapper> DIFF_CALLBACK = new DiffUtil.ItemCallback<ItemWrapper>() {
    @Override
    public boolean areItemsTheSame(@NonNull ItemWrapper item1, @NonNull ItemWrapper item2) {
      return item1.uid==(item2.uid);
    }

    @Override
    public boolean areContentsTheSame(@NonNull ItemWrapper d1, @NonNull ItemWrapper d2) {
      if(d1.uid==1){
        Log.e("mcheck", "areContentsTheSame: d1 "+d1.isSelected+" d2 "+d2.isSelected);
      }
      return d1.equals(d2);
    }
  };

}
