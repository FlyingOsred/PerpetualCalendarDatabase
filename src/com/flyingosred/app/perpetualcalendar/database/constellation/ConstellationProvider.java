package com.flyingosred.app.perpetualcalendar.database.constellation;

import java.util.Calendar;

public final class ConstellationProvider {

    public int get(Calendar calendar) {
        return ConstellationDatabase.get(calendar);
    }
}
