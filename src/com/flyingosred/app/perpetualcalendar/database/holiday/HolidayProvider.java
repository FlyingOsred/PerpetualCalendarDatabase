package com.flyingosred.app.perpetualcalendar.database.holiday;

import java.util.Calendar;
import java.util.List;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.resource.Resource;

public class HolidayProvider {

    private final HolidayDatabase mDatabase = new HolidayDatabase();

    public List<Integer> get(Calendar calendar) {
        return null;
    }

    public Resource getResource() {
        return mDatabase.getResource();
    }
}
