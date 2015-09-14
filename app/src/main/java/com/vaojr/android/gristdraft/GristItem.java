package com.vaojr.android.gristdraft;

import java.util.Date;

/**
 * Created by him on 8/11/15.
 */

public class GristItem {
    private long mItemId;
    private long mListId;
    private String mItemName;
    private int mPrice;
    private String mCategory;
    private String mStoreName;
    private int mAmount;

    public GristItem() {
        mItemId = -1;
        mItemName = null;
        mPrice = 0;
        mCategory = null;
        mStoreName = null;
        mAmount = 0;
        mListId = -1;
    }

    public int getAmount() {
        return mAmount;
    }

    public void setAmount(int amount) {
        mAmount = amount;
    }

    public long getItemId() {
        return mItemId;
    }

    public void setItemId(long itemId) {
        mItemId = itemId;
    }

    public long getListId() {
        return mListId;
    }

    public void setListId(long listId) {
        mListId = listId;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getStoreName() {
        return mStoreName;
    }

    public void setStoreName(String storeName) {
        mStoreName = storeName;
    }
}
