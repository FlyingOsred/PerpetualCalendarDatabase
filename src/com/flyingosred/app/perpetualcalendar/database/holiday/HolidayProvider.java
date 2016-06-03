package com.flyingosred.app.perpetualcalendar.database.holiday;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;

public class HolidayProvider {

    private final HolidayDatabase mDatabase = new HolidayDatabase();

    public HolidayProvider() {
        init();
    }

    private void init() {
        ExcelHelper excelHelper = new ExcelHelper();
        mDatabase.init(excelHelper);
        excelHelper.destroy();
    }
}
