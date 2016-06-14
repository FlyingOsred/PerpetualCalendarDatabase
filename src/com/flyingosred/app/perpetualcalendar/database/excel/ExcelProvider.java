/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.excel;

import java.util.Calendar;
import java.util.List;

import com.flyingosred.app.perpetualcalendar.database.data.Data;
import com.flyingosred.app.perpetualcalendar.database.library.PerpetualCalendarContract;
import com.flyingosred.app.perpetualcalendar.database.resource.Resource;

public abstract class ExcelProvider {

    private final ExcelDatabase mDatabase;

    public ExcelProvider(ExcelDatabase database) {
        mDatabase = database;
    }

    public List<Resource> getResources() {
        return mDatabase.getResources();
    }

    public int getId(Calendar calendar) {
        int id = PerpetualCalendarContract.INVALID;
        for (Data data : mDatabase.getData()) {
            id = data.getId(calendar);
            if (id > 0) {
                break;
            }
        }
        return id;
    }

    protected ExcelDatabase getDatabase() {
        return mDatabase;
    }
}
