package com.flyingosred.app.perpetualcalendar.database.holiday;

import java.util.Date;

public class Holiday {

    private final HolidayRegion mRegion;

    private final Festival mFestival;

    private final Date mDate;

    private final int mOffWork;

    public Holiday(HolidayRegion region, Festival festival, Date date, int offWork) {
        mRegion = region;
        mFestival = festival;
        mDate = date;
        mOffWork = offWork;
    }

    @Override
    public String toString() {
        return "Holiday [mRegion=" + mRegion + ", mFestival=" + mFestival + ", mDate=" + mDate + ", mOffWork="
                + mOffWork + "]";
    }

    public HolidayRegion getRegion() {
        return mRegion;
    }

    public Festival getFestival() {
        return mFestival;
    }

    public Date getDate() {
        return mDate;
    }

    public int getOffWork() {
        return mOffWork;
    }
}
