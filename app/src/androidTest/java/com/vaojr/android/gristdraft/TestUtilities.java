package com.vaojr.android.gristdraft;

import android.content.ClipData;
import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;
import com.vaojr.android.gristdraft.GristContract.ListEntry;
import com.vaojr.android.gristdraft.GristContract.ItemListEntry;

/**
 * Created by him on 7/31/15.
 */
public class TestUtilities extends AndroidTestCase {
    static final long TEST_DATE = 1419033600L; // December 20th, 2014

    static void validateCurrentRecord(
            String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx== -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                "' did not match the expected value '" +
                expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    static ContentValues createExampleListValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(ListEntry.COLUMN_NAME, "Example List");
        testValues.put(ListEntry.COLUMN_DATE, TEST_DATE);

        return testValues;
    }

    static ContentValues createItemListValues(long listRowId) {
        ContentValues itemListValues = new ContentValues();
        itemListValues.put(ItemListEntry.COLUMN_LIST_ID, listRowId);
        itemListValues.put(ItemListEntry.COLUMN_ITEM_NAME, "Example Item 0");
        itemListValues.put(ItemListEntry.COLUMN_AMOUNT, 3);
        itemListValues.put(ItemListEntry.COLUMN_CATEGORY, "Category Zero");
        itemListValues.put(ItemListEntry.COLUMN_STORE_NAME, "MyMy's Fruit");
        itemListValues.put(ItemListEntry.COLUMN_PRICE, 777);

        return itemListValues;
    }
}
