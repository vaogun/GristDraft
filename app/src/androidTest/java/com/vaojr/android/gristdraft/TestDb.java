package com.vaojr.android.gristdraft;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

/**
 * Created by him on 7/30/15.
 */
public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    // Since we want each test to start with a clean slate
    void deleteTheDb() {
        mContext.deleteDatabase(GristDbHelper.DATABASE_NAME);
    }

    // This function gets called before each test is executed to delete the database.
    // This makes sure that we always have a clean test.
    public void setUp() {
        deleteTheDb();
    }

    public long insertList() {
        // First step: Get reference to a writable database
        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        GristDbHelper dbHelper = new GristDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Second Step: Create ContentValues of what you want to insert
        // (we will use createExampleListValues)
        ContentValues testValues = TestUtilities.createExampleListValues();

        // Third Step: Insert ContentValues into database and get a row ID back
        long locationRowId;
        locationRowId = db.insert(GristContract.ListEntry.TABLE_NAME, null, testValues);

        // Verify that we got a row back
        assertTrue(locationRowId != -1);

        // Data's inserted, IN THEORY. Now pull data out to verify that it is correct.

        // Fourth Step: Query the database and receive a Cursor back
        // A cursor is your primary interface to the query results.
        Cursor cursor = db.query(
                GristContract.ListEntry.TABLE_NAME, // Table to query
                null, // All Columns
                null, // Columns for the "WHERE" clause
                null, // Values for the "WHERE" clause
                null, // Columns to group "BY"
                null, // Columns to filter by row groups
                null // Sort order
        );

        // Move the cursor to a valid database row and check to see
        // if we got any records back from the query
        assertTrue("Error: No records returned from list query", cursor.moveToFirst());

        // Fifth Step: Validate data in resulting Cursor with the original ContentValues
        // (we will use the validateCurrentRecord function in TestUtilities to validate the query)
        TestUtilities.validateCurrentRecord("testInsertReadDb ListEntry failed to validate",
                cursor, testValues);

        // Move the cursor to demonstrate that there is only one record in the database
        assertFalse("Error: More than one record returned from weather query",
                cursor.moveToNext());

        // Sixth Step: Close Cursor and Database
        cursor.close();
        db.close();
        return locationRowId;
    }

    public void testCreateDb() throws Throwable {
        // Build a Hashset of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (DB version info)
        final HashSet<String>tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(GristContract.ListEntry.TABLE_NAME);
        tableNameHashSet.add(GristContract.ItemListEntry.TABLE_NAME);

        mContext.deleteDatabase(GristDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new GristDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // Do we have the tables that we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly", c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while (c.moveToNext());

        // If this fails, it means that the DB doesn't contain the list entry table
        assertTrue("Error: Your DB was created without the list entry", tableNameHashSet.isEmpty());

        // Now, do our tables contain the correct columns
        c = db.rawQuery("PRAGMA table_info(" + GristContract.ListEntry.TABLE_NAME + ")", null);

        assertTrue("Error: This means that we were unable to query the DB for table info.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> listColumnHashSet = new HashSet<String>();
        listColumnHashSet.add(GristContract.ListEntry._ID);
        listColumnHashSet.add(GristContract.ListEntry.COLUMN_NAME);
        listColumnHashSet.add(GristContract.ListEntry.COLUMN_DATE);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            listColumnHashSet.remove(columnName);
        } while (c.moveToNext());

        // if this fails, it means that your DB doesn't contain
        // all of the required list entry columns
        assertTrue("Error: The DB doesn't contain all of the required location entry columns",
                listColumnHashSet.isEmpty());
        c.close();
        db.close();
    }

    public void testListTable() {
        insertList();
    }


    public void testItemListTable() {
        // First insert the list, and then use the the listRowId to insert
        // the item.

        long listRowId = insertList();

        assertFalse("Error: Location Not Inserted Correctly", listRowId == -1L);

        // First step: Get reference to writable database
        // If there is an error in the SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        GristDbHelper dbHelper = new GristDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Second step (ItemList): Create ItemList values
        ContentValues itemListValues = TestUtilities.createItemListValues(listRowId);

        // Third step (ItemList): Insert ContentValues into database and get a row ID back
        long itemListRowId = db.insert(GristContract.ItemListEntry.TABLE_NAME, null, itemListValues);
        assertTrue(itemListRowId != -1);

        // Fourth step: Query the database and receive a Cursor back
        // A cursor is your primary interface to the query results
        Cursor itemListCursor = db.query(
                GristContract.ItemListEntry.TABLE_NAME, // table to query
                null, // leaving "columns" null just returns all the columns
                null, // cols for "where" clause
                null, // values for "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        // Move the cursor to the first valid database row and check to see if we have any rows
        assertTrue("Error: No Records returned from itemList query", itemListCursor.moveToFirst());

        // Fifth step: Validate the itemList query
        TestUtilities.validateCurrentRecord("testInsertReadDb itemListEntry failed to validate",
                itemListCursor, itemListValues);

        // Move the cursor to demonstrate that there is only one record in the database
        assertFalse("Error: More than one record returned from itemList query",
                itemListCursor.moveToNext());

        // Sixth step: close cursor and database
        itemListCursor.close();
        dbHelper.close();
    }
}
