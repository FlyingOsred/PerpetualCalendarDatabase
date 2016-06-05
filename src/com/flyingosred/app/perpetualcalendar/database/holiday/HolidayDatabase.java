package com.flyingosred.app.perpetualcalendar.database.holiday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.flyingosred.app.perpetualcalendar.database.LocaleName;
import com.flyingosred.app.perpetualcalendar.database.excel.ExcelHelper;

import sun.util.resources.cldr.en.TimeZoneNames_en;

public class HolidayDatabase {

    private static final String EXCEL_SHEET_REGION_NAME = "Region";

    private static final int EXCEL_COL_REGION_ID = 0;
    private static final int EXCEL_COL_REGION_NAME = 1;
    private static final int EXCEL_COL_REGION_LOCALE_START = 1;
    private static final int EXCEL_COL_REGION_LOCALE_END = 4;
    private static final int EXCEL_ROW_REGION_DATA_START = 2;
    private static final int EXCEL_ROW_REGION_LOCALE = 1;

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

    private List<HolidayRegion> mHolidayRegionList = new ArrayList<>();

    private List<Festival> mFestivalList = new ArrayList<>();

    private List<Holiday> mHolidayList = new ArrayList<>();

    public HolidayDatabase() {

    }

    public void init(ExcelHelper excelHelper) {
        parseRegion(excelHelper);
        parseFestival(excelHelper);
        parseHoliday(excelHelper);
    }

    public List<Integer> get(Calendar calendar) {
        List<Integer> holidayValueList = new ArrayList<>();
        for (Holiday holiday : mHolidayList) {
            Date date = holiday.getDate();
            Calendar tempCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.ENGLISH);
            tempCalendar.setTime(date);
            if (isSameDay(calendar, tempCalendar)) {
                int regionId = holiday.getRegion().getId();
                int festivalId = holiday.getFestival().getId();
                int offWork = holiday.getOffWork();
                int value = 
                if (holiday.get
            }
        }
        return holidayValueList;
    }

    private void parseHoliday(ExcelHelper excelHelper) {
        List<XSSFSheet> sheets = excelHelper.getSheetsWithSuffix(EXCEL_SHEET_HOLIDAY_SUFFIX);
        for (XSSFSheet sheet : sheets) {
            int rows = sheet.getPhysicalNumberOfRows();
            String sheetName = sheet.getSheetName();
            String regionName = sheetName.replace(EXCEL_SHEET_HOLIDAY_SUFFIX, "").toLowerCase();
            HolidayRegion region = findRegion(regionName);
            System.out.println("Parsing holiday sheet " + sheetName + " region " + region);
            int i;
            int j;
            for (i = EXCEL_ROW_HOLIDAY_DATA_START; i < rows; i++) {
                XSSFRow row = sheet.getRow(i);
                String nameFormula = ExcelHelper.getCellFormula(row, EXCEL_COL_HOLIDAY_NAME);
                Festival festival = null;
                if (nameFormula != null) {
                    String[] nameFormulaParts = nameFormula.split("!");
                    String type = nameFormulaParts[0];
                    String refCellName = nameFormulaParts[1];
                    refCellName = refCellName.replaceAll("[^\\d.]", "");
                    int index = Integer.parseInt(refCellName);
                    festival = findFestival(type, index);
                }
                int cols = row.getPhysicalNumberOfCells();
                j = EXCEL_COL_HOLIDAY_YEAR_START;
                while (j < cols) {
                    Date date = ExcelHelper.getDateCellValue(row, j);
                    j++;
                    int offWork = ExcelHelper.getIntCellValue(row, j);
                    j++;
                    Holiday holiday = new Holiday(region, festival, date, offWork);
                    mHolidayList.add(holiday);
                }

            }
        }
    }

    private void parseFestival(ExcelHelper excelHelper) {
        List<XSSFSheet> sheets = excelHelper.getSheetsWithSuffix(EXCEL_SHEET_FESTIVAL_SUFFIX);
        for (XSSFSheet sheet : sheets) {
            int rows = sheet.getPhysicalNumberOfRows();
            String sheetName = sheet.getSheetName();
            System.out.println("Parsing festival sheet " + sheetName);
            String[] nameLocale = new String[EXCEL_COL_FESTIVAL_LOCALE_END - EXCEL_COL_FESTIVAL_LOCALE_START + 1];
            XSSFRow localeRow = sheet.getRow(EXCEL_ROW_FESTIVAL_LOCALE);
            int i;
            int j;
            for (i = EXCEL_COL_FESTIVAL_LOCALE_START; i <= EXCEL_COL_FESTIVAL_LOCALE_END; i++) {
                nameLocale[i - EXCEL_COL_FESTIVAL_LOCALE_START] = ExcelHelper.getStringCellValue(localeRow, i);
            }
            for (i = EXCEL_ROW_FESTIVAL_DATA_START; i < rows; i++) {
                XSSFRow row = sheet.getRow(i);
                int id = ExcelHelper.getIntCellValue(row, EXCEL_COL_FESTIVAL_ID);
                if (id < 0) {
                    continue;
                }
                List<LocaleName> localeList = new ArrayList<>();
                for (j = EXCEL_COL_FESTIVAL_LOCALE_START; j <= EXCEL_COL_FESTIVAL_LOCALE_END; j++) {
                    String localeName = ExcelHelper.getStringCellValue(row, j);
                    LocaleName locale = new LocaleName(nameLocale[j - EXCEL_COL_FESTIVAL_LOCALE_START], localeName);
                    localeList.add(locale);
                }
                Festival festival = new Festival(id, sheetName, localeList);
                mFestivalList.add(festival);
            }
        }
        System.out.println("Totally " + mFestivalList.size() + " festival.");
    }

    private void parseRegion(ExcelHelper excelHelper) {
        XSSFSheet sheet = excelHelper.getSheet(EXCEL_SHEET_REGION_NAME);
        int rows = sheet.getPhysicalNumberOfRows();
        int i;
        int j;
        String[] nameLocale = new String[EXCEL_COL_REGION_LOCALE_END - EXCEL_COL_REGION_LOCALE_START + 1];
        XSSFRow localeRow = sheet.getRow(EXCEL_ROW_REGION_LOCALE);
        for (i = EXCEL_COL_REGION_LOCALE_START; i <= EXCEL_COL_REGION_LOCALE_END; i++) {
            nameLocale[i - EXCEL_COL_REGION_LOCALE_START] = ExcelHelper.getStringCellValue(localeRow, i);
        }
        for (i = EXCEL_ROW_REGION_DATA_START; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            int id = ExcelHelper.getIntCellValue(row, EXCEL_COL_REGION_ID);
            if (id < 0) {
                continue;
            }
            String name = ExcelHelper.getStringCellValue(row, EXCEL_COL_REGION_NAME).toLowerCase();
            List<LocaleName> localeList = new ArrayList<>();
            for (j = EXCEL_COL_REGION_LOCALE_START; j <= EXCEL_COL_REGION_LOCALE_END; j++) {
                String localeName = ExcelHelper.getStringCellValue(row, j);
                LocaleName locale = new LocaleName(nameLocale[j - EXCEL_COL_REGION_LOCALE_START], localeName);
                localeList.add(locale);
            }
            HolidayRegion region = new HolidayRegion(id, name, localeList);
            System.out.println("Found holiday region " + region.toString());
            mHolidayRegionList.add(region);
        }
    }

    private Festival findFestival(String type, int index) {
        for (Festival festival : mFestivalList) {
            if (festival.getId() == index - Festival.INDEX_ID_OFFSET && type.equals(festival.getType())) {
                return festival;
            }
        }
        return null;
    }

    private HolidayRegion findRegion(String regionName) {
        for (HolidayRegion region : mHolidayRegionList) {
            if (region.getName().equals(regionName)) {
                return region;
            }
        }
        return null;
    }

    private boolean isSameDay(Calendar day1, Calendar day2) {
        return day1.get(Calendar.YEAR) == day2.get(Calendar.YEAR)
                && day1.get(Calendar.MONTH) == day2.get(Calendar.MONTH)
                && day1.get(Calendar.DATE) == day2.get(Calendar.DATE);
    }

}
