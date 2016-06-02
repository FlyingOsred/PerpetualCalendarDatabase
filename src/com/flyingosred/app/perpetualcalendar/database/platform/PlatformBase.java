package com.flyingosred.app.perpetualcalendar.database.platform;

public abstract class PlatformBase {

    public static final int PLATFORM_ANDROID = 1;

    private final int mType;

    public PlatformBase(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }
}
