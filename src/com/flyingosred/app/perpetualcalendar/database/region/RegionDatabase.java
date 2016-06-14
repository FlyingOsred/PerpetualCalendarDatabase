/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.region;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelDatabase;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;

public class RegionDatabase extends ExcelDatabase {

    private static final String EXCEL_SHEET_NAME = "Region";

    public RegionDatabase(ExcelHelper excelHelper) {
        add(new RegionResource(excelHelper.getSheet(EXCEL_SHEET_NAME)));
    }

}
