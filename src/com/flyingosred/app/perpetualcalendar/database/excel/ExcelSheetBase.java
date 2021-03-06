/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.excel;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public abstract class ExcelSheetBase {

    protected static final int EXCEL_COL_ID = 0;
    protected static final int EXCEL_COL_DATA_START_WITH_SOURCE = 5;
    protected static final int EXCEL_COL_DATA_START = 2;

    protected static final int EXCEL_ROW_DATA_START = 2;

    private final String mType;

    public ExcelSheetBase(XSSFSheet sheet) {
        mType = getNamePrefix(sheet.getSheetName()).toLowerCase();
    }

    public String getType() {
        return mType;
    }

    protected String getNamePrefix(String sheetName) {
        String[] words = sheetName.split("(?=\\p{Upper})");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            sb.append(word);
            if (i != words.length - 1) {
                sb.append("_");
            }
        }
        return sb.toString();
    }
}
