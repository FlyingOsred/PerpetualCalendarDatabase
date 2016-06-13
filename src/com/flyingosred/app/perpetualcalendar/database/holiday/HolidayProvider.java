package com.flyingosred.app.perpetualcalendar.database.holiday;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import com.flyingosred.app.perpetualcalendar.database.data.Data;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelProvider;

public class HolidayProvider extends ExcelProvider {

    public HolidayProvider(ExcelHelper excelHelper) {
        super(new HolidayDatabase(excelHelper));
    }

    public Map<String, String> get(Calendar calendar) {
        Map<String, String> map = new LinkedHashMap<>();
        for (Data data : getDatabase().getData()) {
            HolidayData holidayData = (HolidayData) data;
            String holidayString = holidayData.getString(calendar);
            if (holidayString != null) {
                map.put(holidayData.getRegion(), holidayString);
            }
        }
        return map;
    }
}
