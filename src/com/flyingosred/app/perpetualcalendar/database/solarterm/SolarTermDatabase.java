package com.flyingosred.app.perpetualcalendar.database.solarterm;

import java.util.Calendar;

import com.flyingosred.app.perpetualcalendar.database.resource.Resource;

public final class SolarTermDatabase {

    public static final String EXCEL_SHEET_NAME = "SolarTerm";

    private final SolarTermResource mResource = new SolarTermResource();

    private SolarTermData mData;
    
    public SolarTermDatabase() {
        mResource.Init();
    }

    public Resource getResource() {
        return mResource;
    }

    public int get(Calendar calendar) {
        if (mData != null) {
            return mData.get(calendar);
        }
        return -1;
    }
}
