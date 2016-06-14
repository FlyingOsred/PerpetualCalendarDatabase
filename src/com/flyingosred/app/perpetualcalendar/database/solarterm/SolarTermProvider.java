/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.solarterm;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelProvider;

public class SolarTermProvider extends ExcelProvider {

    public SolarTermProvider(ExcelHelper excelHelper) {
        super(new SolarTermDatabase(excelHelper));
    }
}
