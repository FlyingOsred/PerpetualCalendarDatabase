package com.flyingosred.app.perpetualcalendar.database.constellation;

import java.util.Calendar;
import java.util.Date;

final class ConstellationDataItem {

    private final Date mStartDate;

    private final Date mEndDate;

    private final int mId;

    public ConstellationDataItem(int id, Date startDate, Date endDate) {
        mStartDate = startDate;
        mEndDate = endDate;
        mId = id;
    }

    public boolean match(int month, int day) {
        Calendar start = Calendar.getInstance();
        start.setTime(mStartDate);
        Calendar end = Calendar.getInstance();
        end.setTime(mEndDate);
        if ((month == start.get(Calendar.MONTH) && day >= start.get(Calendar.DATE))
                || (month == end.get(Calendar.MONTH) && day <= end.get(Calendar.DATE))) {
            return true;
        }
        return false;
    }

    public int getId() {
        return mId;
    }

}
