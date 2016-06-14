/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.constellation;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelProvider;

public final class ConstellationProvider extends ExcelProvider {

    public ConstellationProvider(ExcelHelper excelHelper) {
        super(new ConstellationDatabase(excelHelper));
    }
}