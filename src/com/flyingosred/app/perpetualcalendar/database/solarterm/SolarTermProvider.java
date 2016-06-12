package com.flyingosred.app.perpetualcalendar.database.solarterm;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.resource.ResourceBase;
import com.flyingosred.app.perpetualcalendar.database.util.Utils;

public class SolarTermProvider {

    private final SolarTermDatabase mDatabase = new SolarTermDatabase();

    public SolarTermProvider() {
    }

    public int get(Calendar calendar) {
        return mDatabase.get(calendar);
    }

    public ResourceBase getResource() {
        return mDatabase.getResource();
    }
}
