package com.flyingosred.app.perpetualcalendar.database.platform.android;

import com.flyingosred.app.perpetualcalendar.database.platform.PlatformBase;

public final class PlatformAndroid extends PlatformBase {

    public PlatformAndroid() {
        super(PlatformBase.PLATFORM_ANDROID, "Android");
    }

    @Override
    protected void generateDatabase() {
        super.generateDatabase();
        getSqlHelper().excute("CREATE TABLE 'android_metadata' ('locale' TEXT DEFAULT 'en_US');");
        getSqlHelper().excute("INSERT INTO 'android_metadata' VALUES ('en_US');");
    }

}
