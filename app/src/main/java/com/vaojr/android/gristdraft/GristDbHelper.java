package com.vaojr.android.gristdraft;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.vaojr.android.gristdraft.GristContract.ListEntry;
import com.vaojr.android.gristdraft.GristContract.ItemListEntry;

/**
 * Created by him on 7/30/15.
 */
public class GristDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "grist.db";

    public GristDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LIST_TABLE = "CREATE TABLE " + ListEntry.TABLE_NAME + " (" +
                ListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ListEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                ListEntry.COLUMN_DATE + " INTEGER NOT NULL" +  ");";

        final String SQL_CREATE_ITEM_LIST_TABLE = "CREATE TABLE " + ItemListEntry.TABLE_NAME + " (" +
                ItemListEntry._ID + " INTEGER PRIMARY KEY, " +

                ItemListEntry.COLUMN_LIST_ID + " INTEGER NOT NULL, " +
                ItemListEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
                ItemListEntry.COLUMN_AMOUNT + "  INTEGER NOT NULL, " +
                ItemListEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                ItemListEntry.COLUMN_STORE_NAME + " TEXT NOT NULL, " +
                ItemListEntry.COLUMN_PRICE + " INTEGER, " +

                // Set up the list_id column as a foreign key to the list table
                " FOREIGN KEY (" + ItemListEntry.COLUMN_LIST_ID + ") REFERENCES " +
                ListEntry.TABLE_NAME + " (" + ListEntry._ID + "));";

        db.execSQL(SQL_CREATE_LIST_TABLE);
        db.execSQL(SQL_CREATE_ITEM_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Since you will want to preserve user data we will use "ALTER TABLE" statement to
        // add new columns and increment DATABASE_VERSION number when upgrading
    }

    public long insertGristList(GristList gristList) {
        ContentValues cv = new ContentValues();
        cv.put(ListEntry.COLUMN_NAME, gristList.getListName());
        cv.put(ListEntry.COLUMN_DATE, gristList.getCreationDate().getTime());
        return getWritableDatabase().insert(ListEntry.TABLE_NAME, null, cv);
    }

    public long insertGristItem(GristItem gristItem) {
        ContentValues cv = new ContentValues();
        cv.put(ItemListEntry.COLUMN_LIST_ID, gristItem.getListId());
        cv.put(ItemListEntry.COLUMN_ITEM_NAME, gristItem.getItemName());
        cv.put(ItemListEntry.COLUMN_AMOUNT, gristItem.getAmount());
        cv.put(ItemListEntry.COLUMN_CATEGORY, gristItem.getCategory());
        cv.put(ItemListEntry.COLUMN_STORE_NAME, gristItem.getStoreName());
        cv.put(ItemListEntry.COLUMN_PRICE, gristItem.getPrice());
        return getWritableDatabase().insert(ItemListEntry.TABLE_NAME, null, cv);
    }

    public GristListCursor queryGls() {
        // Equivalent to "select * from run order by start_date desc"
        Cursor wrapped = getReadableDatabase()
                .query(
                        ListEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        ListEntry.COLUMN_DATE + " DESC");
        return new GristListCursor(wrapped);
    }

    public GristListCursor queryListName(long id) {
        // Equivalent to "select * from list order by start_date desc"
        Cursor wrapped = getReadableDatabase()
                .query(
                        ListEntry.TABLE_NAME,
                        null,
                        ListEntry._ID + "=?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null);
        return new GristListCursor(wrapped);
    }

    /**
     * A convenience class to wrap a cursor that returns rows from the "list" table.
     * The {@link GristList getGirstList()} method will give you a GristList instance representing
     * the current row
     */
    public static class GristListCursor extends CursorWrapper {

        public GristListCursor(Cursor c) {
            super(c);
        }

        /**
         * Returns a GristList object configured for the current row,
         * or null if the current row is invalid
         */


        public GristList getGristList() {
            if (isBeforeFirst() || isAfterLast())
                return null;

            GristList gl = new GristList();
            long glId = getLong(getColumnIndex(ListEntry._ID));
            gl.setId(glId);
            String glName = getString(getColumnIndex(ListEntry.COLUMN_NAME));
            gl.setListName(glName);
            return gl;
        }

    }


    public GristItemCursor queryItemList(long id) {
        // Equivalent to "select * from list order by start_date desc"
        Cursor wrapped = getReadableDatabase()
                .query(
                        ItemListEntry.TABLE_NAME,
                        null,
                        ItemListEntry.COLUMN_LIST_ID + "=?",
                        new String[] {String.valueOf(id)},
                        null,
                        null,
                        ItemListEntry.COLUMN_ITEM_NAME + " ASC");
        DatabaseUtils.dumpCursor(wrapped);
        return new GristItemCursor(wrapped);
    }

    /**
     * A convenience class to wrap a cursor that returns rows from the "list" table.
     * The {@link GristList getGirstList()} method will give you a GristList instance representing
     * the current row
     */
    public static class GristItemCursor extends CursorWrapper {

        public GristItemCursor(Cursor c) {
            super(c);
        }
    }




}
