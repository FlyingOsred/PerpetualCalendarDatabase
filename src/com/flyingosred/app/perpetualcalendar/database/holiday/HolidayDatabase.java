package com.flyingosred.app.perpetualcalendar.database.holiday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;
import com.flyingosred.app.perpetualcalendar.database.locale.LocaleName;
import com.flyingosred.app.perpetualcalendar.database.resource.Resource;
import com.flyingosred.app.perpetualcalendar.database.solarterm.SolarTermResource;
import com.flyingosred.app.perpetualcalendar.database.util.Utils;

import sun.util.resources.cldr.en.TimeZoneNames_en;

public class HolidayDatabase {

    private static final String EXCEL_SHEET_REGION_NAME = "Region";

    private static final int EXCEL_COL_REGION_ID = 0;
    private static final int EXCEL_COL_REGION_NAME = 1;
    private static final int EXCEL_COL_REGION_LOCALE_START = 1;
    private static final int EXCEL_COL_REGION_LOCALE_END = 4;
    private static final int EXCEL_ROW_REGION_DATA_START = 2;
    private static final int EXCEL_ROW_REGION_LOCALE = 1;

    private static final int EXCEL_COL_FESTIVAL_NAME = 1;
    private static final int EXCEL_COL_FESTIVAL_LOCALE_START = 1;
    private static final int EXCEL_COL_FESTIVAL_LOCALE_END = 4;
    private static final int EXCEL_ROW_FESTIVAL_LOCALE = 1;
    private static final int EXCEL_ROW_FESTIVAL_DATA_START = 2;
    private static final int EXCEL_COL_FESTIVAL_ID = 0;

    private static final int EXCEL_COL_HOLIDAY_ID = 0;
    private static final int EXCEL_COL_HOLIDAY_NAME = 1;
    private static final int EXCEL_COL_HOLIDAY_YEAR_START = 2;
    private static final int EXCEL_COL_HOLIDAY_YEAR_OFFSET = 2;
    private static final int EXCEL_ROW_HOLIDAY_DATA_START = 2;

    private static final String EXCEL_SHEET_FESTIVAL_SUFFIX = "Festival";

    private static final String EXCEL_SHEET_HOLIDAY_SUFFIX = "Holiday";

    private static final String REGION_NAME_PREFIX = "region";

    private static final String NAME_POSTFIX = "name";

    private final HolidayResource mResource = new HolidayResource();

    public HolidayDatabase() {
        mResource.Init();
    }
    
//    public static void parse(Resources resources) {
//        ExcelHelper excelHelper = new ExcelHelper();
//        parseRegion(excelHelper, resources);
//        parseFestival(excelHelper, resources);
//        parseHoliday(excelHelper, resources);
//    }

    public Resource getResource() {
        return mResource;
    }

//    private static void parseHoliday(ExcelHelper excelHelper, Resources resources) {
        // List<XSSFSheet> sheets =
        // excelHelper.getSheetsWithSuffix(EXCEL_SHEET_HOLIDAY_SUFFIX);
        // for (XSSFSheet sheet : sheets) {
        // int rows = sheet.getPhysicalNumberOfRows();
        // String sheetName = sheet.getSheetName();
        // String regionName = sheetName.replace(EXCEL_SHEET_HOLIDAY_SUFFIX,
        // "").toLowerCase();
        // HolidayRegion region = findRegion(regionName);
        // System.out.println("Parsing holiday sheet " + sheetName + " region "
        // + region);
        // int i;
        // int j;
        // Festival lastFestival = null;
        // for (i = EXCEL_ROW_HOLIDAY_DATA_START; i < rows; i++) {
        // XSSFRow row = sheet.getRow(i);
        // String nameFormula = ExcelHelper.getCellFormula(row,
        // EXCEL_COL_HOLIDAY_NAME);
        // Festival festival = null;
        // if (nameFormula != null) {
        // String[] nameFormulaParts = nameFormula.split("!");
        // String type = nameFormulaParts[0];
        // String refCellName = nameFormulaParts[1];
        // refCellName = refCellName.replaceAll("[^\\d.]", "");
        // int index = Integer.parseInt(refCellName);
        // festival = findFestival(type, index);
        // lastFestival = festival;
        // }
        // int cols = row.getPhysicalNumberOfCells();
        // j = EXCEL_COL_HOLIDAY_YEAR_START;
        // while (j < cols) {
        // Date date = ExcelHelper.getDateCellValue(row, j);
        // if (date == null) {
        // j += 2;
        // continue;
        // }
        // j++;
        // int offWork = ExcelHelper.getIntCellValue(row, j);
        // j++;
        // if (festival != null) {
        // Holiday holiday = new Holiday(region, festival, date, -1);
        // mHolidayList.add(holiday);
        // }
        //
        // if (offWork >= 0) {
        // if (festival == null) {
        // festival = lastFestival;
        // }
        // Holiday holiday = new Holiday(region, festival, date, offWork);
        // mHolidayList.add(holiday);
        // }
        // }
        // }
        // }
//    }

//    private static void parseFestival(ExcelHelper excelHelper, Resources resources) {
        // List<XSSFSheet> sheets =
        // excelHelper.getSheetsWithSuffix(EXCEL_SHEET_FESTIVAL_SUFFIX);
        // for (XSSFSheet sheet : sheets) {
        // int rows = sheet.getPhysicalNumberOfRows();
        // String sheetName = sheet.getSheetName();
        // System.out.println("Parsing festival sheet " + sheetName);
        // String[] nameLocale = new String[EXCEL_COL_FESTIVAL_LOCALE_END -
        // EXCEL_COL_FESTIVAL_LOCALE_START + 1];
        // XSSFRow localeRow = sheet.getRow(EXCEL_ROW_FESTIVAL_LOCALE);
        // int i;
        // int j;
        // for (i = EXCEL_COL_FESTIVAL_LOCALE_START; i <=
        // EXCEL_COL_FESTIVAL_LOCALE_END; i++) {
        // nameLocale[i - EXCEL_COL_FESTIVAL_LOCALE_START] =
        // ExcelHelper.getStringCellValue(localeRow, i);
        // }
        // String typeName = (sheetName.replace(EXCEL_SHEET_FESTIVAL_SUFFIX, "")
        // + "_" + EXCEL_SHEET_FESTIVAL_SUFFIX)
        // .toLowerCase();
        // LocalizationResource localizationResource = new
        // LocalizationResource(typeName);
        // for (i = EXCEL_ROW_FESTIVAL_DATA_START; i < rows; i++) {
        // XSSFRow row = sheet.getRow(i);
        // int id = ExcelHelper.getIntCellValue(row, EXCEL_COL_FESTIVAL_ID);
        // if (id < 0) {
        // continue;
        // }
        // String name = Utils.composeName(typeName,
        // ExcelHelper.getStringCellValue(row, EXCEL_COL_FESTIVAL_NAME),
        // NAME_POSTFIX);
        // for (j = EXCEL_COL_FESTIVAL_LOCALE_START; j <=
        // EXCEL_COL_FESTIVAL_LOCALE_END; j++) {
        // String localeName = ExcelHelper.getStringCellValue(row, j);
        // localizationResource.add(name, nameLocale[j -
        // EXCEL_COL_REGION_LOCALE_START], localeName);
        // }
        // }
        // }
//    }

//    private static void parseRegion(ExcelHelper excelHelper, Resources resources) {
        // XSSFSheet sheet = excelHelper.getSheet(EXCEL_SHEET_REGION_NAME);
        // int rows = sheet.getPhysicalNumberOfRows();
        // int i;
        // int j;
        // String[] nameLocale = new String[EXCEL_COL_REGION_LOCALE_END -
        // EXCEL_COL_REGION_LOCALE_START + 1];
        // XSSFRow localeRow = sheet.getRow(EXCEL_ROW_REGION_LOCALE);
        // for (i = EXCEL_COL_REGION_LOCALE_START; i <=
        // EXCEL_COL_REGION_LOCALE_END; i++) {
        // nameLocale[i - EXCEL_COL_REGION_LOCALE_START] =
        // ExcelHelper.getStringCellValue(localeRow, i);
        // }
        // LocalizationResource localizationResource = new
        // LocalizationResource(EXCEL_SHEET_REGION_NAME);
        // for (i = EXCEL_ROW_REGION_DATA_START; i < rows; i++) {
        // XSSFRow row = sheet.getRow(i);
        // int id = ExcelHelper.getIntCellValue(row, EXCEL_COL_REGION_ID);
        // if (id < 0) {
        // continue;
        // }
        // String name = Utils.composeName(REGION_NAME_PREFIX,
        // ExcelHelper.getStringCellValue(row, EXCEL_COL_REGION_NAME),
        // NAME_POSTFIX);
        // for (j = EXCEL_COL_REGION_LOCALE_START; j <=
        // EXCEL_COL_REGION_LOCALE_END; j++) {
        // String localeName = ExcelHelper.getStringCellValue(row, j);
        // localizationResource.add(name, nameLocale[j -
        // EXCEL_COL_REGION_LOCALE_START], localeName);
        // }
        // }
        // resources.add(localizationResource);
    // }

    private Festival findFestival(String type, int index) {
        // for (Festival festival : mFestivalList) {
        // if (festival.getIndex() == index - Festival.INDEX_ID_OFFSET &&
        // type.equals(festival.getType())) {
        // return festival;
        // }
        // }
        return null;
    }

    private HolidayRegion findRegion(String regionName) {
        // for (HolidayRegion region : mHolidayRegionList) {
        // if (region.getName().equals(regionName)) {
        // return region;
        // }
        // }
        return null;
    }

    private boolean isSameDay(Calendar day1, Calendar day2) {
        return day1.get(Calendar.YEAR) == day2.get(Calendar.YEAR)
                && day1.get(Calendar.MONTH) == day2.get(Calendar.MONTH)
                && day1.get(Calendar.DATE) == day2.get(Calendar.DATE);
    }

    private int combineHolidayValue(int regionId, int festivalId, int offWork) {
        int value = 0;
        if (festivalId > 0) {
            value = 0x3FF & festivalId;
        }
        if (offWork >= 0) {
            value |= ((1 << offWork) & 0x3) << 10;
        }
        value |= regionId << 12;
        return value;
    }

}
