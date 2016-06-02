package com.flyingosred.app.perpetualcalendar.database.lunar;

import java.util.Calendar;

import com.flyingosred.app.perpetualcalendar.database.util.Utils;

public final class LunarProvider {

    public Lunar get(Calendar solarCalendar) {
        int lunarYear = solarCalendar.get(Calendar.YEAR);
        int lunarMonth = 0;
        int lunarDay = 0;
        int daysInMonth = 0;
        boolean isLeapMonth = false;

        Calendar calendar = LunarDatabase.getSpringFestivalDay(lunarYear);
        if (Utils.isSameDay(solarCalendar, calendar)) {
            lunarMonth = 1;
            lunarDay = 1;
            daysInMonth = LunarDatabase.getLunarDaysInMonth(lunarYear, 0);
        } else {
            if (Utils.isDayBefore(solarCalendar, calendar)) {
                lunarYear--;
                calendar = LunarDatabase.getSpringFestivalDay(lunarYear);
            }

            int leapMonth = LunarDatabase.getLunarLeapMonth(lunarYear);

            for (int lunarMonthIndex = 1, bit = 0; lunarMonthIndex <= 12; lunarMonthIndex++, bit++) {
                if (leapMonth > 0 && (lunarMonthIndex == leapMonth + 1) && !isLeapMonth) {
                    lunarMonthIndex--;
                    isLeapMonth = true;
                } else {
                    isLeapMonth = false;
                }
                daysInMonth = LunarDatabase.getLunarDaysInMonth(lunarYear, bit);
                calendar.add(Calendar.DATE, daysInMonth);
                if (Utils.isDayAfter(calendar, solarCalendar)) {
                    calendar.add(Calendar.DATE, 0 - daysInMonth);
                    lunarMonth = lunarMonthIndex;
                    break;
                }
            }

            lunarDay = 1;
            do {
                if (Utils.isSameDay(solarCalendar, calendar)) {
                    break;
                }
                calendar.add(Calendar.DATE, 1);
                lunarDay++;
            } while (true);
        }

        Lunar lunar = new Lunar(lunarYear, lunarMonth, lunarDay, daysInMonth, isLeapMonth);
        return lunar;
    }
}
