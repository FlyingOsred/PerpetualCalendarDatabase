package com.flyingosred.app.perpetualcalendar.database.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalizationResource extends ResourceBase {

    private final HashMap<String, HashMap<String, String>> mDataMap = new HashMap<>();

    public LocalizationResource(String type) {
        super(type);
    }

    public void add(String name, String locale, String localization) {
        HashMap<String, String> localeNameMap;
        if (mDataMap.containsKey(locale)) {
            localeNameMap = mDataMap.get(locale);
        } else {
            localeNameMap = new HashMap<>();
            mDataMap.put(locale, localeNameMap);
        }
        localeNameMap.put(name, localization);
    }

    public List<String> getNames() {
        String defaultLocale = (String) mDataMap.keySet().toArray()[0];
        return new ArrayList<String>(mDataMap.get(defaultLocale).keySet());
    }

    public List<String> getLocales() {
        return new ArrayList<String>(mDataMap.keySet());
    }

    public Map<String, String> getLocaleMap(String locale) {
        return mDataMap.get(locale);
    }
}
