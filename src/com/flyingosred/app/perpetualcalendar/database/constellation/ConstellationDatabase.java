package com.flyingosred.app.perpetualcalendar.database.constellation;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelDatabase;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;

public class ConstellationDatabase extends ExcelDatabase {

    public static final String EXCEL_SHEET_NAME = "Constellation";

    public ConstellationDatabase(ExcelHelper excelHelper) {
        XSSFSheet sheet = excelHelper.getSheet(EXCEL_SHEET_NAME);
        add(new ConstellationResource(sheet));
        add(new ConstellationData(sheet));
    }
}