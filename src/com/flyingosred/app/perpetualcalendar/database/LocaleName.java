package com.flyingosred.app.perpetualcalendar.database;

public final class LocaleName {

    private final String mLocale;

    private final String mName;

    public LocaleName(String locale, String name) {
        mLocale = locale;
        mName = name;
    }

    @Override
    public String toString() {
        return "locale is " + mLocale + ", name is " + mName;
    }

}
