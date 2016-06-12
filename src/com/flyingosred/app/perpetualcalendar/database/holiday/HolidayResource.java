package com.flyingosred.app.perpetualcalendar.database.holiday;

import com.flyingosred.app.perpetualcalendar.database.resource.ResourceBase;

public class HolidayResource extends ResourceBase {

    private static final String EXCEL_SHEET_NAME_SUFFIX = "Festival";

    @Override
    public String getType() {
        return null;
    }

    @Override
    protected String getSheetName() {
        return EXCEL_SHEET_NAME_SUFFIX;
    }

}
