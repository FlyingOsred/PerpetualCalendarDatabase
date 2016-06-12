package com.flyingosred.app.perpetualcalendar.database.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelSheetBase;

public abstract class ResourceBase extends ExcelSheetBase {

    private static final String NAME_SUFFIX = "name";

    private static final int EXCEL_COL_NAME_START = 1;
    private static final int EXCEL_COL_NAME_END = 4;
    private static final int EXCEL_ROW_LOCALE_NAME = 1;
    private static final int EXCEL_ROW_DATA_START = 2;

    private HashMap<String, HashMap<String, String>> mResourceMap = new HashMap<>();

    public int getId(String value) {
        for (Map.Entry<String, HashMap<String, String>> entry : mResourceMap.entrySet()) {
            HashMap<String, String> localeMap = entry.getValue();
            int index = 0;
            for (Map.Entry<String, String> localeEntry : localeMap.entrySet()) {
                String localeValue = localeEntry.getValue();
                if (localeValue.equals(value)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    public List<String> getNames() {
        String defaultLocale = (String) mResourceMap.keySet().toArray()[0];
        return new ArrayList<String>(mResourceMap.get(defaultLocale).keySet());
    }

    public List<String> getLocales() {
        return new ArrayList<String>(mResourceMap.keySet());
    }

    public Map<String, String> getLocaleMap(String locale) {
        return mResourceMap.get(locale);
    }

    @Override
    protected void parse(List<XSSFSheet> sheetList) {
        for (XSSFSheet sheet : sheetList) {
            System.out.println("Parsing sheet " + sheet.getSheetName());
            String[] nameLocale = new String[EXCEL_COL_NAME_END - EXCEL_COL_NAME_START + 1];
            XSSFRow nameLocaleRow = sheet.getRow(EXCEL_ROW_LOCALE_NAME);
            int i;
            for (i = EXCEL_COL_NAME_START; i <= EXCEL_COL_NAME_END; i++) {
                nameLocale[i - EXCEL_COL_NAME_START] = ExcelHelper.getStringCellValue(nameLocaleRow, i);
            }
            int rows = sheet.getPhysicalNumberOfRows();
            for (i = EXCEL_ROW_DATA_START; i < rows; i++) {
                XSSFRow row = sheet.getRow(i);
                String name = composeName(getNamePrefix(sheet.getSheetName()),
                        ExcelHelper.getStringCellValue(row, EXCEL_COL_NAME_START), NAME_SUFFIX);
                for (int j = EXCEL_COL_NAME_START; j <= EXCEL_COL_NAME_END; j++) {
                    String locale = nameLocale[j - EXCEL_COL_NAME_START];
                    String localeName = ExcelHelper.getStringCellValue(row, j);
                    add(name, locale, localeName);
                }
            }
        }
    }

    public abstract String getType();

    private void add(String name, String locale, String localization) {
        HashMap<String, String> localeNameMap;
        if (mResourceMap.containsKey(locale)) {
            localeNameMap = mResourceMap.get(locale);
        } else {
            localeNameMap = new HashMap<>();
            mResourceMap.put(locale, localeNameMap);
        }
        localeNameMap.put(name, localization);
    }

    private String getNamePrefix(String sheetName) {
        String[] words = sheetName.split("(?=\\p{Upper})");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            sb.append(word);
            if (i != words.length - 1) {
                sb.append("_");
            }
        }
        return sb.toString();
    }

    private String composeName(String prefix, String body, String suffix) {
        StringBuilder sb = new StringBuilder();
        if (prefix != null) {
            sb.append(prefix);
        }
        if (body != null) {
            sb.append("_");
            sb.append(body);
        }
        if (suffix != null) {
            sb.append("_");
            sb.append(suffix);
        }
        return formatNameString(sb.toString());
    }

    private String formatNameString(String name) {
        return name.replace(" ", "_").replace("'", "").replace("-", "_").toLowerCase();
    }
}
