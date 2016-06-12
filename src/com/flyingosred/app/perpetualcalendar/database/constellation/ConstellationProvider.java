package com.flyingosred.app.perpetualcalendar.database.constellation;

import java.util.Calendar;

import com.flyingosred.app.perpetualcalendar.database.resource.Resource;

public final class ConstellationProvider {

    ConstellationDatabase mDatabase = new ConstellationDatabase();

    public int get(Calendar calendar) {
        return ConstellationDatabase.get(calendar);
    }

    public Resource getResource() {
        return mDatabase.getResource();
    }
}