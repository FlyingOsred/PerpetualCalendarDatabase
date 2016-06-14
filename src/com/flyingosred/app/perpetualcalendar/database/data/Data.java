/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.data;

import java.util.Calendar;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelSheetBase;

public abstract class Data extends ExcelSheetBase {

    public Data(XSSFSheet sheet) {
        super(sheet);
    }

    public abstract int getId(Calendar calendar);
}
