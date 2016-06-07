package com.flyingosred.app.perpetualcalendar.database.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ResourceBase {

    HashMap<String, HashMap<String, String>> mLocaleMap = new HashMap<>();

    public void add(String name, String locale, String localization, String value) {
        HashMap<String, String> localeNameMap;
        if (mLocaleMap.containsKey(locale)) {
            localeNameMap = mLocaleMap.get(locale);
        } else {
            localeNameMap = new HashMap<>();
            mLocaleMap.put(locale, localeNameMap);
        }
        localeNameMap.put(name, localization);
    }

    public List<String> getNames() {
        String defaultLocale = (String) mLocaleMap.keySet().toArray()[0];
        return new ArrayList<String>(mLocaleMap.get(defaultLocale).keySet());
    }

    public List<String> getLocales() {
        return new ArrayList<String>(mLocaleMap.keySet());
    }
    
    public List<String> getValues

}
