package com.flyingosred.app.perpetualcalendar.database.holiday;

import java.util.List;

import com.flyingosred.app.perpetualcalendar.database.LocaleName;

public class Festival {

    private final int mId;

    private final String mType;

    private final List<LocaleName> mLocaleList;

    public Festival(int id, String type, List<LocaleName> localeList) {
        mId = id;
        mType = type;
        mLocaleList = localeList;
    }

    @Override
    public String toString() {
        return "Festival [mId=" + mId + ", mType=" + mType + ", mLocaleList=" + mLocaleList + "]";
    }

}
