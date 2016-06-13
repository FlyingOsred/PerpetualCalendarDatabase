package com.flyingosred.app.perpetualcalendar.database.holiday;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelDatabase;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;

public class HolidayDatabase extends ExcelDatabase {

    private static final String EXCEL_FESTIVAL_SHEET_NAME_SUFFIX = "Festival";

    public static final String EXCEL_HOLIDAY_SHEET_NAME_SUFFIX = "Holiday";

    public HolidayDatabase(ExcelHelper excelHelper) {
        for (XSSFSheet sheet : excelHelper.getSheets(EXCEL_FESTIVAL_SHEET_NAME_SUFFIX)) {
            add(new HolidayResource(sheet));
        }
        for (XSSFSheet sheet : excelHelper.getSheets(EXCEL_HOLIDAY_SHEET_NAME_SUFFIX)) {
            add(new HolidayData(sheet));
        }
    }
}
