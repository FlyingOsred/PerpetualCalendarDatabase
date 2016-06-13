package com.flyingosred.app.perpetualcalendar.database.excel;

import java.util.ArrayList;
import java.util.List;

import com.flyingosred.app.perpetualcalendar.database.data.Data;
import com.flyingosred.app.perpetualcalendar.database.resource.Resource;

public abstract class ExcelDatabase {

    private final List<Resource> mResourceList = new ArrayList<>();

    private final List<Data> mDataList = new ArrayList<>();

    public List<Resource> getResources() {
        return mResourceList;
    }

    public List<Data> getData() {
        return mDataList;
    }

    protected void add(Resource resource) {
        mResourceList.add(resource);
    }

    protected void add(Data data) {
        mDataList.add(data);
    }
}
