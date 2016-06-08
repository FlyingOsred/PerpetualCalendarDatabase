package com.flyingosred.app.perpetualcalendar.database.solarterm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.resource.DataResource;
import com.flyingosred.app.perpetualcalendar.database.resource.LocalizationResource;
import com.flyingosred.app.perpetualcalendar.database.resource.Resources;
import com.flyingosred.app.perpetualcalendar.database.util.Utils;

public final class SolarTermDatabase {

    private static final String EXCEL_SHEET_NAME = "SolarTerm";

    private static final String NAME_PREFIX = "solar_term";

    private static final String NAME_POSTFIX = "name";

    private static final int EXCEL_COL_ID = 0;
    private static final int EXCEL_COL_NAME_START = 1;
    private static final int EXCEL_COL_NAME_END = 4;
    private static final int EXCEL_COL_YEAR_START = 5;

    private static final int EXCEL_ROW_YEAR = 0;
    private static final int EXCEL_ROW_SECOND = 1;
    private static final int EXCEL_ROW_DATA_START = 2;

    public static void parse(Resources resources) {
        ExcelHelper excelHelper = new ExcelHelper();
        XSSFSheet sheet = excelHelper.getSheet(EXCEL_SHEET_NAME);
        int i;
        int j;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String[] nameLocale = new String[EXCEL_COL_NAME_END - EXCEL_COL_NAME_START + 1];
        XSSFRow secondRow = sheet.getRow(EXCEL_ROW_SECOND);
        for (i = EXCEL_COL_NAME_START; i <= EXCEL_COL_NAME_END; i++) {
            nameLocale[i - EXCEL_COL_NAME_START] = ExcelHelper.getStringCellValue(secondRow, i);
        }
        int rows = sheet.getPhysicalNumberOfRows();
        LocalizationResource localizationResource = new LocalizationResource(NAME_PREFIX);
        HashMap<Integer, DataResource> dataMap = new HashMap<>();
        for (i = EXCEL_ROW_DATA_START; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            int cols = row.getPhysicalNumberOfCells();
            int id = ExcelHelper.getIntCellValue(row, EXCEL_COL_ID);
            if (id < 0) {
                continue;
            }
            String name = Utils.composeName(NAME_PREFIX, ExcelHelper.getStringCellValue(row, EXCEL_COL_NAME_START),
                    NAME_POSTFIX);
            for (j = EXCEL_COL_NAME_START; j <= EXCEL_COL_NAME_END; j++) {
                String locale = nameLocale[j - EXCEL_COL_NAME_START];
                String localeName = ExcelHelper.getStringCellValue(row, j);
                localizationResource.add(name, locale, localeName);
            }
            for (j = EXCEL_COL_YEAR_START; j < cols; j++) {
                int year = ExcelHelper.getIntCellValue(sheet.getRow(EXCEL_ROW_YEAR), j);
                DataResource dataResource;
                if (dataMap.containsKey(year)) {
                    dataResource = dataMap.get(year);
                } else {
                    dataResource = new DataResource(NAME_PREFIX + "_" + year);
                    dataMap.put(year, dataResource);
                }
                Date date = ExcelHelper.getDateCellValue(row, j);
                if (date != null) {
                    dataResource.add(formatter.format(date));
                }
            }
        }
        resources.add(localizationResource);
        for (Map.Entry<Integer, DataResource> entry : dataMap.entrySet()) {
            resources.add(entry.getValue());
        }
    }
}
