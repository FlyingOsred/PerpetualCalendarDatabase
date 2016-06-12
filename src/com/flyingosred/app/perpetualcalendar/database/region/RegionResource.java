package com.flyingosred.app.perpetualcalendar.database.region;

import com.flyingosred.app.perpetualcalendar.database.resource.ResourceBase;

public class RegionResource extends ResourceBase {

    private static final String EXCEL_SHEET_NAME = "Region";

    @Override
    public String getType() {
        return EXCEL_SHEET_NAME.toLowerCase();
    }

    @Override
    protected String getSheetName() {
        return EXCEL_SHEET_NAME;
    }

}
