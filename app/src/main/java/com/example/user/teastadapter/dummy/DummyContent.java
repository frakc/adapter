package com.example.user.teastadapter.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

  /**
   * An array of sample (dummy) items.
   */
  public static final List<ItemWrapper> ITEMS = new ArrayList<ItemWrapper>();

  /**
   * A map of sample (dummy) items, by ID.
   */

  private static final int COUNT = 25;

  static {
    // Add some sample items.
    for (int i = 1; i <= COUNT; i++) {
      addItem(createDummyItem(i));
    }
  }

  private static void addItem(ItemWrapper item) {
    ITEMS.add(item);
  }

  public static ItemWrapper createDummyItem(int position) {
    return new ItemWrapper(position, "Item " + position, makeDetails(position));
  }

  private static String makeDetails(int position) {
    StringBuilder builder = new StringBuilder();
    builder.append("Details about Item: ").append(position);
    for (int i = 0; i < position; i++) {
      builder.append("\nMore details information here.");
    }
    return builder.toString();
  }

}
