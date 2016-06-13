package com.flyingosred.app.perpetualcalendar.database.constellation;

import java.util.Calendar;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelProvider;

public final class ConstellationProvider extends ExcelProvider {

    public ConstellationProvider(ExcelHelper excelHelper) {
        super(new ConstellationDatabase(excelHelper));
    }
}