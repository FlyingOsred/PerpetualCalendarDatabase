package com.flyingosred.app.perpetualcalendar.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.flyingosred.app.perpetualcalendar.database.constellation.ConstellationProvider;
import com.flyingosred.app.perpetualcalendar.database.holiday.HolidayProvider;
import com.flyingosred.app.perpetualcalendar.database.lunar.Lunar;
import com.flyingosred.app.perpetualcalendar.database.lunar.LunarProvider;
import com.flyingosred.app.perpetualcalendar.database.platform.android.PlatformAndroid;
import com.flyingosred.app.perpetualcalendar.database.solarterm.SolarTerm;
import com.flyingosred.app.perpetualcalendar.database.solarterm.SolarTermProvider;
import com.flyingosred.app.perpetualcalendar.database.util.Utils;

public class Main {

    public static void main(String[] args) {

        LunarProvider lunarProvider = new LunarProvider();
        SolarTermProvider solarTermProvider = new SolarTermProvider();
        ConstellationProvider constellationProvider = new ConstellationProvider();
        HolidayProvider holidayProvider = new HolidayProvider();

        List<DatabaseItem> databaseList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(1901, 1, 19);
        Calendar maxDate = Calendar.getInstance();
        maxDate.set(2100, 0, 1);

        while (!Utils.isSameDay(calendar, maxDate)) {
            Lunar lunar = lunarProvider.get(calendar);
            SolarTerm solarTerm = solarTermProvider.get(calendar);
            int constellationId = constellationProvider.get(calendar);
            List<Integer> holidayList = holidayProvider.get(calendar);
            DatabaseItem item = new DatabaseItem(calendar, lunar, solarTerm == null ? -1 : solarTerm.getId(),
                    constellationId, holidayList);
            databaseList.add(item);
            calendar.add(Calendar.DATE, 1);
        }

        System.out.println("Database size is " + databaseList.size());

        PlatformAndroid platform = new PlatformAndroid();
        platform.generate(databaseList);
    }

}
