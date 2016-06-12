package com.flyingosred.app.perpetualcalendar.database.solarterm;

import com.flyingosred.app.perpetualcalendar.database.resource.ResourceBase;

public class SolarTermResource extends ResourceBase {

    private static final String NAME_PREFIX = "solar_term";

    @Override
    public String getType() {
        return NAME_PREFIX;
    }

    @Override
    protected String getSheetName() {
        return SolarTermDatabase.EXCEL_SHEET_NAME;
    }
}
