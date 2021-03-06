/*
 * Copyright (c) 2016. Osred Brockhoist <osred.brockhoist@hotmail.com>. All Rights Reserved.
 */

package com.flyingosred.app.perpetualcalendar.database.util;

import java.io.File;
import java.util.Calendar;

public final class Utils {

    public static String composeName(String prefix, String body, String postfix) {
        StringBuilder sb = new StringBuilder();
        if (prefix != null) {
            sb.append(prefix);
        }
        if (body != null) {
            sb.append("_");
            sb.append(body);
        }
        if (postfix != null) {
            sb.append("_");
            sb.append(postfix);
        }
        return formatNameString(sb.toString());
    }

    private static String formatNameString(String name) {
        return name.replace(" ", "_").replace("'", "").replace("-", "_").toLowerCase();
    }

    public static boolean isSameDay(Calendar day1, Calendar day2) {
        return day1.get(Calendar.YEAR) == day2.get(Calendar.YEAR)
                && day1.get(Calendar.MONTH) == day2.get(Calendar.MONTH)
                && day1.get(Calendar.DATE) == day2.get(Calendar.DATE);
    }

    public static boolean isDayBefore(Calendar calendar, Calendar baseCalendar) {

        if (calendar.get(Calendar.YEAR) < baseCalendar.get(Calendar.YEAR)) {
            return true;
        } else if (calendar.get(Calendar.YEAR) > baseCalendar.get(Calendar.YEAR)) {
            return false;
        }

        if (calendar.get(Calendar.MONTH) < baseCalendar.get(Calendar.MONTH)) {
            return true;
        } else if (calendar.get(Calendar.MONTH) > baseCalendar.get(Calendar.MONTH)) {
            return false;
        }

        if (calendar.get(Calendar.DATE) < baseCalendar.get(Calendar.DATE)) {
            return true;
        }
        return false;
    }

    public static boolean isDayAfter(Calendar calendar, Calendar baseCalendar) {
        if (calendar.get(Calendar.YEAR) > baseCalendar.get(Calendar.YEAR)) {
            return true;
        } else if (calendar.get(Calendar.YEAR) < baseCalendar.get(Calendar.YEAR)) {
            return false;
        }

        if (calendar.get(Calendar.MONTH) > baseCalendar.get(Calendar.MONTH)) {
            return true;
        } else if (calendar.get(Calendar.MONTH) < baseCalendar.get(Calendar.MONTH)) {
            return false;
        }

        if (calendar.get(Calendar.DATE) > baseCalendar.get(Calendar.DATE)) {
            return true;
        }
        return false;
    }

    public static void delete(File file) {
        if (file.isDirectory()) {
            if (file.list().length == 0) {
                file.delete();
            } else {
                String files[] = file.list();
                for (String temp : files) {
                    File fileDelete = new File(file, temp);
                    delete(fileDelete);
                }
                if (file.list().length == 0) {
                    file.delete();
                }
            }

        } else {
            file.delete();
        }
    }
}
