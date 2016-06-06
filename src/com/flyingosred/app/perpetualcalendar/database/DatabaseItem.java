package com.flyingosred.app.perpetualcalendar.database;

import java.util.Calendar;
import java.util.List;

import com.flyingosred.app.perpetualcalendar.database.lunar.Lunar;

public class DatabaseItem {

    private final Calendar mCalendar;

    private final Lunar mLunar;

    private final int mSolarTermId;

    private final int mConstellationId;

    private final List<Integer> mHolidayList;

    public DatabaseItem(Calendar calendar, Lunar lunar, int solarTermId, int constellationI,
            List<Integer> holidayList) {
        mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(calendar.getTimeInMillis());
        mLunar = lunar;
        mSolarTermId = solarTermId;
        mConstellationId = constellationI;
        mHolidayList = holidayList;
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

    public List<Integer> getHolidayList() {
        return mHolidayList;
    }

}
