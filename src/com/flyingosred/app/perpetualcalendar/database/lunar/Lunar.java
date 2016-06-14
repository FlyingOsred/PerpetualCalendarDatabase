/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.lunar;

public class Lunar {
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mDaysInMonth;
    private boolean mIsLeapMonth;

    public Lunar(int year, int month, int day, int daysInMonth, boolean isLeapMonth) {
        mYear = year;
        mMonth = month;
        mDay = day;
        mDaysInMonth = daysInMonth;
        mIsLeapMonth = isLeapMonth;
    }

    public int getYear() {
        return mYear;
    }

    public int getMonth() {
        return mMonth;
    }

    public int getDay() {
        return mDay;
    }

    public int getDaysInMonth() {
        return mDaysInMonth;
    }

    public boolean isLeapMonth() {
        return mIsLeapMonth;
    }
}
