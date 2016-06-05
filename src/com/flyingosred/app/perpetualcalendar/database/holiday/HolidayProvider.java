package com.flyingosred.app.perpetualcalendar.database.holiday;

import java.util.Calendar;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;

public class HolidayProvider {

    private final HolidayDatabase mDatabase = new HolidayDatabase();

    public HolidayProvider() {
        init();
    }
    
    public void get(Calendar calendar) {
        
    }

    private void init() {
        ExcelHelper excelHelper = new ExcelHelper();
        mDatabase.init(excelHelper);
        excelHelper.destroy();
    }
}
