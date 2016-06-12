package com.flyingosred.app.perpetualcalendar.database.data;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelSheetBase;

public abstract class Data extends ExcelSheetBase {

    @Override
    protected void parse(List<XSSFSheet> sheetList) {
    }
}
