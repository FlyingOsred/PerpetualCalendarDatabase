package com.flyingosred.app.perpetualcalendar.database.constellation;

import com.flyingosred.app.perpetualcalendar.database.resource.ResourceBase;

public class ConstellationResource extends ResourceBase {

    private static final String EXCEL_SHEET_NAME = "Constellation";

    @Override
    public String getType() {
        return EXCEL_SHEET_NAME.toLowerCase();
    }

    @Override
    protected String getSheetName() {
        return EXCEL_SHEET_NAME;
    }

}
