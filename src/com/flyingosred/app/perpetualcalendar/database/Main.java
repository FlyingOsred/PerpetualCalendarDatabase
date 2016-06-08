package com.flyingosred.app.perpetualcalendar.database;

import com.flyingosred.app.perpetualcalendar.database.platform.android.PlatformAndroid;
import com.flyingosred.app.perpetualcalendar.database.resource.Resources;
import com.flyingosred.app.perpetualcalendar.database.solarterm.SolarTermDatabase;

public class Main {

    public static void main(String[] args) {

        Resources resources = new Resources();
        SolarTermDatabase.parse(resources);

        PlatformAndroid platform = new PlatformAndroid();
    }

}
