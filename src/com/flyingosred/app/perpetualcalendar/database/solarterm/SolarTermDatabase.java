package com.flyingosred.app.perpetualcalendar.database.solarterm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.locale.LocaleName;
import com.flyingosred.app.perpetualcalendar.database.util.Utils;

public class SolarTermDatabase {

    private static final String EXCEL_SHEET_NAME = "SolarTerm";

    private static final String NAME_PREFIX = "solar_term";

    private static final String NAME_POSTFIX = "name";

    private static final int EXCEL_COL_ID = 0;
    private static final int EXCEL_COL_NAME_START = 1;
    private static final int EXCEL_COL_NAME_END = 4;
    private static final int EXCEL_COL_YEAR_START = 5;

    private static final int EXCEL_ROW_SECOND = 1;
    private static final int EXCEL_ROW_DATA_START = 2;

    private final List<SolarTerm> mSolarTermList = new ArrayList<>();

    HashMap<String, HashMap<String, String>> mLocaleMap = new HashMap<>();

    public SolarTermDatabase() {
    }

    public void init(ExcelHelper excelHelper) {
        XSSFSheet sheet = excelHelper.getSheet(EXCEL_SHEET_NAME);
        int i;
        int j;
        String[] nameLocale = new String[EXCEL_COL_NAME_END - EXCEL_COL_NAME_START + 1];
        XSSFRow secondRow = sheet.getRow(EXCEL_ROW_SECOND);
        for (i = EXCEL_COL_NAME_START; i <= EXCEL_COL_NAME_END; i++) {
            nameLocale[i - EXCEL_COL_NAME_START] = ExcelHelper.getStringCellValue(secondRow, i);
        }
        int rows = sheet.getPhysicalNumberOfRows();
        for (i = EXCEL_ROW_DATA_START; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            int cols = row.getPhysicalNumberOfCells();
            int id = ExcelHelper.getIntCellValue(row, EXCEL_COL_ID);
            if (id < 0) {
                continue;
            }
            String name = Utils.composeName(NAME_PREFIX, ExcelHelper.getStringCellValue(row, EXCEL_COL_NAME_START),
                    NAME_POSTFIX);
            List<LocaleName> localeList = new ArrayList<>();
            for (j = EXCEL_COL_NAME_START; j <= EXCEL_COL_NAME_END; j++) {
                String locale = nameLocale[j - EXCEL_COL_NAME_START];
                String localeName = ExcelHelper.getStringCellValue(row, j);
                HashMap<String, String> localeNameMap;
                if (mLocaleMap.containsKey(locale)) {
                    localeNameMap = mLocaleMap.get(locale);
                } else {
                    localeNameMap = new HashMap<>();
                    mLocaleMap.put(localeName, localeNameMap);
                }
                localeNameMap.put(name, localeName);
                LocaleName locale = new LocaleName(nameLocale[j - EXCEL_COL_NAME_START], localeName);
                localeList.add(locale);
            }
            List<Date> dateList = new ArrayList<>();
            for (j = EXCEL_COL_YEAR_START; j < cols; j++) {
                Date date = ExcelHelper.getDateCellValue(row, j);
                dateList.add(date);
            }

            SolarTerm solarTerm = new SolarTerm(id, name, localeList, dateList);
            System.out.println(solarTerm.toString());
            mSolarTermList.add(solarTerm);
        }
    }

    public List<SolarTerm> get() {
        return mSolarTermList;
    }
}
