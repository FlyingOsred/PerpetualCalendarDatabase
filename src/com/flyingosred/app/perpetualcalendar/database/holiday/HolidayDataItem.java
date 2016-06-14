/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.holiday;

import java.util.Date;

public final class HolidayDataItem {

    private final String mRegion;

    private final String mType;

    private final int mId;

    private final Date mDate;

    private final int mOffWork;

    public HolidayDataItem(String region, String type, int id, Date date, int offWork) {
        mRegion = region;
        mType = type;
        mId = id;
        mDate = date;
        mOffWork = offWork;
    }

    @Override
    public String toString() {
        return "HolidayDataItem [mRegion=" + mRegion + ", mType=" + mType + ", mId=" + mId + ", mDate=" + mDate
                + ", mOffWork=" + mOffWork + "]";
    }

    public String formatString() {
        return mType + ":" + mId;
    }

    public int getOffWork() {
        return mOffWork;
    }

    public String getRegion() {
        return mRegion;
    }

    public Date getDate() {
        return mDate;
    }
    
    public String getType() {
        return mType;
    }
}
