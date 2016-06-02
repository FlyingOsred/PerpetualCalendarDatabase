package com.flyingosred.app.perpetualcalendar.database;

import java.util.Calendar;

import com.flyingosred.app.perpetualcalendar.database.constellation.ConstellationProvider;
import com.flyingosred.app.perpetualcalendar.database.holiday.HolidayProvider;
import com.flyingosred.app.perpetualcalendar.database.lunar.Lunar;
import com.flyingosred.app.perpetualcalendar.database.lunar.LunarProvider;
import com.flyingosred.app.perpetualcalendar.database.solarterm.SolarTerm;
import com.flyingosred.app.perpetualcalendar.database.solarterm.SolarTermProvider;
import com.flyingosred.app.perpetualcalendar.database.util.Utils;

public class Main {

    public static void main(String[] args) {

        LunarProvider lunarProvider = new LunarProvider();
        SolarTermProvider solarTermProvider = new SolarTermProvider();
        ConstellationProvider constellationProvider = new ConstellationProvider();
        HolidayProvider holidayProvider = new HolidayProvider();

        Calendar calendar = Calendar.getInstance();
        calendar.set(1901, 1, 19);
        Calendar maxDate = Calendar.getInstance();
        maxDate.set(2100, 1, 1);

        while (!Utils.isSameDay(calendar, maxDate)) {
            Lunar lunar = lunarProvider.get(calendar);
            SolarTerm solarTerm = solarTermProvider.get(calendar);
            int constellationId = constellationProvider.get(calendar);
            calendar.add(Calendar.DATE, 1);
        }
    }

}
