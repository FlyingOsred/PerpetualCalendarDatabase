package com.flyingosred.app.perpetualcalendar.database.excel;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public abstract class ExcelSheetBase {

    private static final int EXCEL_COL_ID = 0;
    private static final int EXCEL_COL_NAME_START = 1;
    private static final int EXCEL_COL_NAME_END = 4;
    private static final int EXCEL_COL_DATA_START = 5;

    private static final int EXCEL_ROW_LOCALE_NAME = 1;
    private static final int EXCEL_ROW_DATA_START = 2;

    public ExcelSheetBase() {
    }

    public void Init() {
        ExcelHelper excelHelper = new ExcelHelper();
        List<XSSFSheet> sheetList = excelHelper.getSheets(getSheetName());
        parse(sheetList);
        excelHelper.destroy();
    }

    protected abstract void parse(List<XSSFSheet> sheetList);

    protected abstract String getSheetName();

}
