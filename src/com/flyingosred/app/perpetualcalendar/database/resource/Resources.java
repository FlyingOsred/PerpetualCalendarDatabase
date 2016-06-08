package com.flyingosred.app.perpetualcalendar.database.resource;

import java.util.ArrayList;
import java.util.List;

public class Resources {

    private final List<ResourceBase> mResourceList = new ArrayList<>();

    public void add(ResourceBase resource) {
        mResourceList.add(resource);
    }

    public List<ResourceBase> get() {
        return mResourceList;
    }

}
