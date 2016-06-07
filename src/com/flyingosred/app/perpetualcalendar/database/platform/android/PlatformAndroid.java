package com.flyingosred.app.perpetualcalendar.database.platform.android;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.flyingosred.app.perpetualcalendar.database.locale.LocaleName;
import com.flyingosred.app.perpetualcalendar.database.platform.PlatformBase;
import com.flyingosred.app.perpetualcalendar.database.solarterm.SolarTerm;
import com.flyingosred.app.perpetualcalendar.database.util.Utils;
import com.flyingosred.app.perpetualcalendar.database.xml.XmlHelper;

public final class PlatformAndroid extends PlatformBase {

    private static final String RESOURCES_ROOT = "resources";

    private static final String ELEMENT_STRING_ARRAY = "string-array";

    private static final String ELEMENT_STRING = "string";

    private static final String ATTR_NAME = "name";

    private static final String ELEMENT_ITEM = "item";

    private static final String VALUES_DIR_NAME = "values";

    private static final String XML_SUFFIX = ".xml";

    private static final String SOLAR_TERM_STRINGS_FILE_NAME = "strings_solar_term.xml";

    private static final String SOLAR_TERM_ARRAYS_FILE_NAME = "arrays_solar_term.xml";

    private static final String SOLAR_TERM_ARRAYS_PREFIX = "arrays_solar_term_";

    private static final String SOLAR_TERM_ARRAYS_NAME = "solar_term_name";

    private static final String SOLAR_TERM_ARRAYS_NAME_PREFIX = "solar_term_";

    public PlatformAndroid() {
        super(PlatformBase.PLATFORM_ANDROID, "Android");
    }

    @Override
    public void generate() {

    }

    public void generateSolarTermXml(List<SolarTerm> list) {
        Utils.delete(new File(getGeneratedPath()));
        File valuesDir = new File(getGeneratedPath(), VALUES_DIR_NAME);
        if (!valuesDir.exists()) {
            valuesDir.mkdirs();
        }
        File solarTermStringsFile = new File(valuesDir, SOLAR_TERM_ARRAYS_FILE_NAME);
        List<String> itemNameList = new ArrayList<>();
        HashMap<Integer, List<Date>> dateMap = new HashMap<>();
        HashMap<String, HashMap<String, String>> localeNameMap = new HashMap<>();
        for (SolarTerm solarTerm : list) {
            itemNameList.add("@string/" + solarTerm.getName());
            for (Date date : solarTerm.getDateList()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                if (dateMap.containsKey(year)) {
                    List<Date> dateList = dateMap.get(year);
                    dateList.add(date);
                } else {
                    List<Date> dateList = new ArrayList<>();
                    dateList.add(date);
                    dateMap.put(year, dateList);
                }
                for (LocaleName localeName : solarTerm.getLocaleList()) {
                    String locale = localeName.getLocale();
                    String name = localeName.getName();
                    if (localeNameMap.containsKey(locale)) {
                        HashMap<String, String> nameMap = localeNameMap.get(locale);
                        nameMap.put(solarTerm.getName(), name);
                    } else {
                        HashMap<String, String> nameMap = new HashMap<>();
                        nameMap.put(solarTerm.getName(), name);
                        localeNameMap.put(locale, nameMap);
                    }
                }
            }
        }
        XmlHelper.createXmlFile(solarTermStringsFile.getAbsolutePath(), RESOURCES_ROOT, ELEMENT_STRING_ARRAY,
                SOLAR_TERM_ARRAYS_NAME, ELEMENT_ITEM, itemNameList);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Map.Entry<Integer, List<Date>> entry : dateMap.entrySet()) {
            int year = entry.getKey();
            List<String> dateItemNameList = new ArrayList<>();
            List<Date> dateList = entry.getValue();
            for (Date date : dateList) {
                dateItemNameList.add(formatter.format(date));
            }
            File yearArrayFile = new File(valuesDir, SOLAR_TERM_ARRAYS_PREFIX + year + XML_SUFFIX);
            XmlHelper.createXmlFile(yearArrayFile.getAbsolutePath(), RESOURCES_ROOT, ELEMENT_STRING_ARRAY,
                    SOLAR_TERM_ARRAYS_NAME_PREFIX + year, ELEMENT_ITEM, dateItemNameList);
        }
        for (Map.Entry<String, HashMap<String, String>> entry : localeNameMap.entrySet()) {
            String locale = entry.getKey();
            String localeValueName = null;
            if (locale.equals("zh_CN")) {
                localeValueName = "values-zh-rCN";
            } else if (locale.equals("zh_TW")) {
                localeValueName = "values-zh-rTW";
            } else if (locale.equals("zh_HK")) {
                localeValueName = "values-zh-rHK";
            } else if (locale.equals("en_US")) {
                localeValueName = "values";
            }
            File localeValuesDir = new File(getGeneratedPath(), localeValueName);
            if (!localeValuesDir.exists()) {
                localeValuesDir.mkdirs();
            }
            HashMap<String, String> nameMap = entry.getValue();
            File localeArrayFile = new File(localeValuesDir, SOLAR_TERM_STRINGS_FILE_NAME);
            XmlHelper.createXmlFile(localeArrayFile.getAbsolutePath(), RESOURCES_ROOT, ELEMENT_STRING, nameMap);
        }
    }
}
