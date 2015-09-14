package com.vaojr.android.gristdraft;

import android.provider.BaseColumns;

/**
 * Created by him on 7/30/15.
 */
public class GristContract {

    public static final class ListEntry implements BaseColumns {
        // We are implementing BaseColumns, which contains a constant, "_ID"
        public static final String TABLE_NAME = "list";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATE = "date";
    }

    public static final class ItemListEntry implements BaseColumns {
        // We are implementing BaseColumns, which contains a constant, "_ID"
        public static final String TABLE_NAME = "list_item";

        public static final String COLUMN_ITEM_NAME = "item_name";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_CATEGORY ="category";
        public static final String COLUMN_STORE_NAME = "store_name";
        public static final String COLUMN_PRICE = "price";

        // Foreign key in list table
        public static final String COLUMN_LIST_ID = "list_id";
    }
}
