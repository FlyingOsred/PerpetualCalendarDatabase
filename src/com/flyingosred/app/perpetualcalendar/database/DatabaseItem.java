/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database;

import java.util.Calendar;
import java.util.Map;

import com.flyingosred.app.perpetualcalendar.database.lunar.Lunar;

public class DatabaseItem {

    private final Calendar mCalendar;

    private final Lunar mLunar;

    private final int mSolarTermId;

    private final int mConstellationId;

    private final Map<String, String> mHolidayMap;

    public DatabaseItem(Calendar calendar, Lunar lunar, int solarTermId, int constellationI,
            Map<String, String> holidayMap) {
        mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(calendar.getTimeInMillis());
        mLunar = lunar;
        mSolarTermId = solarTermId;
        mConstellationId = constellationI;
        mHolidayMap = holidayMap;
    }

    public Calendar get() {
        return mCalendar;
    }

    public Lunar getLunar() {
        return mLunar;
    }

    public int getSolarTermId() {
        return mSolarTermId;
    }

    public int getConstellationId() {
        return mConstellationId;
    }

    public Map<String, String> getHolidayMap() {
        return mHolidayMap;
    }
}
