package com.flyingosred.app.perpetualcalendar.database;

import java.util.Calendar;
import com.flyingosred.app.perpetualcalendar.database.constellation.ConstellationProvider;
import com.flyingosred.app.perpetualcalendar.database.holiday.HolidayProvider;
import com.flyingosred.app.perpetualcalendar.database.lunar.LunarProvider;
import com.flyingosred.app.perpetualcalendar.database.platform.android.PlatformAndroid;
import com.flyingosred.app.perpetualcalendar.database.solarterm.SolarTermProvider;

public class Main {

    public static void main(String[] args) {

        LunarProvider lunarProvider = new LunarProvider();
        SolarTermProvider solarTermProvider = new SolarTermProvider();
        ConstellationProvider constellationProvider = new ConstellationProvider();
        HolidayProvider holidayProvider = new HolidayProvider();

        Calendar calendar = Calendar.getInstance();
        calendar.set(1901, 1, 19);
        Calendar maxDate = Calendar.getInstance();
        maxDate.set(2100, 0, 1);

        PlatformAndroid platform = new PlatformAndroid();
        platform.generateSolarTermXml(solarTermProvider.get());
    }

}
