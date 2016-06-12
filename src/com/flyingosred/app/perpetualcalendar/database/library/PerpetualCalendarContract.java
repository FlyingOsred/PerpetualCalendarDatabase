package com.flyingosred.app.perpetualcalendar.database.library;

import java.util.Calendar;

public class PerpetualCalendarContract {

    public static final Calendar MAX_DATE = Calendar.getInstance();

    public static final Calendar MIN_DATE = Calendar.getInstance();

    static {
        MIN_DATE.set(1901, 1, 19);
        MAX_DATE.set(2099, 11, 31);
    }
}
