package com.flyingosred.app.perpetualcalendar.database.platform.android;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.flyingosred.app.perpetualcalendar.database.platform.PlatformBase;
import com.flyingosred.app.perpetualcalendar.database.resource.Resource;
import com.flyingosred.app.perpetualcalendar.database.util.Utils;
import com.flyingosred.app.perpetualcalendar.database.xml.XmlHelper;

public final class PlatformAndroid extends PlatformBase {

    private static final String RESOURCES_ROOT = "resources";

    private static final String ELEMENT_STRING_ARRAY = "string-array";

    private static final String ELEMENT_STRING = "string";

    private static final String ATTR_NAME = "name";

    private static final String ATTR_TRANSLATABLE = "translatable";

    private static final String ELEMENT_ITEM = "item";

    private static final String VALUES_DIR_NAME = "values";

    private static final String XML_SUFFIX = ".xml";

    private static final String STRINGS_FILE_NAME_PREFIX = "strings_";

    private static final String ARRAYS_FILE_NAME_PREFIX = "arrays_";

    private static final String NAME_SUFFIX = "_name";

    private static final String VALUE_SUFFIX = "_value";

    public PlatformAndroid() {
        super(PlatformBase.PLATFORM_ANDROID, "Android");
        Utils.delete(new File(getGeneratedPath()));
    }

    @Override
    public void generate() {
        generateDatabase();
    }

    @Override
    public void generateResources(List<Resource> resourceList) {
        for (Resource resource : resourceList) {
            System.out.println("Generating resource " + resource.getType());
            List<String> names = resource.getNames();
            List<String> itemNameList = new ArrayList<>();
            for (String name : names) {
                itemNameList.add("@string/" + name);
            }
            generateArrayXml(resource.getType(), itemNameList, resource.getValues());
            List<String> localeList = resource.getLocales();
            for (String locale : localeList) {
                generateStringXml(locale, resource.getType(), resource.getLocaleMap(locale));
            }
        }
    }

    @Override
    protected void createDatabase() {
        super.createDatabase();
        getSqlHelper().excute("CREATE TABLE 'android_metadata' ('locale' TEXT DEFAULT 'en_US');");
        getSqlHelper().excute("INSERT INTO 'android_metadata' VALUES ('en_US');");
    }

    private void generateDatabase() {
        createDatabase();
    }

    private void generateArrayXml(String type, List<String> nameList, List<String> valueList) {
        File valuesDir = new File(getGeneratedPath(), VALUES_DIR_NAME);
        if (!valuesDir.exists()) {
            valuesDir.mkdirs();
        }
        File arraysFile = new File(valuesDir, ARRAYS_FILE_NAME_PREFIX + type + XML_SUFFIX);
        String path = arraysFile.getAbsolutePath();
        Document document = XmlHelper.createDocument();
        XmlHelper.addComment(document);
        Element root = XmlHelper.createRootElement(document, RESOURCES_ROOT);
        Element nameElement = XmlHelper.createElement(root, ELEMENT_STRING_ARRAY)
                .addAttribute(ATTR_NAME, type + NAME_SUFFIX).addAttribute(ATTR_TRANSLATABLE, "false");
        for (String item : nameList) {
            XmlHelper.createElement(nameElement, ELEMENT_ITEM, item);
        }
        Element valueElement = XmlHelper.createElement(root, ELEMENT_STRING_ARRAY)
                .addAttribute(ATTR_NAME, type + VALUE_SUFFIX).addAttribute(ATTR_TRANSLATABLE, "false");
        for (String value : valueList) {
            XmlHelper.createElement(valueElement, ELEMENT_ITEM, value);
        }
        XmlHelper.outputFile(path, document);
    }

    private void generateStringXml(String locale, String type, Map<String, String> nameMap) {
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
        File stringsFile = new File(localeValuesDir, STRINGS_FILE_NAME_PREFIX + type + XML_SUFFIX);
        String path = stringsFile.getAbsolutePath();
        Document document = XmlHelper.createDocument();
        XmlHelper.addComment(document);
        Element root = XmlHelper.createRootElement(document, RESOURCES_ROOT);
        for (Map.Entry<String, String> entry : nameMap.entrySet()) {
            XmlHelper.createElement(root, ELEMENT_STRING, formatString(entry.getValue())).addAttribute(ATTR_NAME,
                    entry.getKey());
        }
        XmlHelper.outputFile(path, document);
    }

    private String formatString(String value) {
        return value.replace("'", "\\'");
    }
}
