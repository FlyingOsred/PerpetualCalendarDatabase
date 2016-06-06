package com.flyingosred.app.perpetualcalendar.database.holiday;

import java.util.Calendar;
import java.util.List;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;

public class HolidayProvider {

    private final HolidayDatabase mDatabase = new HolidayDatabase();

    public HolidayProvider() {
        init();
    }

    public List<Integer> get(Calendar calendar) {
        return mDatabase.get(calendar);
    }

    private void init() {
        ExcelHelper excelHelper = new ExcelHelper();
        mDatabase.init(excelHelper);
        excelHelper.destroy();
    }
}
