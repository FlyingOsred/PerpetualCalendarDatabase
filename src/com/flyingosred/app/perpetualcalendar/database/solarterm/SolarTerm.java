package com.flyingosred.app.perpetualcalendar.database.solarterm;

import java.util.Date;
import java.util.List;

import com.flyingosred.app.perpetualcalendar.database.LocaleName;

public class SolarTerm {

    private final int mId;

    private final String mName;

    private final List<LocaleName> mLocaleList;

    private final List<Date> mDateList;

    public SolarTerm(int id, String name, List<LocaleName> localeList, List<Date> dateList) {
        mId = id;
        mName = name;
        mLocaleList = localeList;
        mDateList = dateList;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public List<LocaleName> getLocaleList() {
        return mLocaleList;
    }

    public List<Date> getDateList() {
        return mDateList;
    }

    @Override
    public String toString() {
        return "SolarTerm [mId=" + mId + ", mName=" + mName + ", mLocaleList=" + mLocaleList + ", mDateList="
                + mDateList + "]";
    }
}
