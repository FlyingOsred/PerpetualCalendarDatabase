package com.flyingosred.app.perpetualcalendar.database.solarterm;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelProvider;

public class SolarTermProvider extends ExcelProvider {

    public SolarTermProvider(ExcelHelper excelHelper) {
        super(new SolarTermDatabase(excelHelper));
    }
}
