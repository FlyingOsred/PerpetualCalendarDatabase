package com.flyingosred.app.perpetualcalendar.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.flyingosred.app.perpetualcalendar.database.constellation.ConstellationProvider;
import com.flyingosred.app.perpetualcalendar.database.holiday.HolidayProvider;
import com.flyingosred.app.perpetualcalendar.database.library.PerpetualCalendarContract;
import com.flyingosred.app.perpetualcalendar.database.lunar.Lunar;
import com.flyingosred.app.perpetualcalendar.database.lunar.LunarProvider;
import com.flyingosred.app.perpetualcalendar.database.platform.android.PlatformAndroid;
import com.flyingosred.app.perpetualcalendar.database.region.RegionProvider;
import com.flyingosred.app.perpetualcalendar.database.resource.ResourceBase;
import com.flyingosred.app.perpetualcalendar.database.solarterm.SolarTermProvider;
import com.flyingosred.app.perpetualcalendar.database.util.Utils;

public class Main {

    public static void main(String[] args) {

        LunarProvider lunarProvider = new LunarProvider();
        SolarTermProvider solarTermProvider = new SolarTermProvider();
        ConstellationProvider constellationProvider = new ConstellationProvider();
        RegionProvider regionProvider = new RegionProvider();
        HolidayProvider holidayProvider = new HolidayProvider();

        List<DatabaseItem> databaseList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(PerpetualCalendarContract.MIN_DATE.getTimeInMillis());

        do {
            Lunar lunar = lunarProvider.get(calendar);
            int solarTermId = solarTermProvider.get(calendar);
            int constellationId = constellationProvider.get(calendar);
            List<Integer> holidayList = holidayProvider.get(calendar);
            DatabaseItem item = new DatabaseItem(calendar, lunar, solarTermId, constellationId, holidayList);
            databaseList.add(item);
            calendar.add(Calendar.DATE, 1);
        } while (!Utils.isSameDay(calendar, PerpetualCalendarContract.MAX_DATE));

        List<ResourceBase> resourceList = new ArrayList<>();
        resourceList.add(solarTermProvider.getResource());
        resourceList.add(regionProvider.getResource());

        PlatformAndroid platform = new PlatformAndroid();
        platform.generateResources(resourceList);
    }

}
