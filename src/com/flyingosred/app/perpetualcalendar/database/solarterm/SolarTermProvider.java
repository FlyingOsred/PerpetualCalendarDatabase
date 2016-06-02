package com.flyingosred.app.perpetualcalendar.database.solarterm;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.util.Utils;

public class SolarTermProvider {

    private final SolarTermDatabase mDatabase = new SolarTermDatabase();

    public SolarTermProvider() {
        init();
    }

    public SolarTerm get(Calendar calendar) {
        List<SolarTerm> list = mDatabase.get();
        if (list != null && list.size() > 0) {
            for (SolarTerm solarTerm : list) {
                List<Date> dateList = solarTerm.getDateList();
                for (Date date : dateList) {
                    Calendar tempCalendar = Calendar.getInstance();
                    tempCalendar.setTime(date);
                    if (Utils.isSameDay(calendar, tempCalendar)) {
                        System.out.println("Found solar term " + solarTerm.toString() + " for " + calendar.getTime());
                        return solarTerm;
                    }
                }
            }
        }
        return null;
    }

    private void init() {
        ExcelHelper excelHelper = new ExcelHelper();
        mDatabase.init(excelHelper);
        excelHelper.destroy();
    }

}
