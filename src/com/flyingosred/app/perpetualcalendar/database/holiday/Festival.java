package com.flyingosred.app.perpetualcalendar.database.holiday;

import java.util.List;

import com.flyingosred.app.perpetualcalendar.database.locale.LocaleName;

public class Festival {

    public static final int INDEX_ID_OFFSET = 2;

    private final int mId;

    private final int mIndex;

    private final String mType;

    private final List<LocaleName> mLocaleList;

    public Festival(int id, int index, String type, List<LocaleName> localeList) {
        mId = id;
        mIndex = index;
        mType = type;
        mLocaleList = localeList;
    }

    @Override
    public String toString() {
        return "Festival [mId=" + mId + ", mType=" + mType + ", mLocaleList=" + mLocaleList + "]";
    }

    public int getId() {
        return mId;
    }

    public int getIndex() {
        return mIndex;
    }

    public String getType() {
        return mType;
    }

    public List<LocaleName> getLocaleList() {
        return mLocaleList;
    }
}
