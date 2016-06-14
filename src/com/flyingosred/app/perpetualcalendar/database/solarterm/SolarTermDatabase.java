/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.solarterm;

import java.util.Calendar;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flyingosred.app.perpetualcalendar.database.data.Data;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelDatabase;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.library.PerpetualCalendarContract;

public final class SolarTermDatabase extends ExcelDatabase {

    public static final String EXCEL_SHEET_NAME = "SolarTerm";

    public SolarTermDatabase(ExcelHelper excelHelper) {
        XSSFSheet sheet = excelHelper.getSheet(EXCEL_SHEET_NAME);
        add(new SolarTermResource(sheet));
        add(new SolarTermData(sheet));
    }

    public int get(Calendar calendar) {
        List<Data> list = getData();
        int id = PerpetualCalendarContract.INVALID;
        for (Data data : list) {
            id = data.getId(calendar);
            if (id > 0) {
                break;
            }
        }
        return id;
    }
}
