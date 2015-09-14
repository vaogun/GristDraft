package com.vaojr.android.gristdraft;

import java.util.Date;

/**
 * Created by him on 7/31/15.
 */
public class GristList {
    public long mId;
    public String mListName;
    public Date mCreationDate;

    public GristList(GristList gristList) {
        mId = gristList.getId();
        mListName = gristList.getListName();
        mCreationDate = gristList.mCreationDate;
    }

    public GristList() {
        mId = -1;
        mListName = null;
        mCreationDate = new Date();
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getListName() {
        return mListName;
    }

    public void setListName(String listName) {
        mListName = listName;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }
}
