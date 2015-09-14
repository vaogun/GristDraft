package com.vaojr.android.gristdraft;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by him on 7/31/15.
 */
public class ListManager {
    private static final String TAG = "ListManager";

    private static final String PREF_FILE = "lists";
    private static final String PREF_CURRENT_LIST_ID = "ListManager.currentListId";

    public static ListManager sListManager;
    private Context mAppContext;
    private GristDbHelper mHelper;
    //private SharedPreferences mSharedPreferences;
    //private long mCurrentListId;

    private ListManager(Context appContext) {
        mAppContext = appContext;
        mHelper = new GristDbHelper(mAppContext);
        //mSharedPreferences = appContext.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        //mCurrentListId = mSharedPreferences.getLong(PREF_CURRENT_LIST_ID, -1)
    }

    public static ListManager get(Context c) {
        if (sListManager == null) {
            // Use the application context to avoid leaking activities
            sListManager = new ListManager(c.getApplicationContext());
        }
        return sListManager;
    }

    public long insertList(String listName) {
        GristList gristList = new GristList();
        gristList.setListName(listName);
        gristList.setId(mHelper.insertGristList(gristList));
        return gristList.getId();
    }


    public long insertItem(long listId, String itemName, int price, String category,
                           String storeName, int amount) {
        GristItem gristItem = new GristItem();
        gristItem.setListId(listId);
        gristItem.setItemName(itemName);
        gristItem.setPrice(price);
        gristItem.setCategory(category);
        gristItem.setStoreName(storeName);
        gristItem.setAmount(amount);
        gristItem.setItemId(mHelper.insertGristItem(gristItem));
        return gristItem.getItemId();
    }

    public GristDbHelper.GristListCursor queryGls() {
        return  mHelper.queryGls();
    }

    public GristDbHelper.GristItemCursor queryItemList(long id) {
        return mHelper.queryItemList(id);
    }

    public GristDbHelper.GristListCursor queryListName(long id) {
        return mHelper.queryListName(id);
    }

}
