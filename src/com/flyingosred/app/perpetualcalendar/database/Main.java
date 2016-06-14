/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.flyingosred.app.perpetualcalendar.database.constellation.ConstellationProvider;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.holiday.HolidayProvider;
import com.flyingosred.app.perpetualcalendar.database.library.PerpetualCalendarContract;
import com.flyingosred.app.perpetualcalendar.database.lunar.Lunar;
import com.flyingosred.app.perpetualcalendar.database.lunar.LunarProvider;
import com.flyingosred.app.perpetualcalendar.database.platform.android.PlatformAndroid;
import com.flyingosred.app.perpetualcalendar.database.region.RegionProvider;
import com.flyingosred.app.perpetualcalendar.database.resource.Resource;
import com.flyingosred.app.perpetualcalendar.database.solarterm.SolarTermProvider;
import com.flyingosred.app.perpetualcalendar.database.util.Utils;

public class Main {

    public static void main(String[] args) {

        ExcelHelper excelHelper = new ExcelHelper();
        LunarProvider lunarProvider = new LunarProvider();
        SolarTermProvider solarTermProvider = new SolarTermProvider(excelHelper);
        ConstellationProvider constellationProvider = new ConstellationProvider(excelHelper);
        RegionProvider regionProvider = new RegionProvider(excelHelper);
        HolidayProvider holidayProvider = new HolidayProvider(excelHelper);

        List<DatabaseItem> databaseList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(PerpetualCalendarContract.MIN_DATE.getTimeInMillis());

        do {
            Lunar lunar = lunarProvider.get(calendar);
            int solarTermId = solarTermProvider.getId(calendar);
            if (solarTermId > 0) {
                System.out.println("Found solar term id " + solarTermId + " for " + calendar.getTime());
            }
            int constellationId = constellationProvider.getId(calendar);
            Map<String, String> holidayMap = holidayProvider.get(calendar);
            if (holidayMap.size() > 0) {
                System.out.println("Found holiday " + holidayMap);
            }
            DatabaseItem item = new DatabaseItem(calendar, lunar, solarTermId, constellationId, holidayMap);
            databaseList.add(item);
            calendar.add(Calendar.DATE, 1);
        } while (!Utils.isSameDay(calendar, PerpetualCalendarContract.MAX_DATE));

        List<Resource> resourceList = new ArrayList<>();
        resourceList.addAll(constellationProvider.getResources());
        resourceList.addAll(solarTermProvider.getResources());
        resourceList.addAll(regionProvider.getResources());
        resourceList.addAll(holidayProvider.getResources());
        
        PlatformAndroid platform = new PlatformAndroid();
        platform.generateResources(resourceList);
        platform.generateDatabase(databaseList);

        excelHelper.destroy();
    }

}
