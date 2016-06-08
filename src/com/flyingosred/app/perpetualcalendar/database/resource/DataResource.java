package com.flyingosred.app.perpetualcalendar.database.resource;

import java.util.ArrayList;
import java.util.List;

public class DataResource extends ResourceBase {

    private final List<String> mDataList = new ArrayList<>();

    public DataResource(String type) {
        super(type);
    }

    public void add(String data) {
        mDataList.add(data);
    }

}
