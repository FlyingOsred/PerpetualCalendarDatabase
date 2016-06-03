package com.flyingosred.app.perpetualcalendar.database.holiday;

import java.util.List;

import com.flyingosred.app.perpetualcalendar.database.LocaleName;

public class HolidayRegion {

    private final int mId;

    private final String mName;

    private final List<LocaleName> mLocaleList;

    public HolidayRegion(int id, String name, List<LocaleName> localeList) {
        mId = id;
        mName = name;
        mLocaleList = localeList;
    }

    @Override
    public String toString() {
        return "HolidayRegion [mId=" + mId + ", mName=" + mName + ", mLocaleList=" + mLocaleList + "]";
    }
}
