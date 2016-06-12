package com.flyingosred.app.perpetualcalendar.database.region;

import com.flyingosred.app.perpetualcalendar.database.resource.ResourceBase;

public class RegionProvider {

    private final RegionResource mResouce = new RegionResource();

    public RegionProvider() {
        mResouce.Init();
    }

    public ResourceBase getResource() {
        return mResouce;
    }
}
