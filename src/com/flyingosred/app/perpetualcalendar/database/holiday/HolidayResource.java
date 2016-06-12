package com.flyingosred.app.perpetualcalendar.database.holiday;

import com.flyingosred.app.perpetualcalendar.database.resource.Resource;

public class HolidayResource extends Resource {

    private static final String EXCEL_SHEET_NAME_SUFFIX = "Festival";

    private static final String TYPE_NAME = "Holiday";

    @Override
    public String getType() {
        return TYPE_NAME.toLowerCase();
    }

    @Override
    protected String getSheetName() {
        return EXCEL_SHEET_NAME_SUFFIX;
    }

}
