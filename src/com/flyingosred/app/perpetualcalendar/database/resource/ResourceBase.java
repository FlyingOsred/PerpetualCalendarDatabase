package com.flyingosred.app.perpetualcalendar.database.resource;

public abstract class ResourceBase {

    private final String mType;

    public ResourceBase(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }
}
