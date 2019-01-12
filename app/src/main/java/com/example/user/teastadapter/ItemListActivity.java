package com.example.user.teastadapter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.user.teastadapter.dummy.AppDatabase;
import com.example.user.teastadapter.dummy.DummyContent;
import com.example.user.teastadapter.dummy.ItemDao;
import com.example.user.teastadapter.dummy.ItemWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

  /**
   * Whether or not the activity is in two-pane mode, i.e. running on a tablet
   * device.
   */
  AppDatabase db;
  Handler handler = new Handler();
  int page = 0;
  boolean error = true;
  ExecutorService executor;
  ItemDao itemDao;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_list);
    db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "item-test").fallbackToDestructiveMigration()
        .build();

    executor = Executors.newFixedThreadPool(5);
    executor.execute(new Runnable() {
      @Override
      public void run() {
        db.clearAllTables();
        loadNextPage();
      }
    });
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    itemDao = db.itemDao();
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        loadNextPage();
      }
    });

    View recyclerView = findViewById(R.id.item_list);
    assert recyclerView != null;
    final ItemWrapperAdapter adapter = new ItemWrapperAdapter(myListener);
    //make paged list
    PagedList.Config config = new PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(10)
        .build();


    LiveData<PagedList<ItemWrapper>> listBuidler =
        new LivePagedListBuilder<>(itemDao.getAll(), config)
            .setFetchExecutor(Executors
            .newSingleThreadExecutor())
            .setBoundaryCallback(boundaryCallback)
            .build();

    listBuidler.observe(this, new Observer<PagedList<ItemWrapper>>() {
      @Override
      public void onChanged(@Nullable PagedList<ItemWrapper> itemWrappers) {
        adapter.submitList(itemWrappers);
      }
    });

    ((RecyclerView) recyclerView).setAdapter(adapter);

  }

  ItemClickListener myListener = new ItemClickListener() {
    @Override
    public void onError() {
      loadNextPage();
    }

    @Override
    public void onItem(final ItemWrapper itemWrapper) {
      executor.execute(new Runnable() {
        @Override
        public void run() {
          itemWrapper.isSelected = !itemWrapper.isSelected;
//          ItemWrapper newWrapper = new ItemWrapper(itemWrapper.uid, itemWrapper.content,
//              itemWrapper.details, !itemWrapper.isSelected);
          itemDao.updateItem(itemWrapper);
        }
      });
    }
  };
  PagedList.BoundaryCallback<ItemWrapper> boundaryCallback = new PagedList.BoundaryCallback<ItemWrapper>() {
    @Override
    public void onZeroItemsLoaded() {
      super.onZeroItemsLoaded();
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull ItemWrapper itemAtFront) {
      super.onItemAtFrontLoaded(itemAtFront);
    }

    @Override
    public void onItemAtEndLoaded(@NonNull ItemWrapper itemAtEnd) {
      super.onItemAtEndLoaded(itemAtEnd);
      loadNextPage();
    }
  };
  void loadNextPage() {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        List<ItemWrapper> list = new ArrayList<>();

        if (error) {
          ItemWrapper wrapper = DummyContent.createDummyItem(page);
          wrapper.content = "";
          wrapper.details = "";
          list.add(wrapper);
          itemDao.insertAll(list);
          error = false;
        } else {
          itemDao.deleteStatus();
          for (int i = page; i < page + 10; i++) {
            list.add(DummyContent.createDummyItem(i));
          }
          page += 10;
          itemDao.insertAll(list);
//          error = true;
        }
      }
    });
  }
}
