package com.flyingosred.app.perpetualcalendar.database.region;

import com.flyingosred.app.perpetualcalendar.database.resource.Resource;

public class RegionProvider {

    private final RegionResource mResouce = new RegionResource();

    public RegionProvider() {
        mResouce.Init();
    }

    public Resource getResource() {
        return mResouce;
    }
}
